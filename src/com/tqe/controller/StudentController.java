package com.tqe.controller;
import java.util.List;

import com.tqe.base.enums.DepartmentType;
import com.tqe.base.vo.PageVO;
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

@Controller()
@RequestMapping("/admin")
public class StudentController extends BaseController{

	/**
	 * 显示学生数据界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.GET})
	public String student(Model model){

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
	
	@RequestMapping("/student/show/{sid}")
	public String showStudent(@PathVariable String sid,Model model){
		Student stu = studentService.getById(sid);	//获取学生信息
		
		CourseModel courseModel = new CourseModel();
		List<Batches> batchesList = batchesService.findAllBySeason(SystemUtils.getSeason());	//默认得到当前学期的所有批次
		
		for(Batches b : batchesList){	//遍历所有得到的批次列表
			List<TeaStuResultTable> teaStuTableList = evalService.findAllTeaStuTableBySid(sid, b.getId());
			CourseModel.Batches batches = new CourseModel.Batches();
			batches.setTeaStuTableList(teaStuTableList);
			batches.setBatches(b);
			courseModel.getBatchesList().add(batches);
		}
		model.addAttribute("student", stu);
		model.addAttribute("courseModel", courseModel);
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
