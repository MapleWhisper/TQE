package com.tqe.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * �������� ��excel�ļ��������ݿ�Ŀ�����
 * @author ��·
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
		try {
			List<Teacher> teacherList = teacherReader.getAll("d:/��ʦ��Ϣ��.xls",true);
			teacherService.saveAll(teacherList);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "reditect:/admin/admin";
	}
	@RequestMapping("/excelImport/course/{season}")
	public String excelImportCourse(@PathVariable("season")String season,Model model){
		if(!StringUtils.hasText(season)){	//���sessonΪ�գ���ô���ܼ���
			model.addAttribute("msg", "season����Ϊ�գ�");
			return "error";
		}
		if(StringUtils.hasText(season)){
			try {
				List<Course> courseList =  courseReader.getAll("d:/�γ̰���Ϣ���γ�-��ʦ��Ӧ��ϵ.xls");
				courseService.saveAll(courseList,season);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return "reditect:/admin/admin";
		
	}
	
	@RequestMapping("/excelImport/student")
	public String excelImportStudent(){
			try {
				List<Student> courseList =  studentReader.getAll("d:/ѧ����Ϣ��.xls",true);
				studentService.saveAll(courseList);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		return "redirect:/admin/admin";
		
	}

}
