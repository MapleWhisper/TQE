package com.tqe.controller;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.tqe.po.Data;
import com.tqe.po.Teacher;
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
	
	@RequestMapping("/teacher/json")
	public void teacher(@RequestParam int start ,@RequestParam int length,HttpServletResponse response){
		System.out.println(start+"-"+length);
		List<Teacher> list = teacherService.findByPage(start, length);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Data<Teacher> data = new  Data<Teacher>(list);
		System.out.println(JSON.toJSONString(data,true));
		try {
			response.getWriter().println(JSON.toJSONString(data));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
