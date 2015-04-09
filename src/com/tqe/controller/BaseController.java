package com.tqe.controller;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.tqe.service.AdminServiceImpl;
import com.tqe.service.BatchesServiceImpl;
import com.tqe.service.CommonServiceImpl;
import com.tqe.service.CourseServiceImpl;
import com.tqe.service.EvalServiceImpl;
import com.tqe.service.EvalTableServiceImpl;
import com.tqe.service.StuEvalServiceImpl;
import com.tqe.service.StudentServiceImpl;
import com.tqe.service.TeacherServiceImpl;
import com.tqe.utils.SystemProperties;

@Component
public class BaseController {
	
	
	public static String season;
	
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
	
	
	
}
