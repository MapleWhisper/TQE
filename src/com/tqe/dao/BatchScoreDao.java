package com.tqe.dao;

import com.tqe.po.BatchScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Maple on 2016/4/29.
 */
@Repository
public interface BatchScoreDao extends  BaseDao<BatchScore> {


    @Select("select bs.* from batchScore bs , batches b  where bs.id = #{id} and bs.bid = b.id  order by b.endDate")
    List<BatchScore> findAll(String id);

    @Select("select * from batchscore where id = #{id} and bid = #{bid} ")
    BatchScore getById(@Param("id") String id, @Param("bid") Integer bid);


    @Override
    @Insert("INSERT INTO `tqe`.`batchscore` (`id`, `bid`, `batchName` ,`stuAvgScore`, `teaAvgScore`, `leaAvgScore` ) " +
            "VALUES (#{id},#{bid}, #{batchName} , #{stuAvgScore}, #{teaAvgScore}, #{leaAvgScore} );")
    void save(BatchScore batchScore);


    @Update("update batchscore set stuAvgScore = #{stuAvgScore} , teaAvgScore = #{teaAvgScore} , leaAvgScore = #{leaAvgScore}" +
            " where id = #{id} and bid = #{bid}" )
    void update(BatchScore batchScore);
}
