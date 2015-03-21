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

import org.springframework.util.StringUtils;

import com.tqe.po.Course;
import com.tqe.po.Student;
import com.tqe.po.Teacher;

public abstract class ExcelReader<E> {
	
	/**
	 * 检查文件是否符合
	 * @param excelDir
	 * @return
	 */
	public abstract boolean checkFile(String excelDir);
	
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
	 * @param isSetPassword		是否设置初始密码
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<E> getAll(String excelDir,boolean isSetPassword) throws FileNotFoundException {
		List<E> list = new ArrayList<E>();
		int titleIndex = 0;
		if(clazz==Teacher.class){
			titleIndex = 0;
		}else if(clazz == Course.class){
			titleIndex = 2;
		}else{
			titleIndex = 0;
		}
		if(checkFile(excelDir)){
			list = excelStringValueToPojoList(excelDir, list,isSetPassword ,titleIndex) ;
			
		}else{
			throw new FileNotFoundException("文件没有找到");
		}
		return list;
	}
	
	/**
	 * 把Excel数据转换为 Pojo 的List类型的数据
	 * @param excelDir
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> excelStringValueToPojoList(String excelDir, List<E> list,boolean isSetPassword,int titleIndex) {
		
		List<Map<String,String>> data = ExcelUtils.getExcelRecords(excelDir, 0 ,titleIndex);	//从excel中获取数据
		
		if(data!=null && !data.isEmpty()){
			for(Map<String,String> row :data){	//遍历每行数据
				E e  = null;
				
				Object obj  = null;
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException ex) {
					ex.printStackTrace();
				}
				Field[] fields = clazz.getDeclaredFields();		//获取成员的全部Filed
				for(Field f : fields){
					String fieldName = f.getName();		//获取成员变量名
					String fn = new String(fieldName);
					if(clazz == Course.class){
						fn="course."+fn;
					}else if(clazz == Student.class){
						fn="student."+fn;
					}
					String columnName = ExcelProperty.getProperty(fn);	//获得excel中文列名
					
					
					String methodName = fieldNameToSetter(fieldName);
					Method m;
					try {
						m = clazz.getMethod(methodName,f.getType());
						String value = row.get(columnName);		
						convertAdnInvoke(m, obj,value,f.getType());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
				//如果需要添加初始密码  那么,向初始 数据中添加默认密码（证件号码后六位）
				if(isSetPassword){
					setPassword(obj, row);
				}
				
				e = (E)obj;
				list.add(e);
			}
		}
		return list;
	}
	
	private String fieldNameToSetter(String fieldName){
		if(fieldName!=null){
			fieldName = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		}
		return "set"+fieldName;	//根据成员变量名转换成方法名
	}
	
	/**
	 * 根据Id生成密码,并注入到对象当中
	 * @param idNumber
	 */
	private void setPassword(Object obj,Map<String,String> row){
		
		String defaultPwd;
		//首先获取证件号码
		String idNumber = row.get("证件号码");
		if(idNumber!=null && idNumber.length()==18){	//如果有证件号码
			defaultPwd = idNumber.substring(11);
		}else{
			if(clazz == Teacher.class){
				defaultPwd = row.get("教师号");
			}
			else if(clazz == Student.class){
				defaultPwd = row.get("学号");
			}else{
				defaultPwd = "88888888";
			}
		}
		
		//获取SetPassword方法
		Method m = null ;
		try {
			m = clazz.getDeclaredMethod("setPassword", String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		if(m!=null){
			//截取证件号码后6位，作为密码，然后执行setPassword方法
			convertAdnInvoke(m, obj, defaultPwd, String.class);
		}
		
	}
	
	/**
	 *  将excel中String类型的数据转换成bean中的成员变量类型
	 * @param method
	 * @param obj
	 * @param value
	 * @param type
	 */
	protected void convertAdnInvoke(Method method,Object obj ,String value ,Class<?> type){
		if(method==null){
			throw new IllegalArgumentException("传入方法不能为空");
		}
		try {
			if(type == Integer.class){
				if(StringUtils.hasText(value)){
					method.invoke(obj, (int)Double.parseDouble(value));
				}
			}else if(type == Double.class){
				method.invoke(obj, Double.parseDouble(value));
			}else if(type == Date.class){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				if(value!=null){
					date = sdf.parse(value);
				}
				method.invoke(obj, date);
				
			}else{
				method.invoke(obj, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
