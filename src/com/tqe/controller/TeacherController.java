package com.tqe.controller;

import com.tqe.base.vo.PageVO;
import com.tqe.po.Course;
import com.tqe.po.Teacher;
import com.tqe.utils.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller()
@RequestMapping("/admin")
public class TeacherController extends BaseController{

	
	@RequestMapping("/tea")
	public String tea(Model model){
		return "teacher/teacher";
	}
	
	/**
	 * 显示教师列表主页面
	 */
	@RequestMapping(value="/teacher",method={RequestMethod.GET})
	public String teacher(Model model){
		addSearcherResource(model);
		//model.addAttribute("teacherList", teacherService.findAll());
		return "teacher/teacher";
	}
	
	/**
	 * 关键字 查询教师列表
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
	 */
	@RequestMapping("/teacher/show")
	public String show(
            Model model,
            @RequestParam String tid,
            @RequestParam(required = false) String season
    ){
		//List<Course> courseList = courseService.findAllByTId(tid);
        if(StringUtils.isBlank(tid)) {
            return sendError(model,"教师号不能为空！");
        }
        Teacher teacher = teacherService.getById(tid);
        if(StringUtils.isBlank(season)){
            season = SystemUtils.getSeason();
        }
        if(teacher==null){
            return sendError(model,"没有找到对应的教师信息！tid:"+tid);
        }

        PageVO pageVO = new PageVO(0,200);
        pageVO.getFilters().put("tid",tid);
        pageVO.getFilters().put("season",season);
        List<Course> list = courseService.findByCondition(pageVO);
        model.addAttribute("courseList", list);
        model.addAttribute("teacher",teacher);
        model.addAttribute("condition",pageVO.getFilters());
        return "teacher/showTeacher";
	}
	
	
	
}
