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
	 * ����ѧ�����̽��
	 * @param evalTable	���̱�
	 * @param stuTable	ѧ����
	 */
	@RequestMapping(value={"/eval/save/student"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable, @ModelAttribute StuResultTable stuTable ,Model model,HttpSession session){
		//�ж�ѧ���Ƿ�
		//�������̽������
		
		try {
			preSaveTable(evalTable, stuTable);
			Student stu = (Student) session.getAttribute("student");
			if(stu==null){
				logger.error("�Բ��𣡵�ǰ��¼�û�����ѧ��\\n���ߵ�¼��ʱ�������µ�¼!");
				return sendError(model,"�Բ��𣡵�ǰ��¼�û�����ѧ��\\n���ߵ�¼��ʱ�������µ�¼!");
			}
			stuTable.setSname(stu.getName());
			evalService.saveStuTable(stuTable);
		} catch (Exception e1) {
			logger.error("�ÿγ��Ѿ����ۣ������ظ����ۣ�������û��ѡ���ſγ�!",e1);
			return sendError(model,"�ÿγ��Ѿ����ۣ������ظ����ۣ�������û��ѡ���ſγ�!");
		}
		return "redirect:/admin/stuEval";
	}
	
	
	/**
	 * 
	 * �����쵼���̽��
	 * @param evalTable	���̱�
	 * @param leaTable	��ʦ��
	 * @return
	 */
	@RequestMapping(value={"/eval/save/leader"},method={RequestMethod.POST})
	public String evalTable( @ModelAttribute EvalTable evalTable,
							 @ModelAttribute LeaResultTable leaTable ,
							 @ModelAttribute Leader leader,
							 Model model){
		
		
		try {
			if(leader==null){
				logger.error("��ǰ���̱�ֻ���쵼��ɫ���ܱ��棬��ȷ�ϵ�¼�û����������µ�¼");
				return sendError(model,"��ǰ���̱�ֻ���쵼��ɫ���ܱ��棬��ȷ�ϵ�¼�û����������µ�¼");
			}
			preSaveTable(evalTable, leaTable);
			evalService.saveLeaTable(leaTable);
		} catch (Exception e1) {
			model.addAttribute("msg","�ÿγ��Ѿ����ۣ������ظ����ۣ�");
			e1.printStackTrace();
			return "error";
		}
		return "redirect:/admin/leaEval";
	}
	
	/**
	 * 
	 * �����ʦ���̽��
	 * @param evalTable	���̱�
	 * @param teaTable	��ʦ��
	 * @return
	 */
	@RequestMapping(value={"/eval/save/teacher"},method={RequestMethod.POST})
	public String evalTable(
			@ModelAttribute EvalTable evalTable,
			@ModelAttribute TeaResultTable teaTable ,
			@ModelAttribute Teacher teacher,
			Model model){
		
		try {
			if(teacher == null){
				String msg = "��ǰ���̱�ֻ�н�ʦ��ɫ���ܱ��棬��ȷ�ϵ�¼�û����������µ�¼";
				logger.error(msg);
				return sendError(model,msg);

			}
			preSaveTable(evalTable, teaTable);
			evalService.saveTeaTable(teaTable);
		} catch (Exception e1) {
			logger.error("�ÿγ��Ѿ����ۣ������ظ����ۣ�", e1);
			return sendError(model,"�ÿγ��Ѿ����ۣ������ظ����ۣ�");
		}
		return "redirect:/admin/teaEval";
	}
	
	/**
	 * 
	 * �����ʦ����ѧ�����̽��
	 * @param evalTable	���̱�
	 * @return
	 */
	@RequestMapping(value={"/eval/save/teaStu"},method={RequestMethod.POST})
	public String teaStuevalTable(
			@ModelAttribute EvalTable evalTable,
			@ModelAttribute TeaStuResultTable teaStuTable ,
			@ModelAttribute Teacher teacher,
			Model model){
		
		try {
			if(teacher==null){
				String msg = "��ǰ���̱�ֻ�н�ʦ��ɫ���ܱ��棬��ȷ�ϵ�¼�û����������µ�¼";
				logger.error(msg);
				return sendError(model,msg);
			}
			teaStuTable.setSname(studentService.getNameById(teaStuTable.getSid()).getName());

			preSaveTable(evalTable, teaStuTable);
			evalService.saveTeaStuTable(teaStuTable);
		} catch (Exception e1) {
			model.addAttribute("msg","�ÿγ��Ѿ����ۣ������ظ����ۣ�");
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
	 * ��ʾ���̽��
	 * @param id ���̽��Id
	 * @param model
	 * @param type [student|teacher|lead]
	 * @return
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
	 * ��Ҫ��������̽������Ԥ����
	 * @param evalTable	���̱�
	 * @param resultTable ���̽����
	 */
	private void preSaveTable(EvalTable evalTable, ResultTable resultTable) {
		EvalTable e = evalTableService.getById(resultTable.getEid()).json2Object();
		e.setAns(e, evalTable);
		resultTable.setJsonString(JSON.toJSONString(e));
		Course course = courseService.getById(resultTable.getCid(), resultTable.getCno());
		resultTable.setDepartmentId(course.getDepartmentId());
		resultTable.setTname(course.getTeacher().getName());
		if(!StringUtils.hasText(resultTable.getTid())){
			resultTable.setTid(course.getTeacherId());
		}
		try {
			resultTable.setQuestion1(evalTable.getQuestionList().get(0)==null?null:evalTable.getQuestionList().get(0).getAns());
			resultTable.setQuestion2(evalTable.getQuestionList().get(1)==null?null:evalTable.getQuestionList().get(0).getAns());
			resultTable.setQuestion3(evalTable.getQuestionList().get(2)==null?null:evalTable.getQuestionList().get(0).getAns());
			resultTable.setQuestion4(evalTable.getQuestionList().get(3)==null?null:evalTable.getQuestionList().get(0).getAns());
			resultTable.setQuestion5(evalTable.getQuestionList().get(4)==null?null:evalTable.getQuestionList().get(0).getAns());
		} catch (Exception e2) {
			//���﷢���쳣 �������̱������û�дﵽ5�� ����List��������Խ�� �����쳣
			//�����쳣 �˳����漴��
			//logger.error("�������̽��ʧ��"+evalTable,e2);
		}
	}
}
