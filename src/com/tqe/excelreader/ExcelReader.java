package com.tqe.excelreader;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tqe.po.SC;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.tqe.po.Course;
import com.tqe.po.Student;
import com.tqe.po.Teacher;

public abstract class ExcelReader<E> {

	Log logger = LogFactory.getLog(ExcelReader.class);;

	/**
	 * 检查文件是否符合
	 * @param excelDir
	 * @return
	 */
	protected abstract boolean checkFile(String excelDir);

    protected void postProcessList(List<E> list,String excelDir){

    };
	
	private Class<?> clazz = null;	
	public ExcelReader() {	//获取泛型类型
		ParameterizedType type =   (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<?>) type.getActualTypeArguments()[0];
	}
	
	/**
	 * 获取excel表格内容 ，并封装成List
	 * 默认不设置初始密码
	 * @param excelDir	文件路径
	 * @return	
	 * @throws FileNotFoundException
	 */
	public List<E> getAll(String excelDir) throws FileNotFoundException {
		return this.getAll(excelDir, false);
	}
	
	/**
	 * 获取excel表格内容 ，并封装成List
	 * @param excelDir	文件路径
	 * @param needSetPassword		是否设置初始密码
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<E> getAll(String excelDir,boolean needSetPassword) throws FileNotFoundException {
		List<E> list = new ArrayList<E>();

		int titleIndex = getTitleIndex();;		//有效数据的从第几行开始的
        if(checkFile(excelDir)){
			list = excelStringValueToPojoList(excelDir, list, needSetPassword,titleIndex) ;
            postProcessList(list,excelDir);
		}else{
			logger.error("file not fount! excelDir:"+excelDir);
			throw new FileNotFoundException("文件没有找到");
		}
		return list;
	}

    /**
     * Excel 的标题是从 第几行开始的
     */
    private int getTitleIndex() {
        int titleIndex = 0;
        if(clazz==Teacher.class){
            titleIndex = 0;
        }else if(clazz == Course.class || clazz==SC.class){
            titleIndex = 2;
        }else{
            titleIndex = 0;
        }
        return titleIndex;
    }

    /**
	 * 把Excel数据转换为 Pojo 的List类型的数据
	 * @param excelDir
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> excelStringValueToPojoList(String excelDir, List<E> list,boolean needSetPassword,int titleIndex) {
		
		List<Map<String,String>> data = ExcelUtils.getExcelRecords(excelDir, 0 ,titleIndex);	//从excel中获取数据
		
		if(data!=null && !data.isEmpty()){
			for(Map<String,String> row :data){	//遍历每行数据
				E e  = null;
		        boolean isSkip = false;     //改行是否需要过滤掉
				Object obj  = null;
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex){
					ex.printStackTrace();
				}
				Field[] fields = clazz.getDeclaredFields();		//获取成员的全部Filed
				for(Field f : fields){
					String fieldName = f.getName();		//获取成员变量名
					String fn = new String(fieldName);

                    fn = clazz.getSimpleName().toLowerCase()+"."+fn;  //获取Property文件中的key

					String columnName = ExcelProperty.getProperty(fn);	//获得excel中文列名
					String methodName = fieldNameToSetter(fieldName);

					Method m;
					try {
						m = clazz.getMethod(methodName,f.getType());
						String value = row.get(columnName);

                        String filterFieldName = "filter."+fn;
                        String filterFieldValue = ExcelProperty.getProperty(filterFieldName);
                        if(filterFieldValue!=null && value.equalsIgnoreCase(filterFieldValue.trim())){   //如果该字段的值符合过滤条件 那么该行数据就跳过
                            isSkip = true;
                            break;
                        }
						convertAndInvoke(m, obj, value, f.getType());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
				//如果需要添加初始密码  那么,向初始 数据中添加默认密码（证件号码后六位）
				if(needSetPassword){
					setPassword(obj, row);
				}
                if(!isSkip){    //只添加
                    e = (E)obj;
                    list.add(e);
                }

			}
		}
		return list;
	}
	
	/**
	 * 根据成员属性获取Setter方法
	 * @param fieldName
	 * @return
	 */
	private String fieldNameToSetter(String fieldName){
		if(fieldName!=null){
			fieldName = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		}
		return "set"+fieldName;	//根据成员变量名转换成方法名
	}
	
	/**
	 * 根据Id生成密码,并注入到对象当中
	 * 密码生成策略：
	 * 如果有证件号 并且证件号为18位 那么默认密码为证件号的后8位
	 * 没有的话 学生为学生学号 教师为教师学号
	 * 如果学号和教师号都没有，那么默认密码为 8个8 88888888
	 */
	private void setPassword(Object obj,Map<String,String> row){
		
		String defaultPwd;
		//首先获取证件号码
		String idNumber = row.get("证件号码");
		if(idNumber!=null && idNumber.length()==18){	//如果有证件号码 并且证件号码为18位
			defaultPwd = idNumber.substring(10);		//那么就取证件号码后8位作为登陆密码
		}else{
			if(clazz == Teacher.class){
				defaultPwd = row.get("教师号");		//教师号作为默认的密码
			}
			else if(clazz == Student.class){		//学生学号作为默认的密码
				defaultPwd = row.get("学号");
			}else{
				defaultPwd = "88888888";		//如果没有教师号 和 学生好，那么默认密码就是88888888
			}
		}
		
		//获取SetPassword方法
		Method m = null ;
		try {
			m = clazz.getDeclaredMethod("setPassword", String.class);
		} catch (NoSuchMethodException e) {
            logger.warn(e.getMessage());
		} catch (SecurityException e) {
            logger.warn(e.getMessage());
		}

		if(m!=null){	//注入密码
			convertAndInvoke(m, obj, defaultPwd, String.class);
		}
		
	}
	
	/**
	 *  将excel中String类型的数据转换成bean中的成员变量类型，并注入到对象中去
	 */
	protected void convertAndInvoke(Method method, Object obj, String value, Class<?> type){
		if(method==null){
			throw new IllegalArgumentException("传入方法不能为空");
		}
		try {
			if(value==null){
				return ;
			}
			if(type == Integer.class){
				if(StringUtils.hasText(value)){
					method.invoke(obj, (int)Double.parseDouble(value));
				}
			}else if(type == Double.class){
				method.invoke(obj, Double.parseDouble(value));
			}else if(type == Date.class){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				date = sdf.parse(value);
				method.invoke(obj, date);
				
			}else{
				method.invoke(obj, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
