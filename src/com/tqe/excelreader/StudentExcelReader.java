package com.tqe.excelreader;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tqe.po.Course;
import com.tqe.po.Student;

@Component
public class StudentExcelReader extends ExcelReader<Student> {
	
	public static void main(String[] args) throws FileNotFoundException {
		List<Student> list = new StudentExcelReader().getAll("d:/学生信息表.xls");
		//System.out.println(list.size());
		
		for(Student s: list){
			System.out.println(s.getName()+"\t"+s.getSid()+"\t");
		}
	}
	
	
	
	/**
	 * 检查文件是否是教师文件
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 0);
		
		return list.contains("学号")&&list.contains("班级");
	}
	
	
}
