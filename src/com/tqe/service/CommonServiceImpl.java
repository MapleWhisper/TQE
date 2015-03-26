package com.tqe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tqe.dao.AdminDao;
import com.tqe.dao.TeacherDao;
import com.tqe.po.Admin;
import com.tqe.po.User;

@Controller
public class CommonServiceImpl extends BaseService<Object>{
	
	public void updatePwd(User user){
		if(user.getType().equals("admin")){
			adminDao.updatePwd(user);
		}else if(user.getType().equals("teacher")){
			teacherDao.updatePwd(user);
		}else{
			studentDao.updatePwd(user);
		}
	}
}
