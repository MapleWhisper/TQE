package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.EvalTable;

@Repository
public interface EvalTableDao extends BaseDao<EvalTable>{
	@Select("select * from evalTable where id = #{id}")
	public EvalTable getById(@Param("id")Integer id);
	
	@Insert("insert into evalTable values(null,#{type},#{title},#{note},#{createDate},#{jsonString},#{questionNameList}) ")
	public void save(EvalTable	EvalTable);
	
	@Select("select * from evalTable")
	public List<EvalTable> findAll();
	
	@Delete("delete from EvalTable where id = #{id}")
	public void delete(Integer id);
	
	@Update("update evaltable set title = #{title} , note = #{note} ,jsonString = #{jsonString} ,questionNameList = #{questionNameList} where id = #{id}")
	public void update(EvalTable eTable);


	
}
