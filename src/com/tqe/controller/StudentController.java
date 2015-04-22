package com.tqe.controller;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
@RequestMapping("/admin")
public class StudentController extends BaseController{

	/**
	 * 显示学生数据界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.GET})
	public String student(Model model){
		addSercherResource(model);
		//model.addAttribute("studentList", studentService.findAll());
		return "student/student";
	}
	
	/**
	 * 显示学生数据界面 关键字查找
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.POST})
	public String serchStudent(Model model,String did,String mid,String cid,String sname,String sid,HttpSession session){
		addSercherResource(model);
		HashMap<String,String> condition = new HashMap<String,String>();
		condition.put("did", did);
		condition.put("mid", mid);
		condition.put("cid", cid);
		condition.put("sname", sname);
		condition.put("sid", sid);
		model.addAttribute("studentList", studentService.findByCondition(condition));
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
