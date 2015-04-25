package com.tqe.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.tqe.po.Privilege;

@Service
public class PrivilegeService extends BaseService<Privilege>{
	
	@Override
	public Privilege getById(Integer id) {
		return privilegeDao.getById(id);
	}
	@Override
	public List<Privilege> findAll() {
		return privilegeDao.findAll();
	}

	public List<Privilege> findAdminAll() {
		return privilegeDao.findAdminAll();
	}
	
	
	public List<Privilege> findStudentAll(){
		return privilegeDao.findStudentAll();
	}
	
	public List<Privilege> findTeacherAll(){
		return privilegeDao.findTeacherAll();
	}
	public void update(Privilege p) {
		privilegeDao.update(p);
	}
}	
