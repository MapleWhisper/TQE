package com.tqe.controller;


import javax.servlet.http.HttpSession;

import com.tqe.base.BaseResult;
import com.tqe.base.enums.UserType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqe.po.Admin;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.po.User;
import com.tqe.utils.MD5Utils;

import java.util.Date;

@Controller
public class IndexController extends BaseController{

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

        boolean skipVerify = false;
        Long lastLoginTime = (Long) session.getAttribute("lastLoginTime");
        if(lastLoginTime == null || lastLoginTime == 0){  //用户第一次登陆
            skipVerify = true;
        }else{
            Long curTime = new Date().getTime();
            if( (curTime-lastLoginTime)/(1000*60) >5 ){  //5分钟内重新登录需要验证码
                skipVerify = true;
            }
        }
        session.setAttribute("skipVerify",skipVerify);
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

    /**
     *
     * 重新设置密码
     */
    @RequestMapping("/admin/resetPwd")
    @ResponseBody
    public BaseResult resetPwd(
            Integer id ,
            String pwd,
            String oldPwd,
            HttpSession session)
    {
        User user = new User();
        String Md5oldPwd = MD5Utils.string2MD5(oldPwd);
        if(session.getAttribute("admin")!=null){
            Admin admin = adminService.getById(id);
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
}
