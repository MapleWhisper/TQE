package com.tqe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tqe.po.Admin;
import com.tqe.po.Leader;
import com.tqe.po.Privilege;
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

	Log logger = LogFactory.getLog(LoginController.class);

	private static List<String> users = new ArrayList<String>();
	static{
		users.add("student");
		users.add("teacher");
		users.add("leader");
		users.add("admin");
	}
	
	/**
	 * 用户登陆
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@ModelAttribute User user,
						HttpSession session,
						@RequestParam String valifCode,
						HttpServletResponse response,
						Model model){
		//如果输入的验证码不正确
		if( !valifCode.equals(session.getAttribute("valifCode") ))  {
			model.addAttribute("error","验证码错误");
			return "index";
		}

		String type = user.getType();

		//记住用户最后的登录方式
		Cookie cookie = new Cookie("loginType",type);
		cookie.setMaxAge(365*24*60*60);
		response.addCookie(cookie);

		if(type.equals("admin")) {
			Admin a =adminService.login(user);
			if(a!=null){
				System.out.println("管理员登陆");
				session.setAttribute("admin", a);
				removeOtherUser(session,"admin");
				List<Privilege> pList = privilegeService.findAdminAll();
				addPrivilege(session, pList);
				return "redirect:/admin/admin";
			}
		}else if(type.equals("teacher")){
			Teacher t = teacherService.login(user);
			if(t!=null){
				System.out.println("教师登陆");
				session.setAttribute("teacher", t);
				removeOtherUser(session,"teacher");
				List<Privilege> pList = privilegeService.findTeacherAll();
				addPrivilege(session, pList);
				return "redirect:/admin/teaEval";
			}
		}else if(type.equals("student")){
			Student stu = studentService.login(user);
			if(stu!=null){
				System.out.println("学生登陆");
				session.setAttribute("student", stu);
				removeOtherUser(session,"student");
				List<Privilege> pList = privilegeService.findStudentAll();
				addPrivilege(session, pList);
				return "redirect:/admin/stuEval";
			}
			//return "redirect:/admin/admin";
		}else if(type.equals("leader")){										//领导登陆
			Leader leader = leaderService.login(user);
			if(leader!=null){
				System.out.println("领导登陆");
				session.setAttribute("leader", leader);
				removeOtherUser(session,"leader"); 
				List<Privilege> pList = privilegeService.findLeaderAll();
				addPrivilege(session, pList);
				return "redirect:/admin/leaEval";
			}
		}else{
			logger.error("未知的登录角色！ " + type);
			model.addAttribute("error","为止的登录用户的角色！");
			return "index";

		}
		model.addAttribute("error","用户名或密码错误");
		return "index";
		
	}

	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		removeOtherUser(session, "");
		return "redirect:/index";
	}

	/**
	 * 添加权限信息到session中 用于前台进行权限的显示的判断
	 * @param session
	 * @param pList
	 */
	private void addPrivilege(HttpSession session,List<Privilege> pList){
		session.setAttribute("pList", pList);
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		for(Privilege p : pList){
			map.put(p.getUrl().substring(1), true);
		}
		session.setAttribute("pMap", map);
	}
	
	/**
	 * 用户登陆时，移除其他无关的角色，保证系统同一时刻只有一个角色能登陆
	 * @param session
	 * @param userTyle
	 */
	private void removeOtherUser(HttpSession session , String userTyle) {		
		for(String s : users){
			if(!s.equals(userTyle)){
				session.removeAttribute(s);
			}
		}
		
	}
}
