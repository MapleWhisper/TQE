package com.tqe.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tqe.po.Admin;
import com.tqe.service.*;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;

@Component
public class BaseController {

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
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

    @Autowired
    protected CourseBatchServiceImpl courseBatchService;

    @Autowired
    protected StudentSeasonServiceImpl studentSeasonService;
	
	protected void addSearcherResource(Model model){
		
		model.addAttribute("departmentList", departmentService.findAll());
	}

    /**
     * 发送错误
     */
	protected String sendError(Model model ,String msg){
		model.addAttribute("msg",msg);
		return "error";
	}

	protected String sendError(Model model ,String msg,Log log){
		log.error(msg);
		return this.sendError(model,msg);
	}

    protected String sendError(Model model ,String msg,Log log ,Exception e){
        log.error(msg,e);
        return this.sendError(model,msg);
    }

    /**
     * 判断当前登录的用户是否是超级管理员
     * @param session
     * @return
     */
    protected boolean isSuperAdmin(HttpSession session){

        Admin curAdmin = (Admin) session.getAttribute("admin");
        return curAdmin != null && curAdmin.getName().equals("admin");

    }
}
