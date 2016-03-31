package com.tqe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tqe.po.EvalTable;

@Controller
@RequestMapping("/admin")
public class EvalTableController extends BaseController{
	
	
	/**
	 * 评教表
	 */
	@RequestMapping("/evalTable")
	public String evalTable(Model model){
		List<EvalTable> list = evalTableService.findAll();
		model.addAttribute("evalTableList",list);
		return "evalTable/evalTable";
	}
	
	/**
	 * 添加评教表页面
	 */
	@RequestMapping("/evalTable/add")
	public String addEvalTable(){
		return "evalTable/addEvalTable";
	}
	
	/**
	 * 显示评教表
	 */
	@RequestMapping("/evalTable/show/{id}")
	public String showEvalTable(@PathVariable Integer id,Model model){
		EvalTable evalTable = evalTableService.getById(id);
		if(evalTable==null){
			return sendError(model,"没有找到指定的评教表");
		}
		model.addAttribute("evalTable",evalTable);
		return "evalTable/showEvalTable";
	}
	
	/**
	 * 保存评教表
	 */
	@RequestMapping("/evalTable/save")
	public String saveEvalTable(@ModelAttribute()EvalTable evalTable){
		
		evalTableService.save(evalTable);
		return "redirect:/admin/evalTable";
	}
	
	/**
	 * 修改评教表页面
	 */
	@RequestMapping("/evalTable/edit/{eid}")
	public String editEvalTable(@PathVariable Integer eid,Model model){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			return sendError(model,"没有找到可以修改的评教表");
		}
		model.addAttribute("evalTable",eTable);
		return "evalTable/editEvalTable";
	}
	
	/**
	 * 更新评教表
	 */
	@RequestMapping("/evalTable/update")
	public String updateEvalTable(Integer eid,Model model,@ModelAttribute()EvalTable evalTable){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			return sendError(model,"没有找到要更新的评教表");
		}
        evalTable.setId(eid);
		evalTableService.update(evalTable);
		model.addAttribute("evalTable",eTable);
		return "redirect:/admin/evalTable";
	}
}
