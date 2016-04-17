package com.tqe.controller;


import java.util.List;
import javax.servlet.http.HttpSession;

import com.tqe.po.EvalTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.po.Batches;
import com.tqe.po.Course;
import com.tqe.po.Student;
import com.tqe.utils.SystemUtils;

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
		Batches batches = batchesService.getAvailableBatches(SystemUtils.getSeason());
		if(batches!=null){		//如果当前存在 可以评教的批次
			List<Course> courseList = courseService.findAllBySid(stu.getSid(), batches);	//得到所有的课程列表
			model.addAttribute("courseList", courseList);
			model.addAttribute("batches", batches);
			
		}else{		//如果当前没有评教批次
			model.addAttribute("message", "您好，当前还没有可以评教的课程~");
		}
		return "stuEval/stuEval";
	}
	
	/**
	 * 学生开始评教
	 * @param model
	 * @param cid
	 * @param cno
	 * @return
	 */
	@RequestMapping("/stuEval/eval/{cid}/{cno}")
	public String EvalstuEval(Model model,@PathVariable("cid") String cid ,@PathVariable("cno") Integer cno){
		Course c = courseService.getById(cid, cno);
		if(c!=null){	//如果要评教的课程不为空
			Batches batches = batchesService.getAvailableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("message", "您好，当前评教还未开始~");
				return "error";
			}
            EvalTable evalTable = evalTableService.getById(batches.getStuEvalId());
            if(evalTable==null){
                return sendError(model,"对不起当前评教批次还没有设置学生评教表，请联系管理设置评教表");
            }
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable",evalTable );
			model.addAttribute("type", "student");
			return "eval/eval";
		}
		return "redirect:/admin/stuEval";
	}
	
	@RequestMapping("/stuEval/show/{id}")
	public String addstuEval(@PathVariable int id,Model model){
		return "stuEval/showstuEval";
	}
	
	@RequestMapping("/stuEval/save")
	public String savestuEval(){
		return "redirect:/admin/stuEval";
	}
}
