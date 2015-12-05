package com.tqe.controller;

import javax.servlet.http.HttpSession;

import com.tqe.po.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
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
	 * 保存学生评教结果
	 * @param evalTable	评教表
	 * @param stuTable	学生表
	 */
	@RequestMapping(value={"/eval/save/student"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable, @ModelAttribute StuTable stuTable ,Model model,HttpSession session){
		//判断学生是否
		//处理评教结果数据
		
		try {
			preSaveTable(evalTable, stuTable);
			Student stu = (Student) session.getAttribute("student");
			if(stu==null){
				logger.error("对不起！当前登录用户不是学生\\n或者登录超时，请重新登录!");
				return sendError(model,"对不起！当前登录用户不是学生\\n或者登录超时，请重新登录!");
			}
			stuTable.setSname(stu.getName());
			evalService.saveStuTable(stuTable);
		} catch (Exception e1) {
			logger.error("该课程已经评价！不能重复评价！或者您没有选这门课程!",e1);
			return sendError(model,"该课程已经评价！不能重复评价！或者您没有选这门课程!");
		}
		return "redirect:/admin/stuEval";
	}
	
	
	/**
	 * 
	 * 保存领导评教结果
	 * @param evalTable	评教表
	 * @param leaTable	教师表
	 * @return
	 */
	@RequestMapping(value={"/eval/save/leader"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable,
							 @ModelAttribute LeaTable leaTable ,
							 @ModelAttribute Leader leader,
							 Model model){
		
		
		try {
			if(leader==null){
				logger.error("当前评教表只有领导角色才能保存，请确认登录用户或者请重新登录");
				return sendError(model,"当前评教表只有领导角色才能保存，请确认登录用户或者请重新登录");
			}
			preSaveTable(evalTable, leaTable);
			evalService.saveLeaTable(leaTable);
		} catch (Exception e1) {
			model.addAttribute("msg","该课程已经评价！不能重复评价！");
			e1.printStackTrace();
			return "error";
		}
		return "redirect:/admin/leaEval";
	}
	
	/**
	 * 
	 * 保存教师评教结果
	 * @param evalTable	评教表
	 * @param teaTable	教师表
	 * @return
	 */
	@RequestMapping(value={"/eval/save/teacher"},method={RequestMethod.POST})
	public String evalTable(
			@ModelAttribute EvalTable evalTable,
			@ModelAttribute TeaTable teaTable ,
			@ModelAttribute Teacher teacher,
			Model model){
		
		try {
			if(teacher == null){
				String msg = "当前评教表只有教师角色才能保存，请确认登录用户或者请重新登录";
				logger.error(msg);
				return sendError(model,msg);

			}
			preSaveTable(evalTable, teaTable);
			evalService.saveTeaTable(teaTable);
		} catch (Exception e1) {
			logger.error("该课程已经评价！不能重复评价！", e1);
			return sendError(model,"该课程已经评价！不能重复评价！");
		}
		return "redirect:/admin/teaEval";
	}
	
	/**
	 * 
	 * 保存教师评价学生评教结果
	 * @param evalTable	评教表
	 * @return
	 */
	@RequestMapping(value={"/eval/save/teaStu"},method={RequestMethod.POST})
	public String teaStuevalTable( @ModelAttribute EvalTable evalTable, @ModelAttribute TeaStuTable teaStuTable ,Model model){
		
		try {
			teaStuTable.setSname(studentService.getNameById(teaStuTable.getSid()).getName());
			preSaveTable(evalTable, teaStuTable);
			evalService.saveTeaStuTable(teaStuTable);
		} catch (Exception e1) {
			model.addAttribute("msg","该课程已经评价！不能重复评价！");
			e1.printStackTrace();
			return "error";
		}
		String cid = teaStuTable.getCid();
		Integer cno = teaStuTable.getCno();
		StringBuilder sb = new StringBuilder();
		String view = sb.append("redirect:/admin/").append(cid).append("/").append(cno).append("/teaStuEval").toString();
		return view;
	}


	
	
	/**
	 * 显示评教结果
	 * @param id 评教结果Id
	 * @param model
	 * @param type [student|teacher|lead]
	 * @return
	 */
	@RequestMapping("/eval/show/{type}/{id}")
	public String showEvalTable( @PathVariable Integer id ,Model model,@PathVariable String type){
		if(StringUtils.hasText(type)){
			if(type.equals("student")){
				
				StuTable stuTable = evalService.getStuTableById(id);
				EvalTable evalTable = JSON.parseObject(stuTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", stuTable);
				
			}else if(type.equals("teacher")){
				
				TeaTable teaTable = evalService.getTeaTableById(id);
				EvalTable evalTable = JSON.parseObject(teaTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", teaTable);
				
			}else if(type.equals("leader")){
				LeaTable leaTable = evalService.getLeaTableById(id);
				EvalTable evalTable = JSON.parseObject(leaTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", leaTable);
				
			}else if(type.equals("teaStu")){
				TeaStuTable teaStuTable = evalService.getTeaStuTableById(id);
				EvalTable evalTable = JSON.parseObject(teaStuTable.getJsonString(),EvalTable.class);
				model.addAttribute("evalTable", evalTable);
				model.addAttribute("table", teaStuTable);
			}
			

			return "eval/showEval";
		}
		return "error";
		
	}
	

	
	private void preSaveTable(EvalTable evalTable, Table table) {
		EvalTable e = evalTableService.getById(table.getEid()).json2Object();
		e.setAns(e, evalTable);
		table.setJsonString(JSON.toJSONString(e));
		Course course = courseService.getById(table.getCid(), table.getCno());
		table.setDepartmentId(course.getDepartmentId());
		table.setTname(course.getTeacher().getName());
		if(!StringUtils.hasText(table.getTid())){	
			table.setTid(course.getTeacherId());
		}
		try {
			table.setQuestion1(evalTable.getQuestionList().get(0)==null?null:evalTable.getQuestionList().get(0).getAns());
			table.setQuestion2(evalTable.getQuestionList().get(1)==null?null:evalTable.getQuestionList().get(0).getAns());
			table.setQuestion3(evalTable.getQuestionList().get(2)==null?null:evalTable.getQuestionList().get(0).getAns());
			table.setQuestion4(evalTable.getQuestionList().get(3)==null?null:evalTable.getQuestionList().get(0).getAns());
			table.setQuestion5(evalTable.getQuestionList().get(4)==null?null:evalTable.getQuestionList().get(0).getAns());
		} catch (Exception e2) {
			logger.error("保存评教结果失败"+evalTable,e2);
		}
	}
}
