package com.tqe.excelreader;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tqe.po.Course;
import com.tqe.po.Student;

@Component
public class StudentExcelReader extends ExcelReader<Student> {
	
	public static void main(String[] args) throws FileNotFoundException {
		List<Student> list = new StudentExcelReader().getAll("d:/ѧ����Ϣ��.xls");
		//System.out.println(list.size());
		
		for(Student s: list){
			System.out.println(s.getName()+"\t"+s.getSid()+"\t");
		}
	}
	
	
	
	/**
	 * ����ļ��Ƿ��ǽ�ʦ�ļ�
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 0);
		
		return list.contains("ѧ��")&&list.contains("�༶");
	}
	
	
}
