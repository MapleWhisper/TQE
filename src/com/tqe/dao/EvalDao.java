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
	
	@Select("select cid from stutable where sid = #{sid}")
	public List<String> getAllStuTablecids(Integer sid);

	@Select("select * from stutable where cid =#{cid} and cno=#{cno} and bid =#{bid} ")
	public List<StuTable> findAllStuTableByCourse(@Param("cid")String cid, @Param("cno")Integer cno, @Param("bid")Integer bid);

	@Select("select * from stutable where id = #{stuTableId}")
	public StuTable getStuTableById(Integer stuTableId);
	
}
