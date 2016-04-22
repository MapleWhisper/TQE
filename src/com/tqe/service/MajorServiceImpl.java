package com.tqe.service;

import java.util.List;

import com.tqe.base.vo.PageVO;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(value="mCache",key="#did+'findAllByDid'")
	public List<Major> findAllByDid(Integer did) {
		
		return majorDao.findAllByDid(did);
	}
}
