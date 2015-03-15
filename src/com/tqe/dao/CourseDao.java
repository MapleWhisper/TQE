package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.tqe.po.Course;

public interface CourseDao extends BaseDao<Course>{
	@Select("select * from course where cid = #{cid} and cno = #{cno}")
	public Course getById(@Param("id")int cid,int cno);
	
	@Insert("INSERT INTO `tqe`.`course` (`cid`, `cno`, `name`, `stuNumber`, `peroid`, `credit`, `attr`, `examMode`, `nature`, `teacherId`, `department`, `campus`,`season`,`combine`) VALUES (#{cid}, #{cno}, #{name}, #{stuNumber}, #{peroid}, #{credit}, #{attr}, #{examMode}, #{nature}, #{teacherId}, #{department}, #{campus},#{season},#{combine});")
	public void save(Course	Course);
	
	@Select("select * from course")
	public List<Course> findAll();
}
