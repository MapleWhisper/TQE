package com.tqe.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.tqe.base.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.po.Batches;
import com.tqe.service.BatchesServiceImpl;
import org.springframework.web.bind.annotation.ResponseBody;


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
	public @ResponseBody
	BaseResult
	updateBatches(@ModelAttribute() Batches batches) throws IOException{
		if(batches.getBeginDate()==null || batches.getEndDate()==null || batches.getBeginDate().getTime()>=batches.getEndDate().getTime()){
			return BaseResult.createFailure("起始时间不能为空 截止时间不能为空 起始时间必须要要小于截止时间");
		}
		Date latest = batchesService.getLatestDate(batches.getId());
		if(latest!=null && batches.getBeginDate().getTime() <= latest.getTime()){
			return BaseResult.createFailure("设定日期失败! 您设置的评教批次的起始日期必须大于最新批次的截止日期:"+new SimpleDateFormat("yyyy-MM-dd").format(latest)+" 请重新设置开始日期或者截止日期");
		}
		Batches b = batchesService.getById(batches.getId());
		if(b!=null){
			b.setBeginDate(batches.getBeginDate());
			b.setEndDate(batches.getEndDate());
		}

		batchesService.update(b);
		return BaseResult.createSuccess("修改日期成功！");
	}
	
	
	/**
	 * 
	 * 设置评教批次的默认评价表
	 * @param bid 评教批次Id
	 * @param eid 评教表Id
	 * @param type student:学生 teacher:教师 lead:领导
	 * @return
	 */
	@RequestMapping("/batches/setEval/{type}/{bid}/{eid}")
	public String defaultEval(@PathVariable Integer bid,@PathVariable Integer eid,@PathVariable String type,Model model){
		
		if(bid==null || eid==null){
			return "redirect:/admin/batches";
		}
		Batches b = batchesService.getById(bid);
			
		if(StringUtils.hasText(type) && b!=null){		//如果有内容
			if(type.equals("student")){
				
				b.setStuEvalId(eid);
				batchesService.update(b);
				return "redirect:/admin/batches/show/"+bid;
			}
			if(type.equals("teacher")){
				b.setTeaEvalId(eid);
				batchesService.update(b);
				
				return "redirect:/admin/batches/show/"+bid;
			}
			if(type.equals("lead")){
				b.setLeadEvalId(eid);
				batchesService.update(b);
				return "redirect:/admin/batches/show/"+bid;
			}
			if(type.equals("teaStu")){
				b.setTeaStuEvalId(eid);
				batchesService.update(b);
				return "redirect:/admin/batches/show/"+bid;
			}
			
		}
		model.addAttribute("msg", "路径参数不正确:/batches/setEval/{type}/{bid}/{eid}\n");
		return "error";
		
			
	}

}
