package com.tqe.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tqe.base.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tqe.po.Admin;
import com.tqe.service.AdminServiceImpl;


/**
 * 管理员控制器
 */
@Controller("adminControl")
@RequestMapping("/admin")
public class AdminController extends BaseController{
	
	@Resource(name="adminServiceImpl")
	private AdminServiceImpl adminService;	//???admin????	adminServerImpl
	
	/*
	@Resource(name="privilegeServiceImpl")
	private PrivilegeService privilegeService;
	*/
	/**
     * 显示管理员显示页面
	 */
	@RequestMapping("/admin")
	public String admin(Model model){
		List<Admin> adminList = adminService.findAll();
		model.addAttribute("adminList", adminList);
		return "admin/admin";
	}
	

    @RequestMapping("/admin/getInfo")
    @ResponseBody
    public  BaseResult getAdminById(
            @RequestParam Integer id
    ){
        if(id ==null || id< 0 ){
            return BaseResult.createFailure("管理员的Id不能为空 id:"+id);
        }
        Admin admin = adminService.getById(id);
        if(admin==null || admin.getId()==null){
            return BaseResult.createFailure("没有找到该管理员信息 id:"+id);
        }
        return BaseResult.createSuccess(admin);
    }

	/**
	 * 保存管理员
	 */
	@RequestMapping("/admin/save")
	public String save(
            @ModelAttribute Admin admin,
            Model model
    ){
		if(StringUtils.isBlank(admin.getName()) || StringUtils.isBlank(admin.getPosition())
                || StringUtils.isBlank(admin.getUsername()) || StringUtils.isBlank(admin.getPassword())){
           return  sendError(model, "管理员的姓名 职位 用户名 和密码 不能为空");
        }
		adminService.save(admin);
		return "redirect:/admin/admin";
	}

    /**
     * 更新管理员
     */
    @RequestMapping("/admin/update")
    public String update(
            @ModelAttribute Admin admin,
            Model model,
            HttpSession session
    ){

        if(admin == null || admin.getId()==null){
            return sendError(model,"管理员Id不能为空");
        }
        Admin a = (Admin) session.getAttribute("admin");
        if(!a.getId().equals(admin.getId())){
            return sendError(model,"您只能修改自己的信息！");
        }

        a = adminService.getById(admin.getId());
        a.setName(admin.getName());
        a.setPosition(admin.getPosition());
        if(!admin.getPassword().contains("*")){   //只有不包含"*"才会继续修改密码
            a.setPassword(admin.getPassword());
        }
        adminService.update(a);
        return "redirect:/admin/admin";

    }
	
	/**
	 * 删除管理员
     * */
	@RequestMapping("admin/delete")
	public String delete(
            @RequestParam Integer id,
            Model model,
            HttpSession session
    ){
		if(id==null){
            return sendError(model,"未知的管理员Id");
        }
        if(!isSuperAdmin(session)){
            return sendError(model,"只有超级管理员才能删除管理员！");
        }
		adminService.delete(id);
		return "redirect:/admin/admin";
	}
}
