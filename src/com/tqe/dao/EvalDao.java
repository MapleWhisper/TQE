package com.tqe.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.EvalTable;
import com.tqe.po.LeaResultTable;
import com.tqe.po.StuResultTable;
import com.tqe.po.TeaStuResultTable;
import com.tqe.po.TeaResultTable;

@Repository
public interface EvalDao extends BaseDao<EvalTable>{
	
	
	@Insert("INSERT INTO `tqe`.`stutable` (`id`, `sid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tid`,`sname`,`tname`,`question1`,`question2`,`question3`,`question4`,`question5`,`departmentid`) "
										+ "	VALUES (null, #{sid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString},#{tid},#{sname},#{tname},#{question1},#{question2},#{question3},#{question4},#{question5},#{departmentId});")
	public void saveStuTable(StuResultTable stuTable);
	
	@Insert("INSERT INTO `tqe`.`teatable` (`id`, `tid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`question1`,`question2`,`question3`,`question4`,`question5`,`departmentid`) "
								+ "VALUES (null, #{tid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{question1},#{question2},#{question3},#{question4},#{question5},#{departmentId});")
	public void saveTeaTable(TeaResultTable teaTable);
	
	@Insert("INSERT INTO `tqe`.`leatable` (`id`,`lid`,  `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`question1`,`question2`,`question3`,`question4`,`question5`,`departmentid`) "
							 + "	VALUES (null, #{lid},  #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{question1},#{question2},#{question3},#{question4},#{question5},#{departmentId});")
	public void saveLeaTable(LeaResultTable leaTable);
	
	@Insert("INSERT INTO `tqe`.`teaStutable` (`id`,`tid`,`sid`,  `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`sname`,`question1`,`question2`,`question3`,`question4`,`question5`,`departmentid`) "
						+ "	VALUES (null, #{tid},#{sid},  #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{sname},#{question1},#{question2},#{question3},#{question4},#{question5},#{departmentId});")
	public void saveTeaStuTable(TeaStuResultTable teaStuTable);
	
	
	@Select("select `id`,  `sid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from stutable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<StuResultTable> findAllStuTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);
	
	@Select("select `id`,  `tid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from teatable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<TeaResultTable> findAllTeaTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);
	
	@Select("select `id`,  `lid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from leatable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<LeaResultTable> findAllLeaTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);
	
	@Select("select `id`,  `tid`,`sid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level`,`tname` from teaStutable where sid =#{sid}  and bid =#{bid} ")
	public List<TeaStuResultTable> findAllTeaStuTableBySid(@Param("sid") String sid, @Param("bid")Integer bid);

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

	


	

	

	
	

	

	

	

	


	

	
}
