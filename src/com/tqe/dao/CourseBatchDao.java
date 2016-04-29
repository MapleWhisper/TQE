package com.tqe.dao;

import com.tqe.po.BatchScore;
import com.tqe.po.CourseBatch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseBatchDao {

	@Select("select * from `courseBatch` where cid=#{cid} and cno=#{cno} and bid = #{bid}")
    CourseBatch getById(@Param("cid") String cid, @Param("cno") int cno , @Param("bid")int bid);

    @Insert("INSERT INTO `tqe`.`coursebatch` " +
            "(`cid`, `cno`, `bid`, `tid` , `stuEvalAvgScore`, `teaEvalAvgScore`, `leaEvalAvgScore`, `stuEvalTotal`, `stuEvalCnt`, `stuEvalLevelCnts`, `teaEvalLevelCnts`, `leaEvalLevelCnts`, `stuEvalTableJsonString`, `teaEvalTableJsonString`, `leaEvalTableJsonString` ,`mtime`) VALUES " +
            "(#{cid}, #{cno}, #{bid}, #{tid} ,#{stuEvalAvgScore}, #{teaEvalAvgScore}, #{leaEvalAvgScore}, #{stuEvalTotal}, #{stuEvalCnt}, #{stuEvalLevelCnts}, #{teaEvalLevelCnts}, #{leaEvalLevelCnts}  ,#{stuEvalTableJsonString} ,#{teaEvalTableJsonString} ,#{leaEvalTableJsonString},now() );")
    void save(CourseBatch courseBatch);

    @Update("UPDATE `tqe`.`coursebatch` SET `stuEvalAvgScore`=#{stuEvalAvgScore}, `teaEvalAvgScore`=#{teaEvalAvgScore}," +
            " `leaEvalAvgScore`=#{leaEvalAvgScore}, `stuEvalTotal`=#{stuEvalTotal}, `stuEvalCnt`=#{stuEvalCnt}, `stuEvalLevelCnts`=#{stuEvalLevelCnts}, " +
            "`teaEvalLevelCnts`=#{teaEvalLevelCnts}, `leaEvalLevelCnts`=#{leaEvalLevelCnts} ,`stuEvalTableJsonString`=#{stuEvalTableJsonString}," +
            " `teaEvalTableJsonString`=#{teaEvalTableJsonString}, `leaEvalTableJsonString`=#{leaEvalTableJsonString} ,`mtime` = now() WHERE  `cid`=#{cid} AND `cno`=#{cno} AND `bid`=#{bid};")
    void update(CourseBatch courseBatch);

    @Select("SELECT  `cid`,  `cno`,  `bid`, `tid` , `stuEvalAvgScore`,  `teaEvalAvgScore`,  `leaEvalAvgScore`,  `stuEvalTotal`,  `stuEvalCnt`,  `stuEvalLevelCnts`,  `teaEvalLevelCnts`,  `leaEvalLevelCnts` " +
            " FROM `tqe`.`coursebatch`  where cid = #{cid} and cno = #{cno}")
    List<CourseBatch> getAllByCidAndCno(@Param("cid")String cid, @Param("cno")Integer cno);

    @Select("   select b.name 'batchName', cb.stuEvalAvgScore 'stuAvgScore' , cb.teaEvalAvgScore 'teaAvgScore'\n" +
            "   from batches b , courseBatch cb   where b.id = cb.bid and cb.tid = #{tid} order by b.endDate")
    List<BatchScore> getTeacherBatchList(String tid);

    @Select("select distinct bid from coursebatch where tid = #{tid} ")
    List<Integer> findBidByTid(String tid);

    @Select(" select b.name 'batchName', cb.tid 'id'  , cb.bid, avg(cb.stuEvalAvgScore) 'stuAvgScore' , avg(cb.teaEvalAvgScore) 'teaAvgScore' "
           +" from batches b , courseBatch cb   where b.id = cb.bid and cb.tid = #{tid} and bid = #{bid} group by cb.tid, cb.bid ")
    BatchScore getTeacherBatchScore(@Param("tid")String tid, @Param("bid")Integer bid);
}
