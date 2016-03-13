package com.tqe.controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqe.po.Admin;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.po.User;
import com.tqe.utils.MD5Utils;

@Controller
public class IndexController extends BaseController{

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
	public String resetPwd( Integer id ,String pwd,String oldPwd,
			Model model,HttpSession session){
		User user = new User();
		String Md5oldPwd = MD5Utils.string2MD5(oldPwd);
		if(session.getAttribute("admin")!=null){
			Admin admin = adminService.getById(id);
			if(admin.getPassword().equalsIgnoreCase(Md5oldPwd)){	//如果密码相等

				user.setId(admin.getId() + "");
				user.setUsername(admin.getUsername());
				user.setPassword(pwd);
				user.setType("admin");

			}
		}else if(session.getAttribute("teacher")!=null){
			Teacher teacher = teacherService.getById(id);
			if(teacher.getPassword().equalsIgnoreCase(Md5oldPwd)){	//如果密码相同
				user.setId(teacher.getId());
				user.setPassword(pwd);
				user.setType("teacher");
			}
		}else if(session.getAttribute("student")!=null){
			Student student = studentService.getById(id+"");
			if(student.getPassword().equalsIgnoreCase(Md5oldPwd)){	//如果密码相同
				user.setId(student.getSid()+"");
				user.setPassword(pwd);
				user.setType("student");
			}
		}else{
			model.addAttribute("error","原密码错误");
			return "resetPwd";
		}
		commonService.updatePwd(user);
		return "redirect:/logout";

	}
	
	@RequestMapping("getMajor/{did}")
	@ResponseBody
	public Object getMajor(@PathVariable Integer did){
		return majorService.findAllByDid(did);
	}
	
	@RequestMapping("getClazz/{did}/{mid}")
	@ResponseBody
	public Object getClazz(@PathVariable Integer did,@PathVariable Integer mid){
		return clazzService.findAllByDidMid(did, mid);
				
	}
	
	@RequestMapping("error")
	public String error(Model model,HttpSession session){
		model.addAttribute("msg",session.getAttribute("msg"));
		return "error";
	}
}
