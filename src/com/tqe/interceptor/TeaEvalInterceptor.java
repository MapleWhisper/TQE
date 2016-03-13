package com.tqe.interceptor;
		
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component	 
public class TeaEvalInterceptor extends HandlerInterceptorAdaptor{
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		HttpSession session = req.getSession();
		if(session.getAttribute("teacher")==null){
			sendError(req, resp, "�Բ�������û�е�½�����¼��ʦ��Ϣ");
			return false;
		}
		return true;
	}
	

}
