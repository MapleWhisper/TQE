package com.tqe.dao;


import java.util.List;

import com.tqe.base.vo.PageVO;
import com.tqe.dao.SqlProvider.EvalDaoSqlProvider;
import com.tqe.po.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface EvalDao extends BaseDao<EvalTable>{
	
	
	@Insert("INSERT INTO `tqe`.`stutable` (`id`, `sid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tid`,`sname`,`tname`,`departmentid`) "
										+ "	VALUES (null, #{sid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString},#{tid},#{sname},#{tname},#{departmentId});")
	public void saveStuTable(StuResultTable stuTable);
	
	@Insert("INSERT INTO `tqe`.`teatable` (`id`, `tid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`departmentid`) "
								+ "VALUES (null, #{tid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{departmentId});")
	public void saveTeaTable(TeaResultTable teaTable);
	
	@Insert("INSERT INTO `tqe`.`leatable` (`id`,`lid`,  `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`departmentid`) "
							 + "	VALUES (null, #{lid},  #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{departmentId});")
	public void saveLeaTable(LeaResultTable leaTable);
	
	@Insert("INSERT INTO `tqe`.`teaStutable` (`id`,`tid`,`sid`,  `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`sname`,`season`,`departmentid`) "
						+ "	VALUES (null, #{tid},#{sid},  #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{sname}, #{season} , #{departmentId});")
	public void saveTeaStuTable(TeaStuResultTable teaStuTable);
	
	
	@Select("select `id`,  `sid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from stutable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<StuResultTable> findAllStuTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);
	
	@Select("select `id`,  `tid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from teatable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<TeaResultTable> findAllTeaTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);
	
	@Select("select `id`,  `lid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from leatable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<LeaResultTable> findAllLeaTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);
	
    @SelectProvider(type = EvalDaoSqlProvider.class,method = "findTeaStuResultTable")
	List<TeaStuResultTable> findTeaStuResultTable(PageVO pageVO);

    @Select("select * from stutable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
    List<StuResultTable> findAllStuTableWithJSONString(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);

    @Select("select * from teatable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
     List<TeaResultTable> findAllTeaTableWithJSONString(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);

    @Select("select * from leatable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
     List<LeaResultTable> findAllLeaTableWithJSONString(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);

    @Select("select * from teaStutable where sid =#{sid}  and bid =#{bid} ")
     List<TeaStuResultTable> findAllTeaStuTableWithJSONString(@Param("sid") String sid, @Param("bid")Integer bid);

	@Select("select * from stutable where id = #{stuTableId}")
	public StuResultTable getStuTableById(Integer stuTableId);
	
	@Select("select * from teatable where id = #{teaTableid}")
	public TeaResultTable getTeaTableById(Integer teaTableid);
	
	@Select("select * from leatable where id = #{leaTableId}")
	public LeaResultTable getLeaTableById(Integer leaTableId );
	
	@Select("select * from teaStuTable where id = #{teaStuTableId}")
	public TeaStuResultTable getTeaStuTableById(Integer teaStuTableId);
	
	@Select("select cid from stutable where sid = #{sid} and bid = #{bid}")
	public List<String> findAllStuTablecids(@Param("sid")String sid,@Param("bid")Integer bid);
	
	
	
	@Select("select concat(cid,cno) from teatable where tid = #{tid} and bid = #{bid}")
	public List<String> findAllTeaTablecids(@Param("tid")String tid, @Param("bid")Integer bid);

	@Select("select sid from teastutable where tid = #{tid} and cid = #{cid} and cno = #{cno} and bid = #{bid}")
	public List<String> findAllSidsByCidTid(@Param("cid")String cid, @Param("cno")Integer cno,@Param("tid")String tid, @Param("bid")Integer bid);


    /**
     *  统计指定课程指定批次 学生已经评教的人数
     */
    @Select("select count(*) from stutable where cno = #{cno} and cid = #{cid} and bid = #{bid}")
    int cntStuEvalByCidCnoBid(@Param("cid")String cid,@Param("cno")Integer cno,@Param("bid")Integer bid);


    @Select("   select b.name 'batchName', b.id 'bid'  , ts.sid 'id' , avg(ts.score) 'teaStuAvgScore' " +
            "   from batches b , teastutable ts   " +
            "   where b.id = ts.bid and ts.sid = #{sid}   and bid = #{bid} ")
    BatchScore getStudentBatchScore(@Param("sid")String sid, @Param("bid")Integer bid);

    @Select("select distinct bid from teastutable where sid = #{id} ")
    List<Integer> findTeaStuBidBySid(String id);
}
