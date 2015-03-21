package com.tqe.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tqe.dao.BatchesDao;
import com.tqe.po.Batches;
import com.tqe.po.Batches;

@Service
public class BatchesServiceImpl implements BaseService<Batches>{
	@Autowired
	private BatchesDao batchesDao;
	@Override
	public Batches getById(Integer id) {
		return batchesDao.getById(id);
	}
	
	@Override
	public void save(Batches e) {
		batchesDao.save(e);
	}

	public List<Batches> findAll() {
		return batchesDao.findAll();
	}

	public void delete(int id) {
		batchesDao.delete(id);
		
	}
}
