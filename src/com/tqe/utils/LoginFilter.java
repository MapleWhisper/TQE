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
import com.tqe.po.Privilege;
import com.tqe.po.Student;
import com.tqe.po.Teacher;

@Component
public class LoginFilter implements  Filter{
	
	
	private static List<String> list;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		list = new ArrayList<>();
		list.add("/personalCenter");
		list.add("/resume");
		list.add("/apply");
		list.add("/paper");
	   
		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req =  (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getRequestURI();	//得到请求Url
		HttpSession session = req.getSession();
		String ctxPath = req.getContextPath();
		System.out.println(path);
		
		Student stu = (Student) session.getAttribute("student");
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Admin admin = (Admin) session.getAttribute("admin");
		
		if(path.indexOf("/admin")==-1){	//如果访问的不是后台数据，那么跳过
			chain.doFilter(req, resp);
			return;
		}
		
		if( stu==null && tea==null && admin==null){		//如果都没有登陆，就返回登录页
			resp.sendRedirect(ctxPath+"/index");
			return;
		}
		if(admin!=null && admin.getUsername().equals("admin")){		//如果是超级管理员，那么不进行权限检查
			chain.doFilter(req, resp);
			return;
		}
		//如果登陆了 那么就检查权限
		@SuppressWarnings("unchecked")
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
	
	public boolean checkPrivilege(String path,List<Privilege> pList){
		for(Privilege p : pList){
			if(path.endsWith(p.getUrl())){		//如果是例如（/privilege）的页面，直接默认为继续
				return true;
			}
		}
		//如果是二级权限
		for(Privilege p : pList){
			if(p.getUrl().lastIndexOf("/")!=0){	//是二级权限
				if(path.indexOf(p.getUrl())!=-1){	//如果和二级或多级权限匹配，那么继续
					return true;
				}
			}
		}
		return false; 
	}
	
	@Override
	public void destroy() {
		
	}
}
