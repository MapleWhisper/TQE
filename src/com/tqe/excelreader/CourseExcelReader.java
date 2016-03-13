package com.tqe.excelreader;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tqe.dao.TeacherDao;
import com.tqe.po.Course;
import com.tqe.po.Teacher;

@Component
public class CourseExcelReader extends ExcelReader<Course> {
	public static void main(String[] args) throws FileNotFoundException {
		List<Course> list = new CourseExcelReader().getAll("d:/�γ̰���Ϣ���γ�-��ʦ��Ӧ��ϵ.xls");
		//System.out.println(list.size());
		
		for(Course c: list){
			System.out.println(c.getName()+"\t"+c.getCid()+"\t"+c.getCno()+"\t");
		}
	}
	
	
	
	/**
	 * ����ļ��Ƿ��ǽ�ʦ�ļ�
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 2);
		
		return list.contains("�γ̺�")&&list.contains("�γ�����");
	}


}
