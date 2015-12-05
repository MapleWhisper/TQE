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
	 * 显示评教表列表
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
	 * @param id 评教表的ID
	 */
	@RequestMapping("/evalTable/show/{id}")
	public String showEvalTable(@PathVariable Integer id,Model model){
		EvalTable evalTable = evalTableService.getById(id);
		if(evalTable==null){
			return sendError(model,"您访问的评教表不存在");
		}
		model.addAttribute("evalTable",evalTable);
		return "evalTable/showEvalTable";
	}
	
	/**
	 * 保存评教表
	 * @param evalTable 需要被保存的评教表
	 */
	@RequestMapping("/evalTable/save")
	public String saveEvalTable(@ModelAttribute()EvalTable evalTable){
		
		evalTableService.save(evalTable);
		return "redirect:/admin/evalTable";
	}
	
	/**
	 * 修改评教表页面
	 * @param eid	需要被修改的平角表
	 */
	@RequestMapping("/evalTable/edit/{eid}")
	public String editEvalTable(@PathVariable Integer eid,Model model){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			return sendError(model,"您访问的评教表不存在");
		}
		model.addAttribute("evalTable",eTable);
		return "evalTable/editEvalTable";
	}
	
	/**
	 * 修改评教表
	 * @param evalTable 修改需要保存的评教表
	 */
	@RequestMapping("/evalTable/update")
	public String updateEvalTable(Integer eid,Model model,@ModelAttribute()EvalTable evalTable){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			return sendError(model,"您访问的评教表不存在");
		}
		eTable.setJsonString(JSON.toJSONString(evalTable));
		eTable.setTitle(evalTable.getTitle());
		eTable.setNote(evalTable.getNote());
		evalService.update(eTable);
		model.addAttribute("evalTable",eTable);
		return "redirect:/admin/evalTable";
	}
}
