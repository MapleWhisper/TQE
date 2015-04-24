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
	 * @param model
	 * @return
	 */
	@RequestMapping("/evalTable")
	public String evalTable(Model model){
		List<EvalTable> list = evalTableService.findAll();
		model.addAttribute("evalTableList",list);
		return "evalTable/evalTable";
	}
	
	/**
	 * 添加评教表页面
	 * @return
	 */
	@RequestMapping("/evalTable/add")
	public String addEvalTable(){
		return "evalTable/addEvalTable";
	}
	
	/**
	 * 显示评教表
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/evalTable/show/{id}")
	public String showEvalTable(@PathVariable Integer id,Model model){
		EvalTable evalTable = evalTableService.getById(id);
		if(evalTable==null){
			model.addAttribute("msg", "您访问的评教表不存在");
			return "error";
		}
		model.addAttribute("evalTable",evalTable);
		return "evalTable/showEvalTable";
	}
	
	/**
	 * 保存评教表
	 * @param evalTable
	 * @return
	 */
	@RequestMapping("/evalTable/save")
	public String saveEvalTable(@ModelAttribute()EvalTable evalTable){
		
		evalTableService.save(evalTable);
		return "redirect:/admin/evalTable";
	}
	
	/**
	 * 修改评教表页面
	 * @param evalTable
	 * @return
	 */
	@RequestMapping("/evalTable/edit/{eid}")
	public String editEvalTable(@PathVariable Integer eid,Model model){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			model.addAttribute("msg", "您访问的评教表不存在");
			return "error";
		}
		model.addAttribute("evalTable",eTable);
		return "evalTable/editEvalTable";
	}
	
	/**
	 * 修改评教表
	 * @param evalTable
	 * @return
	 */
	@RequestMapping("/evalTable/update")
	public String updateEvalTable(Integer eid,Model model,@ModelAttribute()EvalTable evalTable){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			model.addAttribute("msg", "您访问的评教表不存在");
			return "error";
		}
		eTable.setJsonString(JSON.toJSONString(evalTable));
		eTable.setTitle(evalTable.getTitle());
		eTable.setNote(evalTable.getNote());
		evalService.update(eTable);
		model.addAttribute("evalTable",eTable);
		return "redirect:/admin/evalTable";
	}
}
