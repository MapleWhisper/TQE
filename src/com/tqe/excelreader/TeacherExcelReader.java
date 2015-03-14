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
import com.tqe.po.Teacher;

@Component
public class TeacherExcelReader extends ExcelReader<Teacher> {
	public static void main(String[] args) throws FileNotFoundException {
		List<Teacher> list = new TeacherExcelReader().getAll("d:/教师信息表.xls");
		//System.out.println(list.size());
		for(Teacher t: list){
			System.out.println(t.getName()+"\t"+t.getIdNumber()+"\t"+t.getId());
		}
	}
	
	
	
	/**
	 * 检查文件是否是教师文件
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 0);
		return list.contains("教师号");
	}
	
	
}
