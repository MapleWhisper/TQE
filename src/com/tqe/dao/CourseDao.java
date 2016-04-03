package com.tqe.dao;

import java.util.HashMap;
import java.util.List;

import com.tqe.base.vo.PageVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.tqe.po.Course;


@Repository
public interface CourseDao {

	@Select("select cid,cno,name,stuNumber,peroid,credit,attr,examMode,nature,teacherId,department,campus,season,combine,departmentId from course where cid=#{cid} and cno=#{cno}")
	Course getById(@Param("cid") String  cid,@Param("cno") int cno);

    @Select("select * from course where cid=#{cid} and cno=#{cno}")
    Course getAllById(@Param("cid") String  cid,@Param("cno") int cno);

	@Insert("INSERT INTO `tqe`.`course` (`cid`, `cno`, `name`, `stuNumber`, `peroid`, `credit`, `attr`, `examMode`, `nature`, `teacherId`, `department`, `campus`,`season`,`combine`,`departmentid`) "
			+ "VALUES (#{cid}, #{cno}, #{name}, #{stuNumber}, #{peroid}, #{credit}, #{attr}, #{examMode}, #{nature}, #{teacherId}, #{department}, #{campus},#{season},#{combine},#{departmentId});")
	public void save(Course	course);

	@Select("select c.*,t.name as `teacher.name` ,t.id as `teacher.id` from course c ,teacher t where  c.teacherId = t.id")
	public List<Course> findAll();

	@Select("select c.*,t.name as `teacher.name` ,t.id as `teacher.id` from course c ,sc,teacher t  where c.cid = sc.cid and c.cno = sc.cno and  t.id = c.teacherId and sc.sid = #{sid} and c.season = #{season}")
	public List<Course> findAllBySid(@Param("sid") String sid, @Param("season")String season);

	/**
	 * 根据教师Id 选择教师所教的所有课程
	 * @param tid
	 * @return
	 */
	@Select("select c.*,t.name as  `teacher.name` , t.id as `teacher.id` from course c ,teacher t where c.name in("
			+"select name from course where teacherid = #{tid}"
			+") and c.teacherId =t.id and t.id!= #{tid}")
	public List<Course> findAllByTId(String tid);



	@Select("select c.*,t.name as  `teacher.name` , t.id as `teacher.id`  from course c ,teacher t where c.teacherId = t.id and t.id = #{tid}")
	public List<Course> findAllByTid(String tid);

	@SelectProvider(type=BaseDaoTemplate.class,method="findCourseByCondition")
	List<Course> findByCondition(PageVO pageVO);


    @Update("update course set `stuEvalAvgScore` = #{stuEvalAvgScore} ,`teaEvalAvgScore`=#{teaEvalAvgScore} ,`leaEvalAvgScore`=#{leaEvalAvgScore} " +
            ",`stuEvalScores`=#{stuEvalScores} ,`teaEvalScores`=#{teaEvalScores} ,`leaEvalScores` = #{leaEvalScores}," +
            "`stuEvalLevelCnts`=#{stuEvalLevelCnts},`teaEvalLevelCnts`=#{teaEvalLevelCnts},`leaEvalLevelCnts`=#{leaEvalLevelCnts} where cid = #{cid} and cno = #{cno}")
    void updateStatisticalData(Course course);

}
