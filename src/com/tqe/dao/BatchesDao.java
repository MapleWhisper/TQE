package com.tqe.dao;

import java.util.Date;
import java.util.List;

import com.tqe.base.vo.PageVO;
import com.tqe.dao.SqlProvider.BatchesDaoSqlProvider;
import com.tqe.po.BatchScore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.tqe.po.Batches;

/**
 *
 */
@Repository
public interface BatchesDao extends BaseDao<Batches>{
	@Select("select * from Batches where id = #{id}")
	public Batches getById(@Param("id")Integer id);
	
	@Insert("insert into batches( id,name,courseNumber, curCourseNumber , season ,beginDate ,endDate)" +
            " values(null,#{name},#{courseNumber},#{curCourseNumber},#{season},#{beginDate},#{endDate} ) ")
	public void save(Batches batches);
	
    @SelectProvider(type = BaseDaoTemplate.class,method = "findBatchAll" )
	public List<Batches> findAll(PageVO pageVO);
	
	@Delete("delete from batches where id = #{id}")
	public void delete(int id);
	
	@Insert("update  batches set beginDate = #{beginDate}, endDate = #{endDate} , teaStuEvalId = #{teaStuEvalId},"
			+ "stuEvalId = #{stuEvalId} , teaEvalId = #{teaEvalId}, leadEvalId = #{leadEvalId} where id = #{id}")
	public void update(Batches b);
	
	@Select("select * from batches b where now() between b.beginDate and b.endDate and b.season = #{season}")
	Batches getAvailableBatches(@Param("season")String season);
	

	
	@Select("select * from batches where season = #{season}  order by beginDate desc")
	public List<Batches> findAllBySeason(@Param("season")String season);



    @SelectProvider(type=BatchesDaoSqlProvider.class, method="checkDateConflict")
    Batches checkDateConflict(@Param("id")Integer id ,@Param("date") Date date);

    @SelectProvider(type=BatchesDaoSqlProvider.class, method="checkDateRangeConflict")
    Batches checkDateRangeConflict(@Param("id")Integer id ,@Param("beginDate")Date beginDate ,@Param("endDate")Date endDate);

    @Update("   update batches  b" +
            " set stuAvgScore = ( select  avg(stuAvgScore) from batchscore where bid = b.id  ), " +
            " teaAvgScore = ( select avg(teaAvgScore) from batchscore where bid = b.id  ), " +
            " teaStuAvgScore = ( select avg(score) from teaStutable where bid = b.id  ) " +
            " where b.id = #{bid} ")
    void updateStatistic(Integer bid);
}
