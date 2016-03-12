package com.tqe.controller;

import com.tqe.base.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
@RequestMapping("/admin")
public class TeacherController extends BaseController{

	
	@RequestMapping("/tea")
	public String tea(Model model){
		return "teacher/teacher";
	}
	
	/**
	 * 显示教师列表主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/teacher",method={RequestMethod.GET})
	public String teacher(Model model){
		addSearcherResource(model);
		//model.addAttribute("teacherList", teacherService.findAll());
		return "teacher/teacher";
	}
	
	/**
	 * 关键字 查询教师列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/teacher",method={RequestMethod.POST})
	public String teacher1(
			Model model,
			PageVO pageVO){
		addSearcherResource(model);
		model.addAttribute("condition", pageVO.getFilters());
		model.addAttribute("teacherList", teacherService.findByPageVO(pageVO));
		return "teacher/teacher";
	}
	
	/**
	 * 显示教师详情
	 * @param model
	 * @param tid
	 * @return
	 */
	@RequestMapping("/teacher/show/{tid}")
	public String show(Model model,@PathVariable String tid){
		//List<Course> courseList = courseService.findAllByTId(tid);
		return "teacher/showTeacher";
	}
	
	
	
}
