package com.tqe.controller;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.service.TeacherServiceImpl;

@Controller()
@RequestMapping("/admin")
public class StudentController extends BaseController{

	
	@RequestMapping("/student")
	public String student(Model model){
		model.addAttribute("studentList", studentService.findAll());
		return "student/student";
	}
	
	@RequestMapping("/student/add")
	public String addStudent(){
		return "student/addStudent";
	}
	
	@RequestMapping("/student/save")
	public String saveStudent(){
		return "redirect:/admin/student";
	}
	
}
