package com.tqe.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.excelreader.ExcelReader;
import com.tqe.po.Teacher;
import com.tqe.service.TeacherServiceImpl;

/**
 * 用来控制 将excel文件导入数据库的控制器
 * @author 广路
 *
 */
@Controller
public class ExcelImportController {
	@Resource(name="teacherExcelReader")
	private ExcelReader<Teacher> teacherReader;

	@Autowired
	private TeacherServiceImpl teacherServiceImpl;

	@RequestMapping("/excelImport/{type}")
	public String excelImport(@PathVariable("type")String type){
		System.out.println("ok");
		try {
			if(type.equals("teacher")){
				List<Teacher> teacherList = teacherReader.getAll("d:/教师信息表.xls",true);
				teacherServiceImpl.saveAll(teacherList);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "index";
	}

}
