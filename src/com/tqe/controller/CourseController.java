package com.tqe.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.model.CourseModel;
import com.tqe.po.Admin;
import com.tqe.po.Batches;
import com.tqe.po.Course;
import com.tqe.po.StuTable;
import com.tqe.po.TeaTable;
import com.tqe.utils.SystemUtils;



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
		List<Course> list = courseService.findAll();
		model.addAttribute("courseList",list);
		return "course/course";				//直接返回  前缀加 字符串+jsp的页面
	}
	
	/**
	 * 显示课程评教详情
	 * @return
	 */
	@RequestMapping("/course/show/{cid}/{cno}")
	public String showCourse(Model model,@PathVariable String cid,@PathVariable Integer cno){
		Course course = courseService.getById(cid,cno);
		CourseModel courseModel = new CourseModel();
		List<Batches> batchesList = batchesService.findAllBySeason(SystemUtils.getSeason());	//默认得到当前学期的所有批次
		
		for(Batches b : batchesList){	//遍历所有得到的批次列表
			List<StuTable> stuTableList = evalService.findAllStuTableByCourse(cid, cno, b.getId());
			List<TeaTable> teaTableList = evalService.findAllTeaTableByCourse(cid, cno, b.getId());
			CourseModel.Batches batches = new CourseModel.Batches();
			batches.setStuTableList(stuTableList);
			batches.setTeaTableList(teaTableList);
			batches.setBatches(b);
			courseModel.getBatchesList().add(batches);
		}
		model.addAttribute("course", course);
		model.addAttribute("courseModel",courseModel);
		return "course/showCourse";	//转到添加页面
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
