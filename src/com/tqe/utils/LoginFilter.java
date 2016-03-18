package com.tqe.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.tqe.po.Admin;
import com.tqe.po.Leader;
import com.tqe.po.Privilege;
import com.tqe.po.Student;
import com.tqe.po.Teacher;

@Component
public class LoginFilter implements  Filter{
	
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req =  (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getRequestURI();	//得到请求Url
		HttpSession session = req.getSession();
		String ctxPath = req.getContextPath();

		Student stu = (Student) session.getAttribute("student");
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Admin admin = (Admin) session.getAttribute("admin");
		Leader leader = (Leader) session.getAttribute("leader");
		
		if(!path.contains("/admin")){	//如果访问的不是后台数据，那么跳过
			chain.doFilter(req, resp);
			return;
		}
		
		if( stu==null && tea==null && admin==null && leader==null){		//如果都没有登陆，就返回登录页
			resp.sendRedirect(ctxPath+"/index");
			return;
		}
		if(admin!=null && admin.getUsername().equals("admin")){		//如果是超级管理员，那么不进行权限检查
			chain.doFilter(req, resp);
			return;
		}
		//如果登陆了 那么就检查权限
		@SuppressWarnings("unchecked")
		//获取登录用户的权限列表
		List<Privilege> plist = (List<Privilege>) session.getAttribute("pList");
		boolean f = checkPrivilege(path, plist);
		if(f){
			chain.doFilter(req, resp);
		}else{
			session.setAttribute("msg", "对不起，您没有访问权限");
			resp.sendRedirect(ctxPath+"/error");
			return;
		}
		
		
	}
	
	/**
	 * 根据用户访问路径 判断用户是否有权限访问
	 * @param path
	 * @param pList
	 * @return
	 */
	public boolean checkPrivilege(String path,List<Privilege> pList){

        if(path.startsWith("/admin")){
            path = path.substring(6);
        }

        boolean pathIsFirstLevel = path.indexOf("/")==path.lastIndexOf("/");

		for(Privilege p : pList){
            String url = p.getUrl();
            boolean urlIsFirstLevel = url.indexOf("/")==url.lastIndexOf("/");

            //如果是一级权限 /privilege
			if( pathIsFirstLevel && urlIsFirstLevel ){
                if(path.contains(url)){
                    return true;
                }
            }
            //二级权限和 二级权限对比
            if( !pathIsFirstLevel && !urlIsFirstLevel){
                if(path.contains(url)){
                    return true;
                }
            }
		}
		return false;

	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
		
	}
}
