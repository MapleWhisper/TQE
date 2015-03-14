package com.tqe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqe.dao.AdminDao;
import com.tqe.po.Admin;
import com.tqe.po.User;
import com.tqe.utils.MD5Utils;

@Service
public class AdminServiceImpl implements BaseService<Admin>{
	@Autowired
	private AdminDao adminDao;
	@Override
	public Admin getById(Integer id) {
		return adminDao.getById(id);
	}
	
	@Override
	public void save(Admin e) {
		adminDao.save(e);
	}

	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	public Admin login(User user) {
		return adminDao.login(user);
	}

	public void delete(int id) {
		adminDao.delete(id);
		
	}
}
