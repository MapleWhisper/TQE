package com.tqe.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.tqe.service.AdminServiceImpl;
import com.tqe.service.BatchesServiceImpl;
import com.tqe.service.CourseServiceImpl;
import com.tqe.service.EvalTableServiceImpl;
import com.tqe.service.StudentServiceImpl;
import com.tqe.service.TeacherServiceImpl;

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
}
