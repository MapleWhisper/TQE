package com.tqe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqe.po.Privilege;



/**
 * 后台的管理员账号管理，可以查看列表，添加管理员，初始化密码，删除管理员，修改管理员信息
 * 
 * @author 于广路
 *
 */
@Controller()
@RequestMapping("/admin")
public class PrivilegeController extends BaseController{
	
	/**
	 * 显示权限管理页面
	 * @return
	 */
	@RequestMapping("/privilege")
	public String admin(Model model){
		model.addAttribute("privilegeList", privilegeService.findAll());
		return "privilege/privilege";				//直接返回  前缀加 字符串+jsp的页面
	}
	
	
	/**
	 * 修改权限
	 * @return
	 */
	@RequestMapping("/privilege/update")
	@ResponseBody
	public String update(@ModelAttribute Privilege privilege){
		Privilege p = privilegeService.getById(privilege.getId());
		p.setStu(privilege.getStu());
		p.setTea(privilege.getTea());
		p.setAdm(privilege.getAdm());
		p.setLea(privilege.getLea());
		privilegeService.update(p);
		return "success";	//保存完成后  跳转到管理员列表页面
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
