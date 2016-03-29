package com.tqe.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tqe.po.Clazz;

@Service
public class ClassServiceImpl extends BaseService<Clazz>{
	@Override
	public List<Clazz> findAll() {
		
		return classDao.findAll();
	}
	
	/**
	 * 根据学院Id 和专业id 找到对应的课程
	 * @param did departmentid 学院id
	 * @param mid majorid 专业id
	 * @param grade
     * @return
	 */
	public List<Clazz> findAllByDidMid(Integer did, Integer mid, String grade) {
		return classDao.findAllByDidMid(did,mid,grade);
	}
}
