package com.tqe.dao;


import java.util.List;

import com.tqe.base.vo.PageVO;
import com.tqe.dao.SqlProvider.EvalDaoSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.tqe.po.EvalTable;
import com.tqe.po.LeaResultTable;
import com.tqe.po.StuResultTable;
import com.tqe.po.TeaStuResultTable;
import com.tqe.po.TeaResultTable;

@Repository
public interface EvalDao extends BaseDao<EvalTable>{
	
	
	@Insert("INSERT INTO `tqe`.`stutable` (`id`, `sid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tid`,`sname`,`tname`,`departmentid`,`questionAnsList`) "
										+ "	VALUES (null, #{sid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString},#{tid},#{sname},#{tname},#{departmentId},#{questionAnsList});")
	public void saveStuTable(StuResultTable stuTable);
	
	@Insert("INSERT INTO `tqe`.`teatable` (`id`, `tid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`departmentid`,`questionAnsList`) "
								+ "VALUES (null, #{tid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{departmentId},#{questionAnsList});")
	public void saveTeaTable(TeaResultTable teaTable);
	
	@Insert("INSERT INTO `tqe`.`leatable` (`id`,`lid`,  `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`departmentid`,`questionAnsList`) "
							 + "	VALUES (null, #{lid},  #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{departmentId},#{questionAnsList});")
	public void saveLeaTable(LeaResultTable leaTable);
	
	@Insert("INSERT INTO `tqe`.`teaStutable` (`id`,`tid`,`sid`,  `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`,`tname`,`sname`,`season`,`departmentid`,`questionAnsList`) "
						+ "	VALUES (null, #{tid},#{sid},  #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString} ,#{tname},#{sname}, #{season} , #{departmentId},#{questionAnsList});")
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

	

	

	
	

	

	

	

	


	

	
}
