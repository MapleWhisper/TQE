package com.tqe.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.EvalTable;
import com.tqe.po.StuTable;

@Repository
public interface EvalDao extends BaseDao<EvalTable>{
	@Insert("INSERT INTO `tqe`.`stutable` (`id`, `sid`, `eid`, `cid`, `cno`, `bid`, `score`, `level`, `jsonString`) VALUES (null, #{sid}, #{eid}, #{cid}, #{cno}, #{bid}, #{score}, #{level}, #{jsonString});")
	public void saveStuTable(StuTable stuTable);
	
	
	
	@Select("select `id`,  `sid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level` from stutable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<StuTable> findAllStuTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);

	@Select("select * from stutable where id = #{stuTableId}")
	public StuTable getStuTableById(Integer stuTableId);
	
	@Select("select cid from stutable where sid = #{sid} and bid = #{bid}")
	public List<String> findAllStuTablecids(@Param("sid")Integer sid,@Param("bid")Integer bid);
	
	@Select("select concat(cid,cno) from teatable where tid = #{tid} and bid = #{bid}")
	public List<String> findAllTeaTablecids(@Param("tid")Integer tid, @Param("bid")Integer bid);

	
}
