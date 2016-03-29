package com.tqe.dao;

import com.tqe.po.Clazz;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDao extends BaseDao<Clazz>{

	@Select(value="select * from class")
	List<Clazz> findAll();


	@Select(value="select * from class where departmentid = #{did} and majorid =#{mid} and grade = #{grade} order by name")
	List<Clazz> findAllByDidMid(@Param("did") Integer did, @Param("mid") Integer mid, @Param("grade")String grade);

	@Insert(value = "insert into class values(null,#{grade},#{name},#{majorId},#{departmentId})")
	void save(Clazz clazz);
}
