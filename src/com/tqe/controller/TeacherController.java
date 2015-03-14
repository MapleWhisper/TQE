package com.tqe.controller;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.service.TeacherServiceImpl;

@Controller()
@RequestMapping("/admin")
public class TeacherController{

	@Resource(name="teacherServiceImpl")
	private  TeacherServiceImpl teacherService;
	
	@RequestMapping("/tea")
	public String tea(Model model){
		System.out.println("tea");
		return "teacher/teacher";
	}
	
	@RequestMapping("/teacher")
	public String teacher(Model model){
		System.out.println("hello");
		model.addAttribute("teacherList", teacherService.findAll());
		return "teacher/teacher";
	}
	
}
