package com.tqe.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.tqe.po.Data;
import com.tqe.po.Teacher;
import com.tqe.service.TeacherServiceImpl;

@Controller()
@RequestMapping("/admin")
public class TeacherController extends BaseController{

	@Resource(name="teacherServiceImpl")
	private  TeacherServiceImpl teacherService;
	
	@RequestMapping("/tea")
	public String tea(Model model){
		System.out.println("tea");
		return "teacher/teacher";
	}
	
	@RequestMapping(value="/teacher",method={RequestMethod.GET})
	public String teacher(Model model){
		System.out.println("hello");
		addSercherResource(model);
		//model.addAttribute("teacherList", teacherService.findAll());
		return "teacher/teacher";
	}
	
	@RequestMapping(value="/teacher",method={RequestMethod.POST})
	public String teacher1(Model model,String did,String tname,String tid){
		addSercherResource(model);
		HashMap<String,String> condition = new HashMap<String,String>();
		condition.put("did", did);
		condition.put("tname", tname);
		condition.put("tid", tid);
		model.addAttribute("condition", condition);
		model.addAttribute("teacherList", teacherService.findByCondition(condition));
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
