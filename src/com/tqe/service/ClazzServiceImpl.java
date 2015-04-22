package com.tqe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tqe.dao.ClazzDao;
import com.tqe.po.Clazz;

@Service
public class ClazzServiceImpl extends BaseService<Clazz>{
	@Override
	public List<Clazz> findAll() {
		
		return clazzDao.findAll();
	}
	
	/**
	 * 根据学院Id 和专业id 找到对应的课程
	 * @param did departmentid 学院id
	 * @param mid majorid 专业id
	 * @return
	 */
	public List<Clazz> findAllByDidMid(Integer did,Integer mid) {
		
		return clazzDao.findAllByDidMid(did,mid);
	}
}
