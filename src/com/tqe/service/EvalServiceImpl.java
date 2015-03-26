package com.tqe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tqe.po.EvalTable;
import com.tqe.po.StuTable;

@Service
public class EvalServiceImpl extends BaseService<EvalTable>{
	public void saveStuTable(StuTable stuTable) throws Exception{
		try {
			evalDao.saveStuTable(stuTable);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public List<String> getAllStuTablecids(Integer sid){
		return evalDao.getAllStuTablecids(sid);
	}
	
}
