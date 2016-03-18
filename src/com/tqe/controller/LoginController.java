package com.tqe.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tqe.base.enums.UserType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tqe.po.Admin;
import com.tqe.po.Leader;
import com.tqe.po.Privilege;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.po.User;

/**
 * ��½������
 * @author ��·
 *
 */
@Controller
public class LoginController extends BaseController{

	Log logger = LogFactory.getLog(LoginController.class);

	/**
	 * �û���½
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@ModelAttribute User user,
						HttpSession session,
						@RequestParam(required = false) String verificationCode,
						HttpServletResponse response,
						Model model){

        session.setAttribute("lastLoginTime",new Date().getTime());

        Boolean skipVerify = (Boolean) session.getAttribute("skipVerify");
        if(skipVerify==null){   //���session�Ѿ�ʧЧ ˵������Ҫ��֤����
            skipVerify = false;
        }


        //�����Ҫ�����֤��
		if(!skipVerify)  {
            //����������֤�벻��ȷ
            if(StringUtils.isBlank(verificationCode) || !verificationCode.equals(session.getAttribute("verificationCode") )){
                model.addAttribute("error","��֤�����");
                session.setAttribute("skipVerify",false);   //��Ҫ��֤��
                return "index";
            }
		}

		String type = user.getType();
        UserType userType = UserType.toUserType(type);

        boolean loginSuccess = false;

		//��ס�û����ĵ�¼��ʽ
		Cookie cookie = new Cookie("loginType",type);
		cookie.setMaxAge(365 * 24 * 60 * 60);
		response.addCookie(cookie);
        String viewName = "index";
        switch (userType){
            case ADMIN:
                Admin a =adminService.login(user);
                if(a!=null){
                    session.setAttribute("admin", a);
                    viewName="redirect:/admin/admin";
                    loginSuccess = true;
                }
                break;
            case TEACHER:
                Teacher t = teacherService.login(user);
                if(t!=null){
                    session.setAttribute("teacher", t);
                    viewName="redirect:/admin/teaEval";
                    loginSuccess = true;
                }
                break;
            case STUDENT:
                Student stu = studentService.login(user);
                if(stu!=null){
                    session.setAttribute("student", stu);
                    viewName= "redirect:/admin/stuEval";
                    loginSuccess = true;
                }
                break;
            case LEADER:
                Leader leader = leaderService.login(user);
                if(leader!=null){
                    session.setAttribute("leader", leader);
                    List<Privilege> pList = privilegeService.findLeaderAll();
                    addPrivilege(session, pList);
                    viewName =  "redirect:/admin/leaEval";
                    loginSuccess = true;
                }
                break;
            default:
                logger.error("δ֪�ĵ�¼��ɫ�� " + type);
                model.addAttribute("error","δ֪�ĵ�¼�û��Ľ�ɫ��");
                return "index";
        }

        if(loginSuccess) {
            List<Privilege> pList = privilegeService.findAllByUserType(userType);   //Ȩ����Ϣ����session
            addPrivilege(session, pList);
            removeOtherUser(session, userType);
            return viewName;
        }else {
            model.addAttribute("error","�û������������");
            return "index";
        }

	}

	
	
	@RequestMapping("/logout")
    public String logout(HttpSession session){
		removeOtherUser(session, null);
		return "redirect:/index";
	}

	/**
	 * ���Ȩ����Ϣ��session�� ����ǰ̨����Ȩ�޵���ʾ���ж�
	 */
	private void addPrivilege(HttpSession session,List<Privilege> pList){
		session.setAttribute("pList", pList);
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		for(Privilege p : pList){
			map.put(p.getUrl().substring(1), true);
		}
		session.setAttribute("pMap", map);
	}
	
	/**
	 * �û���½ʱ���Ƴ������޹صĽ�ɫ����֤ϵͳͬһʱ��ֻ��һ����ɫ�ܵ�½
	 */
	private void removeOtherUser(HttpSession session , UserType userType) {
        if(userType==null){ //�Ƴ����Ȼػ��е������û�
            for(UserType uType : UserType.values() ){
                    session.removeAttribute(uType.getName());
            }
            return ;
        }
        //���ָ���˽�ɫ����ô�Ƴ�������ɫ��session��Ϣ
        for(UserType uType : UserType.values() ){
            if(!uType.equals(userType)){
                session.removeAttribute(uType.getName());
            }
        }
	}
}
