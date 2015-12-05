package com.tqe.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.Batches;

/**
 * @author 广路
 *
 */
@Repository
public interface BatchesDao extends BaseDao<Batches>{
	@Select("select * from Batches where id = #{id}")
	public Batches getById(@Param("id")Integer id);
	
	@Insert("insert into batches( id,name,courseNumber, curCourseNumber , season) values(null,#{name},#{courseNumber},#{curCourseNumber},#{season}) ")
	public void save(Batches batches);
	
	@Select("select * from batches")
	public List<Batches> findAll();
	
	@Delete("delete from batches where id = #{id}")
	public void delete(int id);
	
	@Insert("update  batches set beginDate = #{beginDate}, endDate = #{endDate} , teaStuEvalId = #{teaStuEvalId},"
			+ "stuEvalId = #{stuEvalId} , teaEvalId = #{teaEvalId}, leadEvalId = #{leadEvalId} where id = #{id}")
	public void update(Batches b);
	
	@Select("select * from batches b where now() between b.beginDate and b.endDate and b.season = #{season}")
	public Batches getAvailiableBatches(@Param("season")String season);
	
	@Select("select max(endDate) from batches where id != #{id}")
	public Date getLatestDate(@Param("id")Integer id);
	
	@Select("select * from batches where season = #{season}  order by beginDate desc")
	public List<Batches> findAllBySeason(@Param("season")String season);
	
}
