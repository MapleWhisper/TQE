package com.tqe.dao;

import java.util.List;

import com.tqe.base.vo.PageVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.tqe.po.EvalTable;

@Repository
public interface EvalTableDao extends BaseDao<EvalTable>{

	@Select("select * from evalTable where id = #{id}")
	public EvalTable getById(@Param("id")Integer id);
	
	@Insert("insert into evalTable values(null,#{type},#{title},#{note},#{createDate},#{jsonString}) ")
	public void save(EvalTable	EvalTable);
	
    @SelectProvider(type = BaseDaoTemplate.class , method = "findEvalTableAll")
	public List<EvalTable> findAll(PageVO pageVO);
	
	@Delete("delete from evalTable where id = #{id}")
	void delete(int id);


    @Update("update evaltable set title = #{title} , note = #{note} ,jsonString = #{jsonString}  where id = #{id}")
	public void update(EvalTable eTable);



	
}
