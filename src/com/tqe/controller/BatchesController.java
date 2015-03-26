package com.tqe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tqe.po.Batches;
import com.tqe.service.BatchesServiceImpl;


@Controller
@RequestMapping("/admin")
public class BatchesController extends BaseController {
	
	@Autowired
	private BatchesServiceImpl batchesService;
	
	@RequestMapping("/batches")
	public String batches(Model model){
		List<Batches> list = batchesService.findAll();
		model.addAttribute("batchesList",list);
		return "batches/batches";
	}
	
	@RequestMapping("/batches/add")
	public String addbatches(){
		return "batches/addBatches";
	}
	
	
	
	@RequestMapping("/batches/show/{id}")
	public String addbatches(@PathVariable int id,Model model){
		model.addAttribute("batches",batchesService.getById(id));
		return "batches/showBatches";
	}
	
	@RequestMapping("/batches/save")
	public String savebatches(@ModelAttribute()Batches batches){
		batchesService.save(batches);
		return "redirect:/admin/batches";
	}
	
	@RequestMapping("/batches/update")
	public void updatebatches(@ModelAttribute()Batches batches){
		
		if(batches.getBeginDate()==null || batches.getEndDate()==null || batches.getBeginDate().getTime()>=batches.getEndDate().getTime()){
			return;
		}
		Batches b = batchesService.getById(batches.getId());
		if(b!=null){
			b.setBeginDate(batches.getBeginDate());
			b.setEndDate(batches.getEndDate());
		}
		batchesService.update(b);
	}
	
	@RequestMapping("/batches/defaultEval/{bid}/{eid}")
	public String defaultEval(@PathVariable Integer bid,@PathVariable Integer eid){
		if(bid==null || eid==null){
			return "redirect:/admin/batches";
		}
		
		
		Batches b = batchesService.getById(bid);
		b.setEvalTableId(eid);
		batchesService.update(b);
		return "redirect:/admin/batches/show/"+bid;
			
	}
}
