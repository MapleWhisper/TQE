package com.tqe.excelreader;

import com.tqe.po.Course;
import com.tqe.po.SC;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 用户实现 Student-Course 学生选课EXCEL的读取
 */
@Component
public class ScExcelReader extends ExcelReader<SC> {

	/**
	 * 检查文件是否是sc文件
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 2);
		
		return list.contains("课程号")&&list.contains("课序号")&&list.contains("学生学号");
	}


}
