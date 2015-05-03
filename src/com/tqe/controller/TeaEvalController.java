package com.tqe.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.po.Batches;
import com.tqe.po.Course;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.utils.SystemUtils;

/**
 * 教师评教
 * @author 广路
 *
 */
@Controller
@RequestMapping("/admin")
public class TeaEvalController extends BaseController{
	
	
	/**
	 * 显示教师评教中心主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/teaEval")
	public String teaEval(Model model,HttpSession session){
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		if(batches!=null){		//如果当前存在 可以评教的批次
			List<Course> courseList = courseService.findAllByTId(tea.getId(),batches.getId());	//得到所有的课程列表
			model.addAttribute("courseList", courseList);
			model.addAttribute("batches", batches);
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
		}
		return "teaEval/teaEval";
	}
	
	/**
	 * 显示教师评教学生中心主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/{cid}/{cno}/teaStuEval")
	public String teaStuEval(Model model,HttpSession session,@PathVariable String cid,@PathVariable Integer cno){
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		if(batches!=null){		//如果当前存在 可以评教的批次
			//List<Course> courseList = courseService.findAllByTId(tea.getId(),batches.getId());	//得到所有的课程列表
			List<Course> courseList = courseService.findAllByTid(tea.getId(),batches.getId());	//得到所有的课程列表
			model.addAttribute("courseList", courseList);
			model.addAttribute("batches", batches);
			
			List<Student> studentList = null;
			if(cid !=null && cno!=null){		//如果要查看学生，那么就列出学生列表
				Course course = courseService.getById(cid, cno);
				
				studentList = studentService.findAllByCId(cid,cno,tea.getId(),batches.getId());		
				if(studentList.size()%2!=0){
					Student s = new Student();
					s.setEvaled(true);
					studentList.add(s);
				}
				
				model.addAttribute("course", course);
				model.addAttribute("studentList", studentList);
			}
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
		}
		return "teaEval/teaStuEval";
	}
	
	/**
	 * 显示教师评教学生中心主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/teaStuEval")
	public String teaStuEval(Model model,HttpSession session){
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		if(batches!=null){		//如果当前存在 可以评教的批次
			//List<Course> courseList = courseService.findAllByTId(tea.getId(),batches.getId());	//得到所有的课程列表
			List<Course> courseList = courseService.findAllByTid(tea.getId(),batches.getId());	//得到所有的课程列表
			model.addAttribute("courseList", courseList);
			model.addAttribute("batches", batches);
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
		}
		return "teaEval/teaStuEval";
	}
	
	/**
	 * 教师开始评教
	 * @param model
	 * @param cid
	 * @param cno
	 * @return
	 */
	@RequestMapping("/teaEval/eval/{cid}/{cno}")
	public String EvalteaEval(Model model,@PathVariable("cid") String cid ,@PathVariable("cno") Integer cno){
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailiableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "对不起，当前评教还未开始");
				return "teaEval/teaEval";
			}
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTableService.getById(batches.getTeaEvalId()));
			model.addAttribute("type", "teacher");
			return "eval/eval";
		}
		return "redirect:/teaEval/teaEval";
	}
	
	
	/**
	 * 教师开始评教学生
	 * @param model
	 * @param cid
	 * @param cno
	 * @return
	 */
	@RequestMapping("/teaStuEval/eval/{cid}/{cno}/{sid}")
	public String evalTeaStuEval(Model model,@PathVariable("cid") String cid ,@PathVariable("cno") Integer cno,@PathVariable("sid") Integer sid){
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailiableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "对不起，当前评教还未开始");
				return "teaEval/teaStuEval";
			}
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTableService.getById(batches.getTeaStuEvalId()));
			model.addAttribute("sid", sid);
			model.addAttribute("type", "teaStu");
			return "eval/eval";
		}
		return "redirect:/admin/teaStuEval";
	}
	
	
}
