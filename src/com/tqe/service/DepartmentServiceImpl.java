package com.tqe.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tqe.po.Department;

@Service
public class DepartmentServiceImpl extends BaseService<Department>{
	@Override
	@Cacheable(value="mCache")
	public List<Department> findAll() {
		
		return departmentDao.findAll();
	}
}
