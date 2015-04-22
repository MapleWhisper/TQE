package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.Major;

@Repository
public interface MajorDao extends BaseDao<Major>{
	public static final String major = "major";
	
	@Override
	@Select(value="select * from major")
	public List<Major> findAll();
	
	@Select(value="select * from major where departmentid = #{did}")
	public List<Major> findAllByDid(Integer did);
}
