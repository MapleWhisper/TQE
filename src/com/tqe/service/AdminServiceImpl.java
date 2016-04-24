package com.tqe.service;

import com.tqe.dao.AdminDao;
import com.tqe.po.Admin;
import com.tqe.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class AdminServiceImpl extends BaseService<Admin>{
	@Autowired
	private AdminDao adminDao;
	@Override
	public Admin getById(Integer id) {
        Admin admin = adminDao.getById(id);
        admin.setPassword("******");
		return admin;
	}
	
	@Override
	public void save(Admin admin) {
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()).toLowerCase());
		adminDao.save(admin);
	}

	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	public Admin login(User user) {
		return adminDao.login(user);
	}

	public void delete(int id) {
        Admin a = adminDao.getById(id);
        if(a==null){
            return ;
        }else{
            if(a.getUsername().equals("admin")){
                throw new IllegalArgumentException("您不可以删除超级管理员！");
            }
        }
		adminDao.delete(id);
		
	}

    public void update(Admin admin) {
        if(admin==null || admin.getId()==null){
            throw new IllegalArgumentException("id 不能为空");
        }
        if(admin.getUsername().equals("admin")){    //超级管理员的信息 不能修改
            admin.setName("admin");
            admin.setPosition("超级管理员");
        }
        adminDao.update(admin);

        if(!admin.getPassword().contains("*")){
            User user = new User();
            user.setId(admin.getId()+"");
            user.setPassWordConvertMD5(admin.getPassword());
            adminDao.updatePwd(user);
        }
    }
}
