package com.tqe.controller;

import javax.annotation.Resource;

import com.tqe.service.*;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

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

    @Autowired
    protected ScServiceImpl scService;
	
	protected void addSearcherResource(Model model){
		
		model.addAttribute("departmentList", departmentService.findAll());
	}

    /**
     * 返回失败的页面并显示错误的信息
     */
	protected String sendError(Model model ,String msg){
		model.addAttribute("msg",msg);
		return "error";
	}

	protected String sendError(Model model ,String msg,Log log){
		log.error(msg);
		return this.sendError(model,msg);
	}


}
