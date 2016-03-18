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
 * ����Ա����
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
     * ��ʾ����Ա����ҳ��
	 */
	@RequestMapping("/admin")
	public String admin(Model model){
		List<Admin> adminList = adminService.findAll();
		model.addAttribute("adminList", adminList);
		return "admin/admin";
	}
	

    @RequestMapping("/admin/getInfo")
    public @ResponseBody BaseResult getAdminById(
            @RequestParam Integer id
    ){
        if(id ==null || id< 0 ){
            return BaseResult.createFailure("�Բ��𣬸��û������� id:"+id);
        }
        Admin admin = adminService.getById(id);
        if(admin==null || admin.getId()==null){
            return BaseResult.createFailure("�Բ��𣬸��û������� id:"+id);
        }
        return BaseResult.createSuccess(admin);
    }

	/**
	 * �������Ա
	 */
	@RequestMapping("/admin/save")
	public String save(
            @ModelAttribute Admin admin,
            Model model
    ){
		if(StringUtils.isBlank(admin.getName()) || StringUtils.isBlank(admin.getPosition())
                || StringUtils.isBlank(admin.getUsername()) || StringUtils.isBlank(admin.getPassword())){
           return  sendError(model, "����Ա������ ְλ���û��� �� ���벻��Ϊ��");
        }
		adminService.save(admin);
		return "redirect:/admin/admin";
	}

    /**
     * ���¹���Ա
     */
    @RequestMapping("/admin/update")
    public String update(
            @ModelAttribute Admin admin,
            Model model,
            HttpSession session
    ){

        if(admin == null || admin.getId()==null){
            return sendError(model,"����ԱId����Ϊ�հ�");
        }
        Admin curAdmin = (Admin) session.getAttribute("admin");
        if(!curAdmin.getName().equals("admin")){   //������ǳ�������Ա ��ô��ͨ����Աֻ���޸��Լ��˺���Ϣ
            if(!curAdmin.getId().equals(admin.getId())){
                return sendError(model,"��ֻ���޸��Լ����˺���Ϣ!");
            }
        }

        Admin a = adminService.getById(admin.getId());
        a.setName(admin.getName());
        a.setPosition(admin.getPosition());
        if(!admin.getPassword().contains("***")){
            a.setPassword(admin.getPassword());
        }
        adminService.update(a);
        return "redirect:/admin/admin";

    }
	
	/**
	 *ɾ������Ա
     * */
	@RequestMapping("admin/delete/{id}")
	public String delete(@PathVariable int id){
		
		//adminService.delete(id);
		
		return "redirect:/admin/admin";
	}
}
