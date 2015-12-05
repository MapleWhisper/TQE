package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.Major;

@Repository
public interface MajorDao extends BaseDao<Major>{
	String major = "major";
	
	@Override
	@Select(value="select * from major")
	List<Major> findAll();
	
	@Select(value="select * from major where departmentid = #{did}")
	List<Major> findAllByDid(Integer did);

	@Override
	@Insert(value = "insert into major values(null,#{name},#{departmentId})")
	void save(Major major);
}
