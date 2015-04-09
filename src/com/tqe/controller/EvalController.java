package com.tqe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.tqe.po.EvalTable;
import com.tqe.po.StuTable;

@Controller
@RequestMapping("/admin")
public class EvalController extends BaseController{
	
	@RequestMapping(value={"/eval/save/{type}"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable, @ModelAttribute StuTable stuTable ,
			@PathVariable String type ,Model model){
		
		EvalTable e = evalTableService.getById(stuTable.getEid()).json2Object();
		e.setAns(e, evalTable);
		stuTable.setJsonString(JSON.toJSONString(e));
		try {
			evalService.saveStuTable(stuTable);
		} catch (Exception e1) {
			model.addAttribute("msg","该课程已经评价！不能重复评价！");
			
			e1.printStackTrace();
			return "error";
		}
		return "redirect:/admin/stuEval";
	}
	
	
	@RequestMapping("/eval/show/student/{id}")
	public String showEvalTable( @PathVariable int id ,Model model){
		StuTable stuTable = evalService.getStuTableById(id);
		EvalTable evalTable = JSON.parseObject(stuTable.getJsonString(),EvalTable.class);
		
		model.addAttribute("evalTable", evalTable);
		model.addAttribute("stuTable", stuTable);

		return "eval/showEval";
	}
}
