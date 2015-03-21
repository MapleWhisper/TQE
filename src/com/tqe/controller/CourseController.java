package com.tqe.controller;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.po.Admin;
import com.tqe.service.AdminServiceImpl;
import com.tqe.service.CourseServiceImpl;



/**
 * 课程类
 * 
 * @author 于广路
 *
 */
@Controller()
@RequestMapping("/admin")
public class CourseController extends BaseController{
	
	
	/**
	 * 显示课程列表页面
	 * @return
	 */
	@RequestMapping("/course")
	public String course(Model model){
		model.addAttribute("courseList",courseService.findAll());
		return "course/course";				//直接返回  前缀加 字符串+jsp的页面
	}
	
	/**
	 * 增加课程页面
	 * @return
	 */
	@RequestMapping("/course/add")
	public String addCourse(Model model){
		//model.addAttribute("privilegeList", privilegeService.findAll());		
		return "course/addCourse";	//转到添加页面
	}
	
	/**
	 * 保存课程
	 * @return
	 */
	@RequestMapping("/course/save")
	public String save(@ModelAttribute Admin admin){
		return "redirect:/admin/course";	//保存完成后  跳转到管理员列表页面
	}
	
	/**
	 * 删除课程
	 * @param id 需要删除课程Id
	 * 	 * @return	返回到管理员列表页面
	 */
	@RequestMapping("/course/delete/{id}")
	public String delete(@PathVariable int id){
		
		//adminService.delete(id);
		
		return "redirect:/admin/course";	//跳到管理员列表页面
	}
}
