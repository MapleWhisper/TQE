package com.tqe.controller;

import com.tqe.base.enums.BaseConfigKye;
import com.tqe.base.enums.LoginType;
import com.tqe.base.enums.UserType;
import com.tqe.base.exception.UserNotExistException;
import com.tqe.po.*;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登陆控制器
 *
 * @author 广路
 */
@Controller
public class LoginController extends BaseController {

    Log logger = LogFactory.getLog(LoginController.class);

    /**
     * 用户登陆
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(
            @ModelAttribute User user,
            HttpSession session,
            @RequestParam(required = false) String verificationCode,
            HttpServletResponse response,
            Model model
    ) {

        String type = user.getType();
        UserType userType = UserType.toUserType(type);
        LoginType loginType = (LoginType) session.getAttribute(BaseConfigKye.LoginType);

        session.setAttribute("lastLoginTime", new Date().getTime());   //设置最后一次登录时间

        Boolean skipVerify = (Boolean) session.getAttribute("skipVerify");  //是否需要输入跳过验证码
        if (skipVerify == null) {   //如果session已经失效 说明不需要验证码了
            skipVerify = false;
        }

        //如果需要检查验证码
        if (!skipVerify && userType.equals(UserType.ADMIN)) {
            //如果输入的验证码不正确
            if (StringUtils.isBlank(verificationCode) || !verificationCode.equals(session.getAttribute("verificationCode"))) {
                model.addAttribute("error", "验证码错误");
                session.setAttribute("skipVerify", false);   //需要验证码
                return "index";
            }
        }

        boolean loginSuccess = false;
        //教务系统验证并且角色为学生和教师
        if (LoginType.SCHOOL.equals(loginType) &&
                (userType.equals(UserType.STUDENT) || userType.equals(UserType.TEACHER))) {
            String errorMsg = schoolSecurityCheck(user, verificationCode, session);
            if (errorMsg == null) {
                loginSuccess = true;
            } else {
                model.addAttribute("error", errorMsg);
                return "index";
            }
        }

        //记住用户最后的登录方式
        Cookie cookie = new Cookie("loginType", type);
        cookie.setMaxAge(365 * 24 * 60 * 60);
        response.addCookie(cookie);
        String viewName = "index";
        user.setPassWordConvertMD5(user.getPassword());
        switch (userType) {
            case ADMIN:
                Admin a = adminService.login(user);
                if (a != null) {
                    session.setAttribute("admin", a);
                    viewName = "redirect:/admin/admin";
                    loginSuccess = true;
                }
                break;
            case TEACHER:
                Teacher t = null;
                if (!loginSuccess) {
                    t = teacherService.login(user);
                }else{
                    t = teacherService.getById(user.getUsername());
                }
                if(t==null){
                    throw new UserNotExistException(user.getUsername());
                }
                session.setAttribute("teacher", t);
                viewName = "redirect:/admin/teaEval";
                loginSuccess = true;
                break;
            case STUDENT:
                Student stu = null;
                if (!loginSuccess) {
                    stu = studentService.login(user);
                }else{
                    stu = studentService.getById(user.getUsername());
                }
                if(stu==null){
                    throw new UserNotExistException(user.getUsername());
                }
                session.setAttribute("student", stu);
                viewName = "redirect:/admin/stuEval";
                loginSuccess = true;
                break;
            case LEADER:
                Leader leader = leaderService.login(user);
                if (leader != null) {
                    session.setAttribute("leader", leader);
                    viewName = "redirect:/admin/leaEval";
                    loginSuccess = true;
                }
                break;
            default:
                logger.error("未知的登录角色！ " + type);
                model.addAttribute("error", "未知的登录用户的角色！");
                return "index";
        }

        if (loginSuccess) {
            List<Privilege> pList = privilegeService.findAllByUserType(userType);
            addPrivilege(session, pList , userType);   //权限信息放入session
            removeOtherUser(session, userType); //移除当前session中其他已经登陆的用户
            session.setAttribute("userType", userType);  //设置当前用户的类型
            return viewName;    //返回到用户主页
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "index";
        }

    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        removeOtherUser(session, null);
        return "redirect:/index";
    }

    /**
     * 添加权限信息到session中 用于前台进行权限的显示的判断
     * 但是如果角色获取的权限信息在数据库中没有发生变化
     * 那么就拷贝applicationScope中的一个引用
     */
    private void addPrivilege(HttpSession session, List<Privilege> pList, UserType userType) {
        ServletContext application =  session.getServletContext();
        List<Privilege> applicationPList = (List<Privilege>) application.getAttribute(userType.getName()+"pList");
        HashMap<String, Boolean> applicationPMap = (HashMap<String, Boolean>) application.getAttribute(userType.getName()+"pMap");

        //如果application没有存在对应角色的权限数据 或者存在的数据和当前获取的角色权限数据不一致
        //就把从数据库中获取到的新权限数据放到application中
        if(applicationPList==null || applicationPMap==null || !ListUtils.isEqualList(applicationPList,pList)){

            applicationPList = pList;
            application.setAttribute(userType.getName()+"pList",applicationPList);

            HashMap<String, Boolean> map = new HashMap<String, Boolean>();
            for (Privilege p : pList) {
                map.put(p.getUrl().substring(1), true);
            }
            applicationPMap = map;
            application.setAttribute(userType.getName()+"pMap",map);
            logger.info("update application privilegeList and privilegeMap , userType:"+userType);

        }

        session.setAttribute("pList", applicationPList);
        session.setAttribute("pMap", applicationPMap);
    }

    /**
     * 用户登陆时，移除其他无关的角色，保证系统同一时刻只有一个角色能登陆
     */
    private void removeOtherUser(HttpSession session, UserType userType) {
        if (userType == null) { //移除当先回话中的所有用户
            for (UserType uType : UserType.values()) {
                session.removeAttribute(uType.getName());
            }
            return;
        }
        //如果指定了角色，那么移除其他角色的session信息
        for (UserType uType : UserType.values()) {
            if (!uType.equals(userType)) {
                session.removeAttribute(uType.getName());
            }
        }
    }

    private String schoolSecurityCheck(User user, String verificationCode, HttpSession session) {
        String errorMsg = null;
        String proxyJsessionId = (String) session.getAttribute("proxyJsessionId");
        if (StringUtils.isBlank(proxyJsessionId)) {
            errorMsg = "从教务系统获取JsessionId出错!暂时不能进行登陆";
            logger.error(errorMsg + user);
            return errorMsg;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("j_username", user.getUsername());
        params.put("j_password", user.getPassword());
        params.put("j_captcha", verificationCode);
        Document doc = null;
        try {
            doc = Jsoup.connect("http://202.118.201.228/academic/j_acegi_security_check").cookie(JSESSIONID, proxyJsessionId)
                    .data(params).followRedirects(true).post();
            Element errorElement = doc.getElementById("error");
            if (errorElement != null) {
                errorMsg = errorElement.html();
            }
        } catch (IOException e) {
            errorMsg = "连接理工教务在线失败！请稍后再试!";
            logger.error(errorMsg, e);
            return errorMsg;
        }

        if (StringUtils.isBlank(errorMsg)) {
            logger.info("登陆成功!" + user);
            return null;
        } else {
            return errorMsg;
        }
    }
}
