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
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.service.CourseServiceImpl;
import com.tqe.service.StudentServiceImpl;
import com.tqe.service.TeacherServiceImpl;

/**
 * 用来控制 将excel文件导入数据库的控制器
 * @author 广路
 *
 */
@Controller
public class ExcelImportController extends BaseController{
	@Resource(name="teacherExcelReader")
	private ExcelReader<Teacher> teacherReader;

	@Resource(name="courseExcelReader")
	private ExcelReader<Course> courseReader;
	
	@Resource(name="studentExcelReader")
	private ExcelReader<Student> studentReader;

	

	@RequestMapping("/excelImport/teacher")
	public String excelImportTeacher(){
		System.out.println("ok");
		try {
			List<Teacher> teacherList = teacherReader.getAll("d:/教师信息表.xls",true);
			teacherService.saveAll(teacherList);


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
				courseService.saveAll(courseList,season);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return "reditect:/admin/admin";
		
	}
	
	@RequestMapping("/excelImport/student")
	public String excelImportStudent(){
		System.out.println("ok");
			try {
				List<Student> courseList =  studentReader.getAll("d:/学生信息表.xls",true);
				studentService.saveAll(courseList);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		return "redirect:/admin/admin";
		
	}

}
