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

/**
 * 学生评教控制器
 * @author 广路
 *
 */
@Controller
@RequestMapping("/admin")
public class StuEvalController extends BaseController{
	
	
	/**
	 * 显示学生评教中心主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/stuEval")
	public String stuEval(Model model,HttpSession session){
		Student stu = (Student) session.getAttribute("student");
		
		List<Course> courseList = courseService.findAll(stu.getSid());
		model.addAttribute("courseList", courseList);
		return "stuEval/stuEval";
	}
	
	@RequestMapping("/stuEval/eval/{cid}/{cno}")
	public String EvalstuEval(Model model,@PathVariable("cid") String cid ,@PathVariable("cno") Integer cno){
		System.out.println(cid+" "+cno);
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailiableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "对不起，当前评教还未开始");
				return "stuEval/stuEval";
			}
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTableService.getById(batches.getEvalTableId()));
			model.addAttribute("type", "student");
			return "eval/eval";
		}
		return "redirect:/stuEval/stuEval";
	}
	
	@RequestMapping("/stuEval/show/{id}")
	public String addstuEval(@PathVariable int id,Model model){
		return "stuEval/showstuEval";
	}
	
	@RequestMapping("/stuEval/save")
	public String savestuEval(){
		//System.out.println(JSON.toJSONString(stuEval, true));
		return "redirect:/admin/stuEval";
	}
}
