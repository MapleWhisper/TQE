package com.tqe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.po.EvalTable;
import com.tqe.service.EvalTableServiceImpl;

@Controller
@RequestMapping("/admin")
public class EvalTableController {
	
	@Autowired
	private EvalTableServiceImpl evalTableService;
	
	@RequestMapping("/evalTable")
	public String evalTable(Model model){
		List<EvalTable> list = evalTableService.findAll();
		model.addAttribute("evalTableList",list);
		return "evalTable/evalTable";
	}
	
	@RequestMapping("/evalTable/add")
	public String addEvalTable(){
		return "evalTable/addEvalTable";
	}
	
	@RequestMapping("/evalTable/show/{id}")
	public String addEvalTable(@PathVariable int id,Model model){
		model.addAttribute("evalTable",evalTableService.getById(id));
		return "evalTable/showEvalTable";
	}
	
	@RequestMapping("/evalTable/save")
	public String saveEvalTable(@ModelAttribute()EvalTable evalTable){
		//System.out.println(JSON.toJSONString(evalTable, true));
		evalTableService.save(evalTable);
		return "redirect:/admin/evalTable";
	}
}
