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
		String path = req.getRequestURI();	//�õ�����Url
		HttpSession session = req.getSession();
		String ctxPath = req.getContextPath();

		Student stu = (Student) session.getAttribute("student");
		Teacher tea = (Teacher) session.getAttribute("teacher");
		Admin admin = (Admin) session.getAttribute("admin");
		Leader leader = (Leader) session.getAttribute("leader");
		
		if(!path.contains("/admin")){	//������ʵĲ��Ǻ�̨���ݣ���ô����
			chain.doFilter(req, resp);
			return;
		}
		
		if( stu==null && tea==null && admin==null && leader==null){		//�����û�е�½���ͷ��ص�¼ҳ
			resp.sendRedirect(ctxPath+"/index");
			return;
		}
		if(admin!=null && admin.getUsername().equals("admin")){		//����ǳ�������Ա����ô������Ȩ�޼��
			chain.doFilter(req, resp);
			return;
		}
		//�����½�� ��ô�ͼ��Ȩ��
		@SuppressWarnings("unchecked")
		//��ȡ��¼�û���Ȩ���б�
		List<Privilege> plist = (List<Privilege>) session.getAttribute("pList");
		boolean f = checkPrivilege(path, plist);
		if(f){
			chain.doFilter(req, resp);
		}else{
			session.setAttribute("msg", "�Բ�����û�з���Ȩ��");
			resp.sendRedirect(ctxPath+"/error");
			return;
		}
		
		
	}
	
	/**
	 * �����û�����·�� �ж��û��Ƿ���Ȩ�޷���
	 * @param path
	 * @param pList
	 * @return
	 */
	public boolean checkPrivilege(String path,List<Privilege> pList){
		for(Privilege p : pList){
			if(path.endsWith(p.getUrl())){		//��������磨/privilege����ҳ�棬ֱ��Ĭ��Ϊ����
				return true;
			}
		}
		//����Ƕ���Ȩ��
		for(Privilege p : pList){
			if(p.getUrl().lastIndexOf("/")!=0){	//�Ƕ���Ȩ��
				if(path.indexOf(p.getUrl())!=-1){	//����Ͷ�����༶Ȩ��ƥ�䣬��ô����
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
