package com.tqe.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.excelreader.ExcelReader;
import com.tqe.po.Course;
import com.tqe.po.Teacher;
import com.tqe.service.CourseServiceImpl;
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

	@Resource(name="courseExcelReader")
	private ExcelReader<Course> courseReader;

	@Autowired
	private TeacherServiceImpl teacherServiceImpl;

	@Autowired
	private CourseServiceImpl courseServiceImpl;

	@RequestMapping("/excelImport/teacher")
	public String excelImportTeacher(){
		System.out.println("ok");
		try {
			List<Teacher> teacherList = teacherReader.getAll("d:/教师信息表.xls",true);
			teacherServiceImpl.saveAll(teacherList);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "reditect:/admin/admin";
	}
	@RequestMapping("/excelImport/course/{season}")
	public String excelImportCourse(@PathVariable("season")String season){
		System.out.println("ok");
		if(StringUtils.hasText(season)){
			try {
				List<Course> courseList =  courseReader.getAll("d:/课程班信息：课程-老师对应关系.xls");
				courseServiceImpl.saveAll(courseList,season);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return "reditect:/admin/admin";
		
	}

}
