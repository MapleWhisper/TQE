package com.tqe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tqe.dao.TeacherDao;
import com.tqe.po.Admin;
import com.tqe.po.Teacher;
import com.tqe.po.User;

@Service
public class TeacherServiceImpl extends BaseService<Teacher>{
	@Resource(name="teacherDao")
	private TeacherDao teacherDao;
	@Override
	public Teacher getById(Integer id) {
		return  teacherDao.getById(id);
	}
	
	@Override
	public void save(Teacher e) {
		teacherDao.save(e);
	}
	
	public boolean saveAll(List<Teacher> list){
		boolean f = false;
		try {
			if(list!=null){
				for(Teacher t:list){
					save(t);
				}
				f= true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
		
	}
	
	@Override
	public List<Teacher> findAll() {
		
		return teacherDao.findAll();
	}
	
	public Teacher login(User user) {
		return teacherDao.login(user);
	}

	public List<Teacher> findByPage(int start, int length) {
		return teacherDao.findByPage(start ,length);
	}
}
