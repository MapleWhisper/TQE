package com.tqe.controller;

import com.tqe.po.Privilege;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限管理控制器
 * @author 于广路
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
		if(p.getEditable()==0){
			return "该权限不能被修改!";
		}
		p.setStu(privilege.getStu());
		p.setTea(privilege.getTea());
		p.setAdm(privilege.getAdm());
		p.setLea(privilege.getLea());
		privilegeService.update(p);
		return "success";	//保存完成后  跳转到管理员列表页面
	}

}
