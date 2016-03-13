package com.tqe.interceptor;
		
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component	 
public class StuEvalInterceptor extends HandlerInterceptorAdaptor{
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		HttpSession session = req.getSession();
		if(session.getAttribute("student")==null){
			sendError(req, resp, "对不起，您还没有登陆。请登录学生信息");
			return false;
		}
		return true;
	}
	

}
