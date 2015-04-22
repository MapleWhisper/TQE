package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.Clazz;
import com.tqe.po.Major;

@Repository
public interface ClazzDao extends BaseDao<Clazz>{
	
	@Override
	@Select(value="select * from class")
	public List<Clazz> findAll();
	
	@Select(value="select * from class where departmentid = #{did} and majorid =#{mid} order by name")
	public List<Clazz> findAllByDidMid(@Param("did")Integer did, @Param("mid")Integer mid);

}
