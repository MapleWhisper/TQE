package com.tqe.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.tqe.service.AdminServiceImpl;
import com.tqe.service.BatchesServiceImpl;
import com.tqe.service.ClassServiceImpl;
import com.tqe.service.CommonServiceImpl;
import com.tqe.service.CourseServiceImpl;
import com.tqe.service.DepartmentServiceImpl;
import com.tqe.service.EvalServiceImpl;
import com.tqe.service.EvalTableServiceImpl;
import com.tqe.service.LeaderServiceImpl;
import com.tqe.service.MajorServiceImpl;
import com.tqe.service.PrivilegeService;
import com.tqe.service.StuEvalServiceImpl;
import com.tqe.service.StudentServiceImpl;
import com.tqe.service.TeacherServiceImpl;

@Component
public class BaseController {
	
	
	@Resource(name="adminServiceImpl")
	protected AdminServiceImpl adminService;
	
	@Autowired
	protected TeacherServiceImpl teacherService;
	
	@Autowired
	protected BatchesServiceImpl batchesService;
	
	@Autowired
	protected CourseServiceImpl courseService;
	
	@Autowired
	protected EvalTableServiceImpl evalTableService;
	
	@Autowired
	protected StudentServiceImpl studentService;
	
	@Autowired
	protected CommonServiceImpl commonService;
	
	@Autowired
	protected StuEvalServiceImpl stuEvalService;
	
	@Autowired
	protected EvalServiceImpl evalService;
	
	@Autowired
	protected DepartmentServiceImpl departmentService;
	
	@Autowired
	protected MajorServiceImpl majorService;
	
	@Autowired
	protected ClassServiceImpl clazzService;
	
	@Autowired
	protected PrivilegeService privilegeService;
	
	@Autowired
	protected LeaderServiceImpl leaderService;
	
	protected void addSercherResource(Model model){
		
		model.addAttribute("departmentList", departmentService.findAll());
	}

	protected String sendError(Model model ,String msg){
		model.addAttribute("msg",msg);
		return "error";
	}
	
}
