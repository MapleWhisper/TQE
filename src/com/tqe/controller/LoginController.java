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
 * 登陆控制器
 * @author 广路
 *
 */
@Controller
public class LoginController extends BaseController{

	Log logger = LogFactory.getLog(LoginController.class);

	/**
	 * 用户登陆
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
        if(skipVerify==null){   //如果session已经失效 说明不需要验证码了
            skipVerify = false;
        }


        //如果需要检查验证码
		if(!skipVerify)  {
            //如果输入的验证码不正确
            if(StringUtils.isBlank(verificationCode) || !verificationCode.equals(session.getAttribute("verificationCode") )){
                model.addAttribute("error","验证码错误");
                session.setAttribute("skipVerify",false);   //需要验证码
                return "index";
            }
		}

		String type = user.getType();
        UserType userType = UserType.toUserType(type);

        boolean loginSuccess = false;

		//记住用户最后的登录方式
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
                logger.error("未知的登录角色！ " + type);
                model.addAttribute("error","未知的登录用户的角色！");
                return "index";
        }

        if(loginSuccess) {
            List<Privilege> pList = privilegeService.findAllByUserType(userType);   //权限信息放入session
            addPrivilege(session, pList);
            removeOtherUser(session, userType);
            return viewName;
        }else {
            model.addAttribute("error","用户名或密码错误");
            return "index";
        }

	}

	
	
	@RequestMapping("/logout")
    public String logout(HttpSession session){
		removeOtherUser(session, null);
		return "redirect:/index";
	}

	/**
	 * 添加权限信息到session中 用于前台进行权限的显示的判断
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
	 * 用户登陆时，移除其他无关的角色，保证系统同一时刻只有一个角色能登陆
	 */
	private void removeOtherUser(HttpSession session , UserType userType) {
        if(userType==null){ //移除当先回话中的所有用户
            for(UserType uType : UserType.values() ){
                    session.removeAttribute(uType.getName());
            }
            return ;
        }
        //如果指定了角色，那么移除其他角色的session信息
        for(UserType uType : UserType.values() ){
            if(!uType.equals(userType)){
                session.removeAttribute(uType.getName());
            }
        }
	}
}
