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
	 * ��ʾ���̱��б�
	 */
	@RequestMapping("/evalTable")
	public String evalTable(Model model){
		List<EvalTable> list = evalTableService.findAll();
		model.addAttribute("evalTableList",list);
		return "evalTable/evalTable";
	}
	
	/**
	 * ������̱�ҳ��
	 */
	@RequestMapping("/evalTable/add")
	public String addEvalTable(){
		return "evalTable/addEvalTable";
	}
	
	/**
	 * ��ʾ���̱�
	 * @param id ���̱��ID
	 */
	@RequestMapping("/evalTable/show/{id}")
	public String showEvalTable(@PathVariable Integer id,Model model){
		EvalTable evalTable = evalTableService.getById(id);
		if(evalTable==null){
			return sendError(model,"�����ʵ����̱�����");
		}
		model.addAttribute("evalTable",evalTable);
		return "evalTable/showEvalTable";
	}
	
	/**
	 * �������̱�
	 * @param evalTable ��Ҫ����������̱�
	 */
	@RequestMapping("/evalTable/save")
	public String saveEvalTable(@ModelAttribute()EvalTable evalTable){
		
		evalTableService.save(evalTable);
		return "redirect:/admin/evalTable";
	}
	
	/**
	 * �޸����̱�ҳ��
	 * @param eid	��Ҫ���޸ĵ�ƽ�Ǳ�
	 */
	@RequestMapping("/evalTable/edit/{eid}")
	public String editEvalTable(@PathVariable Integer eid,Model model){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			return sendError(model,"�����ʵ����̱�����");
		}
		model.addAttribute("evalTable",eTable);
		return "evalTable/editEvalTable";
	}
	
	/**
	 * �޸����̱�
	 * @param evalTable �޸���Ҫ��������̱�
	 */
	@RequestMapping("/evalTable/update")
	public String updateEvalTable(Integer eid,Model model,@ModelAttribute()EvalTable evalTable){
		
		EvalTable eTable = evalTableService.getById(eid);
		if(eTable==null){
			return sendError(model,"�����ʵ����̱�����");
		}
		eTable.setJsonString(JSON.toJSONString(evalTable));
		eTable.setTitle(evalTable.getTitle());
		eTable.setNote(evalTable.getNote());
		evalService.update(eTable);
		model.addAttribute("evalTable",eTable);
		return "redirect:/admin/evalTable";
	}
}
