package com.tqe.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.dao.TeacherDao;
import com.tqe.po.Admin;
import com.tqe.po.Teacher;
import com.tqe.po.User;
import com.tqe.service.AdminServiceImpl;
import com.tqe.service.CommonServiceImpl;
import com.tqe.service.TeacherServiceImpl;
import com.tqe.utils.MD5Utils;

@Controller
public class IndexController {
	@Resource(name="teacherDao")
	private  TeacherDao teacherDao;
	
	@Autowired
	private AdminServiceImpl adminService;
	
	@Autowired
	private TeacherServiceImpl teacherService;
	
	@Autowired
	private CommonServiceImpl commonService;
	
	/**
	 * 进入首页 登陆页面
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model){
		return "index";
	}
	
	@RequestMapping("toIndex")
	public String toIndex(){
		return "redirect:/index";
	}
	
	/**
	 * 
	 * 修改管理员密码 页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/resetPwdUI")
	public String resetPwdUI(){
		
		return "resetPwd";
	}
	
	/**
	 * 
	 * 修改管理员密码
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/resetPwd")
	public String resetPwd( int id ,String pwd,String oldPwd,
				Model model,HttpSession session){
		String Md5oldPwd = MD5Utils.string2MD5(oldPwd);
		if(session.getAttribute("admin")!=null){
			Admin admin = adminService.getById(id);
			if(admin.getPassword().equals(Md5oldPwd)){	//如果密码相等
				User user = new User();
				user.setId(admin.getId());
				user.setUsername(admin.getUsername());
				user.setPassword(pwd);
				user.setType("admin");
				commonService.updatePwd(user);
				return "redirect:/logout";
			}
		}else if(session.getAttribute("teacher")!=null){
			Teacher teacher = teacherService.getById(id);
			if(teacher.getPassword().endsWith(Md5oldPwd)){	//如果密码相同
				User user = new User();
				user.setId(teacher.getId());
				user.setPassword(pwd);
				user.setType("teacher");
				commonService.updatePwd(user);
				return "redirect:/logout";
			}
		}
		model.addAttribute("error","原密码错误");
		return "resetPwd";
		
		
	}
}
