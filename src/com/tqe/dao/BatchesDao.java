package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.Batches;

@Repository
public interface BatchesDao extends BaseDao<Batches>{
	@Select("select * from Batches where id = #{id}")
	public Batches getById(@Param("id")int id);
	@Insert("insert into Batches values(null,#{name},#{courseNumber},#{curCourseNumber},#{season}) ")
	public void save(Batches batches);
	
	@Select("select * from Batches")
	public List<Batches> findAll();
	
	
	@Delete("delete from Batches where id = #{id}")
	public void delete(int id);
	
}
