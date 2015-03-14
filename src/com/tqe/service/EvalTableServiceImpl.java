package com.tqe.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tqe.dao.EvalTableDao;
import com.tqe.po.EvalTable;
import com.tqe.po.User;
import com.tqe.utils.MD5Utils;

@Service
public class EvalTableServiceImpl implements BaseService<EvalTable>{
	@Autowired
	private EvalTableDao evalTableDao;
	@Override
	public EvalTable getById(Integer id) {
		return evalTableDao.getById(id).json2Object();
	}
	
	@Override
	public void save(EvalTable e) {
		e.setJsonString(JSON.toJSONString(e));
		e.setCreateDate(new Date(System.currentTimeMillis()));
		evalTableDao.save(e);
	}

	public List<EvalTable> findAll() {
		List<EvalTable> list = evalTableDao.findAll();
		for(EvalTable evalTable : list){
			evalTable.json2Object();
		}
		return evalTableDao.findAll();
	}

	public void delete(int id) {
		evalTableDao.delete(id);
		
	}
	
	
}
