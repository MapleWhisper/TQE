package com.tqe.controller;


import javax.servlet.http.HttpSession;

import com.tqe.base.BaseResult;
import com.tqe.base.enums.BaseConfigKye;
import com.tqe.base.enums.LoginType;
import com.tqe.base.enums.UserType;
import com.tqe.po.*;
import com.tqe.service.BaseConfigServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.Index;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqe.utils.MD5Utils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
public class IndexController extends BaseController{

    private static final Log logger = LogFactory.getLog(IndexController.class);
    /**
	 * 进入首页 登陆页面
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(
            Model model,
            HttpSession session
    ){
        LoginType loginType = LoginType.toLoginType(baseConfigService.getConfigValue(BaseConfigKye.LoginType));
        session.setAttribute(BaseConfigKye.LoginType,loginType);
        if(LoginType.INNER.equals(loginType)){  //内置登陆
            boolean skipVerify = false;
            //默认都要输入验证码
            /*
            Long lastLoginTime = (Long) session.getAttribute("lastLoginTime");
            if(lastLoginTime == null || lastLoginTime == 0){  //用户第一次登陆
                skipVerify = true;
            }else{
                Long curTime = new Date().getTime();
                if( (curTime-lastLoginTime)/(1000*60) >5 ){  //5分钟内重新登录需要验证码
                    skipVerify = true;
                }
            }
            */

            session.setAttribute("skipVerify",skipVerify);
        }else{  //学校教务系统登陆

            String jsessionIdString = "";

            Map<String,String> cookies = null;
            try {
                cookies = Jsoup.connect("http://202.118.201.228/academic/common/security/login.jsp").execute().cookies();
                for(Map.Entry<String, String> cookie : cookies.entrySet()){
                    System.out.println(cookie.getKey()+" "+cookie.getValue());
                    if(cookie.getKey().equals(JSESSIONID)){
                        jsessionIdString = cookie.getKey()+"="+cookie.getValue();
                        session.setAttribute("proxyJsessionId",cookie.getValue());
                    }
                }
                if(StringUtils.isBlank(jsessionIdString)){
                    sendError(model,"获取JSessionId失败！当前不能进行登陆",logger);
                }
                session.setAttribute("proxyJsessionIdString",jsessionIdString);
            } catch (IOException e) {
                sendError(model,"连接理工教务在线失败！请稍后再试",logger,e);
            }


        }
        return "index";
    }

    @RequestMapping("toIndex")
    public String toIndex(){
        return "redirect:/index";
    }

    /**
     *
     * 修改密码页面
     */
    @RequestMapping("/admin/resetPwdUI")
    public String resetPwdUI(){

        return "model/resetPwd";
    }

    @RequestMapping("/admin/profile")
    public String profile(
            HttpSession session,
            Model model
    ) {
        UserType userType = (UserType) session.getAttribute("userType");
        if(userType==null){
            return "redirect:/index";
        }
        switch (userType){
            case ADMIN:
                return TO_ADMIN_HOME;
            case STUDENT:
                Student stu = (Student) session.getAttribute("student");
                return "redirect:/admin/student/show?sid="+stu.getSid();
            case TEACHER:
                Teacher tea = (Teacher) session.getAttribute("teacher");
                return "redirect:/admin/teacher/show?tid="+tea.getId();
            case LEADER:
                return TO_Leader_HOME;
            default:
                return sendError(model,"位置的用户类型！");
        }
    }

    /**
     *
     * 重新设置密码
     */
    @RequestMapping("/admin/resetPwd")
    @ResponseBody
    public BaseResult resetPwd(
            String id ,
            String pwd,
            String oldPwd,
            HttpSession session)
    {
        User user = new User();
        String Md5oldPwd = MD5Utils.string2MD5(oldPwd);
        if(session.getAttribute("admin")!=null){
            Admin admin = adminService.getById(Integer.parseInt(id));
            if(admin.getPassword().equalsIgnoreCase(Md5oldPwd)){

                user.setId(admin.getId() + "");
                user.setUsername(admin.getUsername());
                user.setPassWordConvertMD5(pwd);
                user.setType(UserType.ADMIN.getName());

            }
        }else if(session.getAttribute("teacher")!=null){
            Teacher teacher = teacherService.getById(id);

            if(teacher.getPassword().equalsIgnoreCase(Md5oldPwd)){
                user.setId(teacher.getId());
                user.setPassWordConvertMD5(pwd);
                user.setType(UserType.TEACHER.getName());
            }
        }else if(session.getAttribute("student")!=null){
            Student student = studentService.getById(id+"");
            if(student.getPassword().equalsIgnoreCase(Md5oldPwd)){
                user.setId(student.getSid()+"");
                user.setPassWordConvertMD5(pwd);
                user.setType(UserType.STUDENT.getName());
            }
        }else{
            return BaseResult.createFailure("当前没有登录的用户！");
        }
        if(StringUtils.isNotBlank(user.getPassword())){
            commonService.updatePwd(user);
            return BaseResult.createSuccess("密码修改成功");
        }
        return BaseResult.createFailure("用户原密码错误！！请重新输入");

    }

    /**
     * 根据学院获取所有的专业
     */
    @RequestMapping("getMajor/{did}")
    @ResponseBody
    public Object getMajor(@PathVariable Integer did){
        return majorService.findAllByDid(did);
    }

    /**
     * 根据 学院 专业 年级 获取所有的班级
     */
    @RequestMapping("getClazz")
    @ResponseBody
    public Object getClazz(
            @RequestParam Integer did,
            @RequestParam Integer mid,
            @RequestParam String grade

    ){
        return clazzService.findAllByDidMid(did, mid,grade.trim());
    }

    @RequestMapping("error")
    public String error(
            Model model,
            @RequestParam(required = false) String msg
    ){
        if(StringUtils.isNotBlank(msg)){
            model.addAttribute("msg",msg);
        }else{
            model.addAttribute("msg","未知的错误");
        }
        return "error";
    }

    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
