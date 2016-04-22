package com.tqe.service;

import java.util.List;

import com.tqe.base.enums.DepartmentType;
import com.tqe.base.vo.PageVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tqe.po.Department;

@Service
public class DepartmentServiceImpl extends BaseService<Department>{

	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	public List<Department> findAvailableDepartmentList(DepartmentType departmentType){
		List<Department> depList = null;
		if(departmentType!=null){
			switch (departmentType){
				case STUDENT:
					depList = departmentDao.findDepListContainMajors();
					break;
				case COURSE:
					depList = departmentDao.findDepListContainCourse();
			}
		}
		return depList;
	}

	public void saveOne(Department department){
		departmentDao.save(department);
	}
}
