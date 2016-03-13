package com.tqe.controller;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.tqe.base.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tqe.po.Batches;
import com.tqe.po.Course;
import com.tqe.po.Leader;
import com.tqe.utils.SystemUtils;

/**
 * 学生评教控制器
 * @author 广路
 *
 */
@Controller
@RequestMapping("/admin")
public class LeaderEvalController extends BaseController{
	
	
	/**
	 * 显示领导中心主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/leaEval",method=RequestMethod.GET)
	public String leaEval(Model model,HttpSession session){
		Leader leader = (Leader) session.getAttribute("leader");
		
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		
		if(batches!=null){		//如果当前存在 可以评教的批次
			addSearcherResource(model);
			model.addAttribute("batches", batches);
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
		}
		return "leaEval/leaEval";
	}
	
	/**
	 * 显示领导中心主页面(查询结果)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/leaEval",method=RequestMethod.POST)
	public String lea1Eval(Model model,HttpSession session,String did,String cname ,String  cid,String tname){
		Leader leader = (Leader) session.getAttribute("leader");
		
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		
		if(batches!=null){		//如果当前存在 可以评教的批次
			addSearcherResource(model);
			//查询并加载课程数据信息
			HashMap<String,String> condition = new HashMap<String,String>();
			condition.put("did", did);
			condition.put("cname", cname);
			condition.put("cid", cid);
			condition.put("tname", tname);
			PageVO pageVO = new PageVO();
			pageVO.setFilters(condition);
			model.addAttribute("condition", condition);
			addSearcherResource(model);
			List<Course> list = courseService.findByCondition(pageVO);
			model.addAttribute("courseList",list);
			
			model.addAttribute("batches", batches);
			
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "您好，当前还没有可以评教的课程");
		}
		return "leaEval/leaEval";
	}
	
	/**
	 * 领导开始评教
	 * @param model
	 * @param cid
	 * @param cno
	 * @return
	 */
	@RequestMapping("/leaEval/eval/{cid}/{cno}")
	public String EvalleaEval(Model model,@PathVariable("cid") String cid ,@PathVariable("cno") Integer cno){
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailiableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "对不起，当前评教还未开始");
				return "leaEval/leaEval";
			}
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTableService.getById(batches.getLeadEvalId()));
			model.addAttribute("type", "leader");
			return "eval/eval";
		}
		return "redirect:/admin/leaEval";
	}
	
	
}
