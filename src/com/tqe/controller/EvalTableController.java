package com.tqe.controller;

import java.util.List;

import com.tqe.base.BaseResult;
import com.tqe.base.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tqe.po.EvalTable;

@Controller
@RequestMapping("/admin")
public class EvalTableController extends BaseController{
	
	
	/**
	 * 评教表
	 */
	@RequestMapping("/evalTable")
	public String evalTable(
            PageVO pageVO,
            Model model
    ){
		List<EvalTable> list = evalTableService.findAll(pageVO);
		model.addAttribute("evalTableList",list);
        model.addAttribute("condition",pageVO.getFilters());
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


    /**
     * 删除评教表
     */
    @RequestMapping("/evalTable/delete")
    @ResponseBody
    public BaseResult deleteEvalTable(
            @RequestParam()Integer eid
    ){
        if(eid==null || eid<=0){
            return BaseResult.createFailure("评教表id 不能为null！eid:"+eid);
        }
        try{
            evalTableService.delete(eid);
        }catch (Exception e){
            return BaseResult.createFailure("删除评教表失败！"+e.getMessage());
        }
        return BaseResult.createSuccess("删除评教表成功");

    }
}
