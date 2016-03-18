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
public class AdminServiceImpl extends BaseService<Admin>{
	@Autowired
	private AdminDao adminDao;
	@Override
	public Admin getById(Integer id) {
        Admin admin = adminDao.getById(id);
        admin.setPassword("******");
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

    public void update(Admin admin) {
        if(admin==null || admin.getId()==null){
            throw new IllegalArgumentException("id ����Ϊnull");
        }

        adminDao.update(admin);

        if(!admin.getPassword().contains("***")){
            User user = new User();
            user.setId(admin.getId()+"");
            user.setMd5Password(admin.getPassword());
            adminDao.updatePwd(user);
        }
    }
}
