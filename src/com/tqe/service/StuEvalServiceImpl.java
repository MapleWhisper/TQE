package com.tqe.service;

import java.util.Date;
import java.util.List;

import com.tqe.base.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tqe.dao.StuEvalDao;
import com.tqe.po.EvalTable;

@Service
public class StuEvalServiceImpl extends BaseService<EvalTable>{
	@Autowired
	private StuEvalDao stuEvalDao;
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

	public List<EvalTable> findAll(PageVO type) {
		List<EvalTable> list = evalTableDao.findAll(type);
		for(EvalTable evalTable : list){
			evalTable.json2Object();
		}
		return evalTableDao.findAll(type);
	}

	public void delete(int id) {
		evalTableDao.delete(id);
		
	}
	
	
}
