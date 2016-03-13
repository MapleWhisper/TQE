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
 * ��̨�Ĺ���Ա�˺Ź������Բ鿴�б���ӹ���Ա����ʼ�����룬ɾ������Ա���޸Ĺ���Ա��Ϣ
 * 
 * @author �ڹ�·
 *
 */
@Controller("adminControl")
@RequestMapping("/admin")
public class AdminController {
	
	@Resource(name="adminServiceImpl")
	private AdminServiceImpl adminService;	//ע��admin����	adminServerImpl
	
	/*
	@Resource(name="privilegeServiceImpl")
	private PrivilegeService privilegeService;
	*/
	/**
	 * ��ʾ����Ա�б�ҳ��
	 * @return
	 */
	@RequestMapping("/admin")
	public String admin(Model model){
		List<Admin> adminList = adminService.findAll();	//��ѯ����Ա�б�
		model.addAttribute("adminList", adminList);
		return "admin/admin";				//ֱ�ӷ���  ǰ׺�� �ַ���+jsp��ҳ��
	}
	
	/**
	 * ���ӹ���Աҳ��
	 * @return
	 */
	@RequestMapping("/admin/add")
	public String addAdmin(Model model){
		//model.addAttribute("privilegeList", privilegeService.findAll());		
		return "admin/addAdmin";	//ת�����ҳ��
	}
	
	/**
	 * �������Ա
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
		return "redirect:/admin/admin";	//������ɺ�  ��ת������Ա�б�ҳ��
	}
	
	
	/**
	 * ɾ������Ա
	 * @param id ��Ҫɾ���Ĺ���Աid
	 * @return	���ص�����Ա�б�ҳ��
	 */
	/*
	@RequestMapping("admin/delete/{id}")
	public String delete(@PathVariable int id){
		
		adminService.delete(id);
		
		return "redirect:/admin/admin";	//��������Ա�б�ҳ��
	}
	*/
}
