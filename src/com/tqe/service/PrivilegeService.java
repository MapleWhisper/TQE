package com.tqe.service;

import java.util.ArrayList;
import java.util.List;

import com.tqe.base.enums.UserType;
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

    public List<Privilege> findAllByUserType(UserType userType){
        if(userType == null){
            return new ArrayList<Privilege>();
        }
        switch (userType){
            case ADMIN:
                return findAdminAll();
            case STUDENT:
                return findStudentAll();
            case TEACHER:
                return findTeacherAll();
            case LEADER:
                return findLeaderAll();
            default:
                return new ArrayList<Privilege>();
        }

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
	public List<Privilege> findLeaderAll() {
		return privilegeDao.findLeaderAll();
	}
}	
