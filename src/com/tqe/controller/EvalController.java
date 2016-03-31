package com.tqe.controller;

import javax.servlet.http.HttpSession;

import com.tqe.po.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/admin")
@SessionAttributes(value = {"leader","teacher"})
public class EvalController extends BaseController{

	Log logger = LogFactory.getLog(EvalController.class);
	
	/**
	 * 
	 * 学生评教管理保存
	 */
	@RequestMapping(value={"/eval/save/student"},method={RequestMethod.POST})
	public String evalTable(
            @ModelAttribute EvalTable evalTable,
            @ModelAttribute StuResultTable stuTable ,
            Model model,
            HttpSession session){

		
		try {
			preSaveTable(evalTable, stuTable);
			Student stu = (Student) session.getAttribute("student");
			if(stu==null){
				return sendError(model,"当前登录的角色不是学生",logger);
			}
			stuTable.setSname(stu.getName());
			evalService.saveStuTable(stuTable);
		} catch (Exception e1) {
			return sendError(model,"保存评教表失败!",logger,e1);
		}
		return "redirect:/admin/stuEval";
	}
	
	
	/**
	 * 
	 * 保存领导评教表
	 */
	@RequestMapping(value={"/eval/save/leader"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable,
							 @ModelAttribute LeaResultTable leaTable ,
							 @ModelAttribute Leader leader,
							 Model model){
		
		
		try {
			if(leader==null){
				return sendError(model,"当前登录的角色不是领导",logger);
			}
			preSaveTable(evalTable, leaTable);
			evalService.saveLeaTable(leaTable);
		} catch (Exception e1) {
			return sendError(model,"保存领导评教表失败",logger,e1);
		}
		return "redirect:/admin/leaEval";
	}
	
	/**
	 * 保存教师评教表
	 */
	@RequestMapping(value={"/eval/save/teacher"},method={RequestMethod.POST})
	public String evalTable(
			@ModelAttribute EvalTable evalTable,
			@ModelAttribute TeaResultTable teaTable ,
			@ModelAttribute Teacher teacher,
			Model model){
		
		try {
			if(teacher == null){
				String msg = "当前登录的角色不是教师";
				return sendError(model,msg,logger);

			}
			preSaveTable(evalTable, teaTable);
			evalService.saveTeaTable(teaTable);
		} catch (Exception e1) {

			return sendError(model,"保存教师评教失败！");
		}
		return "redirect:/admin/teaEval";
	}
	
	/**
	 * 保存教师对学生的评教表
	 *
	 */
	@RequestMapping(value={"/eval/save/teaStu"},method={RequestMethod.POST})
	public String teaStuevalTable(
			@ModelAttribute EvalTable evalTable,
			@ModelAttribute TeaStuResultTable teaStuTable ,
			@ModelAttribute Teacher teacher,
			Model model){
		
		try {
			if(teacher==null){
				return sendError(model,"当前登录的角色不是教师",logger);
			}
			teaStuTable.setSname(studentService.getNameById(teaStuTable.getSid()).getName());

			preSaveTable(evalTable, teaStuTable);
			evalService.saveTeaStuTable(teaStuTable);
		} catch (Exception e1) {

			sendError(model,"保存教师评价学生表失败",logger,e1);
			return "error";
		}
		String cid = teaStuTable.getCid();
		Integer cno = teaStuTable.getCno();
		StringBuilder sb = new StringBuilder();
		String view = sb.append("redirect:/admin/").append(cid).append("/").append(cno).append("/teaStuEval").toString();
		return view;
	}


	
	
	/**
	 * 显示评教表详情
	 */
	@RequestMapping("/eval/show/{type}/{id}")
	public String showEvalTable( @PathVariable Integer id ,Model model,@PathVariable String type){
		if(StringUtils.hasText(type)){
			if(type.equals("student")){
				
				StuResultTable stuTable = evalService.getStuTableById(id);
				EvalTable evalTable = JSON.parseObject(stuTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", stuTable);
				
			}else if(type.equals("teacher")){
				
				TeaResultTable teaTable = evalService.getTeaTableById(id);
				EvalTable evalTable = JSON.parseObject(teaTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", teaTable);
				
			}else if(type.equals("leader")){
				LeaResultTable leaTable = evalService.getLeaTableById(id);
				EvalTable evalTable = JSON.parseObject(leaTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", leaTable);
				
			}else if(type.equals("teaStu")){
				TeaStuResultTable teaStuTable = evalService.getTeaStuTableById(id);
				EvalTable evalTable = JSON.parseObject(teaStuTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", teaStuTable);
			}
			

			return "eval/showEval";
		}
		return "error";
		
	}


	/**
	 * 评教表保存预处理
	 */
	private void preSaveTable(EvalTable evalTable, ResultTable resultTable) {

		EvalTable e = evalTableService.getById(resultTable.getEid()).json2Object();     //从数据库中取出评教表
        e.setJsonString("");
		e.setAns(e, evalTable);     //将 答卷中的答案放到评教结果表中
		resultTable.setJsonString(JSON.toJSONString(e));    //将评教表序列化后存入 再重新评教结果表中
		Course course = courseService.getById(resultTable.getCid(), resultTable.getCno());
		resultTable.setDepartmentId(course.getDepartmentId());
		resultTable.setTname(course.getTeacher().getName());
		if(!StringUtils.hasText(resultTable.getTid())){
			resultTable.setTid(course.getTeacherId());
		}
		try {

            for(EvalTable.EvalItem questionItem :evalTable.getQuestionList()){
                String ans = questionItem.getAns();
                if(ans==null){
                    throw new IllegalArgumentException("问题回答不能为空!question:"+questionItem);
                }
                ans = ans.replaceAll(",","，");
                resultTable.getQuestionAnsList().add(ans);
            }

		} catch (Exception e2) {

			logger.debug("评教表的问题小于5个");
		}
	}
}
