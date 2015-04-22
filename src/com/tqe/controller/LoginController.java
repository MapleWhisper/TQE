package com.tqe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tqe.po.Admin;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.po.User;

/**
 * 登陆控制器
 * @author 广路
 *
 */
@Controller
public class LoginController extends BaseController{
	
	
	
	/**
	 * 用户登陆
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@ModelAttribute User user,HttpSession session, 
				@RequestParam String valifCode,Model model){
		//如果输入的验证码不正确
		if( !valifCode.equals(session.getAttribute("valifCode") ))  {
			model.addAttribute("error","验证码错误");
			return "index";
		}
		String type = user.getType();
		if(type.endsWith("admin")){
			Admin a =adminService.login(user);
			if(a!=null){
				System.out.println("管理员登陆");
				session.setAttribute("admin", a);
				session.removeAttribute("teacher");
				session.removeAttribute("student");
				return "redirect:/admin/admin";
			}
		}else if(type.endsWith("teacher")){
			Teacher t = teacherService.login(user);
			if(t!=null){
				System.out.println("教师登陆");
				session.setAttribute("teacher", t);
				session.removeAttribute("student");
				session.removeAttribute("admin");
				return "redirect:/admin/teaEval";
			}
		}else{
			Student stu = studentService.login(user);
			if(stu!=null){
				System.out.println("学生登陆");
				session.setAttribute("student", stu);
				session.removeAttribute("teacher");
				session.removeAttribute("admin");
				return "redirect:/admin/stuEval";
			}
			//return "redirect:/admin/admin";
		}
		model.addAttribute("error","用户名或密码错误");
		return "index";
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("student");
		session.removeAttribute("teacher");
		session.removeAttribute("admin");
		
		return "redirect:/index";
	}
}
