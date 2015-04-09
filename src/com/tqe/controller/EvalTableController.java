package com.tqe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showEvalTable(@PathVariable int id,Model model){
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
}
