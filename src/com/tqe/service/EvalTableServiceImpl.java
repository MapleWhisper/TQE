package com.tqe.service;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tqe.dao.EvalTableDao;
import com.tqe.po.EvalTable;

@Service
public class EvalTableServiceImpl extends BaseService<EvalTable>{
	@Autowired
	private EvalTableDao evalTableDao;
	@Override
	public EvalTable getById(Integer id){
		EvalTable evalTable = evalTableDao.getById(id);
		if(evalTable==null){
			return null;
		}
		return evalTable.json2Object();
	}
	
	@Override
	public void save(EvalTable e) {
		for(EvalTable.EvalTableItem item :e.getTableItemList()){
			String level = item.getLevel();
			level=level.replaceAll("，", ",");	//把评教的分隔符统一为空格分隔
			level=level.replaceAll(",", " ");
			item.setLevel(level);
		}
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
