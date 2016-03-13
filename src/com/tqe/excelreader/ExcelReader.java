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
	 * ����ļ��Ƿ����
	 * @param excelDir
	 * @return
	 */
	public abstract boolean checkFile(String excelDir);
	
	private Class<?> clazz = null;	
	public ExcelReader() {	//��ȡ��������
		ParameterizedType type =   (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<?>) type.getActualTypeArguments()[0];
	}
	
	/**
	 * ��ȡexcel������� ������װ��List
	 * Ĭ�ϲ����ó�ʼ����
	 * @param excelDir	�ļ�·��
	 * @return	
	 * @throws FileNotFoundException
	 */
	public List<E> getAll(String excelDir) throws FileNotFoundException {
		return this.getAll(excelDir, false);
	}
	
	/**
	 * ��ȡexcel������� ������װ��List
	 * @param excelDir	�ļ�·��
	 * @param needSetPassword		�Ƿ����ó�ʼ����
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<E> getAll(String excelDir,boolean needSetPassword) throws FileNotFoundException {
		List<E> list = new ArrayList<E>();

		int titleIndex = 0;		//��Ч���ݵĴӵڼ��п�ʼ��
		if(clazz==Teacher.class){
			titleIndex = 0;
		}else if(clazz == Course.class || clazz==SC.class){
			titleIndex = 2;
		}else{
			titleIndex = 0;
		}
		if(checkFile(excelDir)){
			list = excelStringValueToPojoList(excelDir, list, needSetPassword,titleIndex) ;
			
		}else{
			logger.error("file not fount! excelDir:"+excelDir);
			throw new FileNotFoundException("�ļ�û���ҵ�");
		}
		return list;
	}
	
	/**
	 * ��Excel����ת��Ϊ Pojo ��List���͵�����
	 * @param excelDir
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> excelStringValueToPojoList(String excelDir, List<E> list,boolean needSetPassword,int titleIndex) {
		
		List<Map<String,String>> data = ExcelUtils.getExcelRecords(excelDir, 0 ,titleIndex);	//��excel�л�ȡ����
		
		if(data!=null && !data.isEmpty()){
			for(Map<String,String> row :data){	//����ÿ������
				E e  = null;
		        boolean isSkip = false;     //�����Ƿ���Ҫ���˵�
				Object obj  = null;
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex){
					ex.printStackTrace();
				}
				Field[] fields = clazz.getDeclaredFields();		//��ȡ��Ա��ȫ��Filed
				for(Field f : fields){
					String fieldName = f.getName();		//��ȡ��Ա������
					String fn = new String(fieldName);

                    fn = clazz.getSimpleName().toLowerCase()+"."+fn;  //��ȡProperty�ļ��е�key

					String columnName = ExcelProperty.getProperty(fn);	//���excel��������
					String methodName = fieldNameToSetter(fieldName);

					Method m;
					try {
						m = clazz.getMethod(methodName,f.getType());
						String value = row.get(columnName);

                        String filterFieldName = "filter."+fn;
                        String filterFieldValue = ExcelProperty.getProperty(filterFieldName);
                        if(filterFieldValue!=null && value.equalsIgnoreCase(filterFieldValue.trim())){   //������ֶε�ֵ���Ϲ������� ��ô�������ݾ�����
                            isSkip = true;
                            break;
                        }
						convertAndInvoke(m, obj, value, f.getType());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
				//�����Ҫ��ӳ�ʼ����  ��ô,���ʼ ���������Ĭ�����루֤���������λ��
				if(needSetPassword){
					setPassword(obj, row);
				}
                if(!isSkip){    //ֻ���
                    e = (E)obj;
                    list.add(e);
                }

			}
		}
		return list;
	}
	
	/**
	 * ���ݳ�Ա���Ի�ȡSetter����
	 * @param fieldName
	 * @return
	 */
	private String fieldNameToSetter(String fieldName){
		if(fieldName!=null){
			fieldName = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		}
		return "set"+fieldName;	//���ݳ�Ա������ת���ɷ�����
	}
	
	/**
	 * ����Id��������,��ע�뵽������
	 * �������ɲ��ԣ�
	 * �����֤���� ����֤����Ϊ18λ ��ôĬ������Ϊ֤���ŵĺ�8λ
	 * û�еĻ� ѧ��Ϊѧ��ѧ�� ��ʦΪ��ʦѧ��
	 * ���ѧ�źͽ�ʦ�Ŷ�û�У���ôĬ������Ϊ 8��8 88888888
	 */
	private void setPassword(Object obj,Map<String,String> row){
		
		String defaultPwd;
		//���Ȼ�ȡ֤������
		String idNumber = row.get("֤������");
		if(idNumber!=null && idNumber.length()==18){	//�����֤������ ����֤������Ϊ18λ
			defaultPwd = idNumber.substring(10);		//��ô��ȡ֤�������8λ��Ϊ��½����
		}else{
			if(clazz == Teacher.class){
				defaultPwd = row.get("��ʦ��");		//��ʦ����ΪĬ�ϵ�����
			}
			else if(clazz == Student.class){		//ѧ��ѧ����ΪĬ�ϵ�����
				defaultPwd = row.get("ѧ��");
			}else{
				defaultPwd = "88888888";		//���û�н�ʦ�� �� ѧ���ã���ôĬ���������88888888
			}
		}
		
		//��ȡSetPassword����
		Method m = null ;
		try {
			m = clazz.getDeclaredMethod("setPassword", String.class);
		} catch (NoSuchMethodException e) {
            logger.warn(e.getMessage());
		} catch (SecurityException e) {
            logger.warn(e.getMessage());
		}

		if(m!=null){	//ע������
			convertAndInvoke(m, obj, defaultPwd, String.class);
		}
		
	}
	
	/**
	 *  ��excel��String���͵�����ת����bean�еĳ�Ա�������ͣ���ע�뵽������ȥ
	 */
	protected void convertAndInvoke(Method method, Object obj, String value, Class<?> type){
		if(method==null){
			throw new IllegalArgumentException("���뷽������Ϊ��");
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
