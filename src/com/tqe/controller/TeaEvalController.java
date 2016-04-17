package com.tqe.controller;


import com.tqe.po.*;
import com.tqe.utils.SystemUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 教师评教
 * @author 广路
 *
 */
@Controller
@RequestMapping("/admin")
public class TeaEvalController extends BaseController{
	
	
	/**
	 * 显示教师评教中心主页面
	 */
	@RequestMapping("/teaEval")
	public String teaEval(Model model,HttpSession session){
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Batches batches = batchesService.getAvailableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		if(batches!=null){		//如果当前存在 可以评教的批次
			List<Course> courseList = courseService.findCourseGroupByTid(tea.getId(),batches.getId());	//得到所有的课程列表
			model.addAttribute("courseList", courseList);
			model.addAttribute("batches", batches);
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
		}
		return "teaEval/teaEval";
	}
	

	
	/**
	 * 显示教师评教学生中心主页面
	 */
	@RequestMapping("/teaStuEval")
	public String teaStuEval(Model model,HttpSession session){
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Batches batches = batchesService.getAvailableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
		if(batches!=null){		//如果当前存在 可以评教的批次
			List<Course> courseList = courseService.findAllByTid(tea.getId(),batches.getSeason());	//得到所有的课程列表
			model.addAttribute("courseList", courseList);
			model.addAttribute("batches", batches);
			
		}else{		//如果当前没有评教批次
			model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
		}
		return "teaEval/teaStuEval";
	}

    /**
     * 显示教师评教学生 班级信息
     */
    @RequestMapping("/teaStuEval/show")
    public String teaStuEval(
            Model model,
            HttpSession session,
            @RequestParam String cid,
            @RequestParam Integer cno
    ){
        Teacher tea = (Teacher) session.getAttribute("teacher");
        Batches batches = batchesService.getAvailableBatches(SystemUtils.getSeason());	//得到当前可用的评教批次
        if(batches!=null){		//如果当前存在 可以评教的批次
            List<Course> courseList = courseService.findAllByTid(tea.getId(),batches.getSeason());	//得到所有的课程列表
            model.addAttribute("courseList", courseList);
            model.addAttribute("batches", batches);

            List<Student> studentList;
            if(cid !=null && cno!=null){		//如果要查看学生，那么就列出学生列表
                Course course = courseService.getById(cid, cno);

                studentList = studentService.findAllByCId(cid,cno,tea.getId(),batches.getId());
                if(studentList.size()%2!=0){
                    Student s = new Student();
                    s.setIsEvaled(true);
                    studentList.add(s);
                }

                model.addAttribute("course", course);
                model.addAttribute("studentList", studentList);
            }

        }else{		//如果当前没有评教批次
            model.addAttribute("msg", "对不起，当前还没有可以评教的课程");
        }
        return "teaEval/teaStuEval";
    }
	
	/**
	 * 教师开始评价课程
	 */
	@RequestMapping("/teaEval/eval")
	public String evalTeaEval(
            Model model,
            @RequestParam() String cid ,
            @RequestParam() Integer cno
    ){
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "对不起，当前评教还未开始");
				return "teaEval/teaEval";
			}
            EvalTable evalTable = evalTableService.getById(batches.getTeaEvalId());
            if(evalTable==null){
                model.addAttribute("msg", "对不起当前评教批次还没有设置教师评教表，请联系管理设置评教表");
                return "teaEval/teaEval";
            }
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTable);
			model.addAttribute("type", "teacher");
			return "eval/eval";
		}
		return "redirect:/teaEval/teaEval";
	}
	
	
	/**
	 * 教师开始评教学生
	 */
	@RequestMapping("/teaStuEval/eval")
	public String evalTeaStuEval(
            Model model,
            @RequestParam() String cid ,
            @RequestParam() Integer cno,
            @RequestParam() Integer sid
    ){
		Course c = courseService.getById(cid, cno);
		if(c!=null){
			Batches batches = batchesService.getAvailableBatches(c.getSeason());
			if(batches==null){
				model.addAttribute("msg", "对不起，当前评教还未开始");
				return "teaEval/teaStuEval";
			}
            EvalTable evalTable = evalTableService.getById(batches.getTeaStuEvalId());
            if(evalTable==null){
                model.addAttribute("msg", "对不起当前评教批次还没有设置教师评学生表，请联系管理设置评教表");
                return "teaEval/teaStuEval";
            }
			model.addAttribute("batches", batches);
			model.addAttribute("course", c);
			model.addAttribute("evalTable", evalTable);
			model.addAttribute("sid", sid);
			model.addAttribute("type", "teaStu");
			return "eval/eval";
		}
		return "redirect:/admin/teaStuEval";
	}
	
	
}
