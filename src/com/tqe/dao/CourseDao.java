package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tqe.po.Course;


@Repository
public interface CourseDao {
	@Select("select * from `course` where cid=#{cid} and cno=#{cno}")
	public Course getById(@Param("cid") String  cid,@Param("cno") int cno);
	
	@Insert("INSERT INTO `tqe`.`course` (`cid`, `cno`, `name`, `stuNumber`, `peroid`, `credit`, `attr`, `examMode`, `nature`, `teacherId`, `department`, `campus`,`season`,`combine`) VALUES (#{cid}, #{cno}, #{name}, #{stuNumber}, #{peroid}, #{credit}, #{attr}, #{examMode}, #{nature}, #{teacherId}, #{department}, #{campus},#{season},#{combine});")
	public void save(Course	course);
	
	@Select("select c.*,t.name as `teacher.name` ,t.id as `teacher.id` from course c ,teacher t where  c.teacherId = t.id")
	public List<Course> findAll();
	
	@Select("select c.*,t.name as `teacher.name` ,t.id as `teacher.id` from course c ,sc,teacher t  where c.cid = sc.cid and c.cno = sc.cno and  t.id = c.teacherId and sc.sid = #{sid}")
	public List<Course> findAllBySid(@Param("sid")Integer sid);
	
	/**
	 * 选出一个教师所教课程的所有任课组的课程
	 * @param tid
	 * @return
	 */
	@Select("select c.*,t.name as  `teacher.name` , t.id as `teacher.id` from course c ,teacher t where c.name in("
			+"select name from course where teacherid = #{tid}"
			+") and c.teacherId =t.id and t.id!= #{tid}")
	public List<Course> findAllByTid(String tid);
}
