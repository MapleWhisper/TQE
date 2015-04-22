package com.tqe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tqe.po.Major;

@Service
public class MajorServiceImpl extends BaseService<Major>{
	@Override
	public List<Major> findAll() {
		
		return majorDao.findAll();
	}
	
	/**
	 * 根据部门Id找到对应的专业
	 * @param did
	 * @return
	 */
	public List<Major> findAllByDid(Integer did) {
		
		return majorDao.findAllByDid(did);
	}
}
