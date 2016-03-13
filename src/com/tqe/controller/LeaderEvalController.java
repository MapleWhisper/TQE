package com.tqe.controller;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.tqe.base.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tqe.po.Batches;
import com.tqe.po.Course;
import com.tqe.po.Leader;
import com.tqe.utils.SystemUtils;

/**
 * ѧ�����̿�����
 * @author ��·
 *
 */
@Controller
@RequestMapping("/admin")
public class LeaderEvalController extends BaseController{
	
	
	/**
	 * ��ʾ�쵼������ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/leaEval",method=RequestMethod.GET)
	public String leaEval(Model model,HttpSession session){
		Leader leader = (Leader) session.getAttribute("leader");
		
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//�õ���ǰ���õ���������
		
		if(batches!=null){		//�����ǰ���� �������̵�����
			addSearcherResource(model);
			model.addAttribute("batches", batches);
			
		}else{		//�����ǰû����������
			model.addAttribute("msg", "�Բ��𣬵�ǰ��û�п������̵Ŀγ�");
		}
		return "leaEval/leaEval";
	}
	
	/**
	 * ��ʾ�쵼������ҳ��(��ѯ���)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/leaEval",method=RequestMethod.POST)
	public String lea1Eval(Model model,HttpSession session,String did,String cname ,String  cid,String tname){
		Leader leader = (Leader) session.getAttribute("leader");
		
		Batches batches = batchesService.getAvailiableBatches(SystemUtils.getSeason());	//�õ���ǰ���õ���������
		
		if(batches!=null){		//�����ǰ���� �������̵�����
			addSearcherResource(model);
			//��ѯ�����ؿγ�������Ϣ
			HashMap<String,String> condition = new HashMap<String,String>();
			condition.put("did", did);
			condition.put("cname", cname);
			condition.put("cid", cid);
			condition.put("tname", tname);
			PageVO pageVO = new PageVO();
			pageVO.setFilters(condition);
			model.addAttribute("condition", condition);
			addSearcherResource(model);
			List<Course> list = courseService.findByCondition(pageVO);
			model.addAttribute("courseList",list);
			
			model.addAttribute("batches", batches);
			
			
		}else{		//�����ǰû����������
			model.addAttribute("msg", "���ã���ǰ��û�п������̵Ŀγ�");
		}
		return "leaEval/leaEval";
	}
	
	/**
	 * �쵼��ʼ����
	 * @param model
	 * @param cid
	 * @param cno
	 * @return
	 */
	@RequestMapping("/leaEval/eval/{cid}/{cno}")
	public String EvalleaEval(Model model,@PathVariable("cid") String cid ,@PathVariable("cno") Integer cno){
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailiableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "�Բ��𣬵�ǰ���̻�δ��ʼ");
				return "leaEval/leaEval";
			}
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTableService.getById(batches.getLeadEvalId()));
			model.addAttribute("type", "leader");
			return "eval/eval";
		}
		return "redirect:/admin/leaEval";
	}
	
	
}
