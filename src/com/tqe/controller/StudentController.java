package com.tqe.controller;
import java.util.*;

import com.tqe.base.enums.DepartmentType;
import com.tqe.base.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tqe.model.CourseModel;
import com.tqe.po.Batches;
import com.tqe.po.Student;
import com.tqe.po.TeaStuResultTable;
import com.tqe.utils.SystemUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("/admin")
public class StudentController extends BaseController{

    private static final Log logger = LogFactory.getLog(StudentController.class);

	/**
	 * 显示学生数据界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.GET})
	public String student(
            Model model,
            HttpSession session
    ){

		model.addAttribute("departmentList", departmentService.findAvailableDepartmentList(DepartmentType.STUDENT));

		return "student/student";
	}
	
	/**
	 * 显示学生数据界面 关键字查找
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.POST})
	public String searchStudent(
			Model model,
			PageVO pageVO){
		model.addAttribute("departmentList", departmentService.findAvailableDepartmentList(DepartmentType.STUDENT));
		model.addAttribute("condition", pageVO.getFilters());
		model.addAttribute("studentList", studentService.findByPageVO(pageVO));
		return "student/student";
	}
	
	@RequestMapping("/student/show")
	public String showStudent(
            @RequestParam() String sid,
            @RequestParam() String season,
            Model model
    ){
		Student stu = studentService.getById(sid);	//获取学生信息
		if(stu==null){
            return sendError(model,"没有找到学生信息! sid="+sid,logger);
        }
		CourseModel courseModel = new CourseModel();
        if(StringUtils.isBlank(season)){
            season = SystemUtils.getSeason();
        }
		List<Batches> batchesList = batchesService.findAllBySeason(season);	//默认得到当前学期的所有批次
		
		for(Batches b : batchesList){	//遍历所有得到的批次列表
			List<TeaStuResultTable> teaStuTableList = evalService.findAllTeaStuTableBySid(sid, b.getId());
			CourseModel.Batches batches = new CourseModel.Batches();
			batches.setTeaStuTableList(teaStuTableList);
			batches.setBatches(b);
			courseModel.getBatchesList().add(batches);
		}
		model.addAttribute("student", stu);
		model.addAttribute("courseModel", courseModel);
        Map<String,String> condition = new HashMap<String,String>();
        condition.put("season",season);
        model.addAttribute("condition",condition);
		return "student/showStudent";
	}
	
	@RequestMapping("/student/add")
	public String addStudent(){
		return "student/addStudent";
	}
	
	@RequestMapping("/student/save")
	public String saveStudent(){
		return "redirect:/admin/student";
	}
	
	
}
