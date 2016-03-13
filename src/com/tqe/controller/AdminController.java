package com.tqe.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tqe.po.Admin;
import com.tqe.service.AdminServiceImpl;



/**
 * 后台的管理员账号管理，可以查看列表，添加管理员，初始化密码，删除管理员，修改管理员信息
 * 
 * @author 于广路
 *
 */
@Controller("adminControl")
@RequestMapping("/admin")
public class AdminController {
	
	@Resource(name="adminServiceImpl")
	private AdminServiceImpl adminService;	//注入admin服务	adminServerImpl
	
	/*
	@Resource(name="privilegeServiceImpl")
	private PrivilegeService privilegeService;
	*/
	/**
	 * 显示管理员列表页面
	 * @return
	 */
	@RequestMapping("/admin")
	public String admin(Model model){
		List<Admin> adminList = adminService.findAll();	//查询管理员列表
		model.addAttribute("adminList", adminList);
		return "admin/admin";				//直接返回  前缀加 字符串+jsp的页面
	}
	
	/**
	 * 增加管理员页面
	 * @return
	 */
	@RequestMapping("/admin/add")
	public String addAdmin(Model model){
		//model.addAttribute("privilegeList", privilegeService.findAll());		
		return "admin/addAdmin";	//转到添加页面
	}
	
	/**
	 * 保存管理员
	 * @return
	 */
	@RequestMapping("/admin/save")
	public String save(@ModelAttribute Admin admin){
		/*
		Integer[] a = admin.getPrivilegeIds();
		HashSet<Privilege> set = new HashSet<>();
		for(int i:a){
			set.add(privilegeService.getById(i));
		}
		admin.setPrivileges(set);
		*/
		adminService.save(admin);
		return "redirect:/admin/admin";	//保存完成后  跳转到管理员列表页面
	}
	
	
	/**
	 * 删除管理员
	 * @param id 需要删除的管理员id
	 * @return	返回到管理员列表页面
	 */
	/*
	@RequestMapping("admin/delete/{id}")
	public String delete(@PathVariable int id){
		
		adminService.delete(id);
		
		return "redirect:/admin/admin";	//跳到管理员列表页面
	}
	*/
}
