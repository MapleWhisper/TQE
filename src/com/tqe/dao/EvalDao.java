package com.tqe.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
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
	
}
