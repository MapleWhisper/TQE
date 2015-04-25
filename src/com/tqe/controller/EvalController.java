package com.tqe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.tqe.po.Course;
import com.tqe.po.EvalTable;
import com.tqe.po.StuTable;
import com.tqe.po.Student;
import com.tqe.po.TeaTable;

@Controller
@RequestMapping("/admin")
public class EvalController extends BaseController{
	
	/**
	 * 
	 * 保存学生评教结果
	 * @param evalTable	评教表
	 * @param stuTable	学生表
	 * @param type		类型
	 * @return
	 */
	@RequestMapping(value={"/eval/save/student"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable, @ModelAttribute StuTable stuTable ,Model model,HttpSession session){
		
		EvalTable e = evalTableService.getById(stuTable.getEid()).json2Object();
		e.setAns(e, evalTable);
		stuTable.setJsonString(JSON.toJSONString(e));
		Course course = courseService.getById(stuTable.getCid(), stuTable.getCno());
		Student stu = (Student) session.getAttribute("student");
		try {
			stuTable.setSname(stu.getName());
			stuTable.setTname(course.getTeacher().getName());
			stuTable.setDepartmentId(course.getDepartmentId());
			stuTable.setTid(course.getTeacherId());
			try {
				stuTable.setQuestion1(evalTable.getQuestionList().get(0)==null?null:evalTable.getQuestionList().get(0).getAns());
				stuTable.setQuestion2(evalTable.getQuestionList().get(1)==null?null:evalTable.getQuestionList().get(0).getAns());
				stuTable.setQuestion3(evalTable.getQuestionList().get(2)==null?null:evalTable.getQuestionList().get(0).getAns());
				stuTable.setQuestion4(evalTable.getQuestionList().get(3)==null?null:evalTable.getQuestionList().get(0).getAns());
				stuTable.setQuestion5(evalTable.getQuestionList().get(4)==null?null:evalTable.getQuestionList().get(0).getAns());
			} catch (Exception e2) {
			}
			
			
			evalService.saveStuTable(stuTable);
		} catch (Exception e1) {
			model.addAttribute("msg","该课程已经评价！不能重复评价！");
			
			e1.printStackTrace();
			return "error";
		}
		return "redirect:/admin/stuEval";
	}
	
	
	/**
	 * 
	 * 保存教师评教结果
	 * @param evalTable	评教表
	 * @param teaTable	教师表
	 * @param type		类型
	 * @return
	 */
	@RequestMapping(value={"/eval/save/teacher"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable, @ModelAttribute TeaTable teaTable ,Model model){
		
		EvalTable e = evalTableService.getById(teaTable.getEid()).json2Object();
		e.setAns(e, evalTable);
		teaTable.setJsonString(JSON.toJSONString(e));
		try {
			evalService.saveTeaTable(teaTable);
		} catch (Exception e1) {
			model.addAttribute("msg","该课程已经评价！不能重复评价！");
			e1.printStackTrace();
			return "error";
		}
		return "redirect:/admin/teaEval";
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
				
			}else{
				
			}
			

			return "eval/showEval";
		}
		return "error";
		
	}
}
