package com.tqe.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.tqe.base.vo.PageVO;
import com.tqe.dao.SqlProvider.BatchesDaoSqlProvider;
import com.tqe.po.Batches;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.tqe.po.Course;


@Repository
public interface CourseDao {

	@Select("select mtime,cid,cno,name,stuNumber,peroid,credit,attr,examMode,nature,teacherId,department,campus,season,combine,departmentId from course where cid=#{cid} and cno=#{cno}")
	Course getById(@Param("cid") String  cid,@Param("cno") int cno);

    @Select("select * from course where cid=#{cid} and cno=#{cno}")
    Course getAllById(@Param("cid") String  cid,@Param("cno") int cno);

	@Insert("INSERT INTO `tqe`.`course` (`cid`, `cno`, `name`, `stuNumber`, `peroid`, `credit`, `attr`, `examMode`, `nature`, `teacherId`, `department`, `campus`,`season`,`combine`,`departmentid` , `mtime`) "
			+ "VALUES (#{cid}, #{cno}, #{name}, #{stuNumber}, #{peroid}, #{credit}, #{attr}, #{examMode}, #{nature}, #{teacherId}, #{department}, #{campus},#{season},#{combine},#{departmentId} ,now() );")
	void save(Course	course);

	@Select("select c.*,t.name as `teacher.name` ,t.id as `teacher.id` from course c ,teacher t where  c.teacherId = t.id")
	List<Course> findAll();

	@Select("select c.*,t.name as `teacher.name` ,t.id as `teacher.id` from course c ,sc,teacher t  where c.cid = sc.cid and c.cno = sc.cno and  t.id = c.teacherId and sc.sid = #{sid} and c.season = #{season}")
	List<Course> findAllBySid(@Param("sid") String sid, @Param("season")String season);

	/**
	 * 根据教师Id 选择教师所教课程的所有课程组
	 */
	@Select("select c.*,t.name as  `teacher.name` , t.id as `teacher.id` from course c ,teacher t where c.name in("
			+"select name from course where teacherid = #{tid}"
			+") and c.teacherId =t.id and t.id!= #{tid} and c.season = #{season}")
    List<Course> findCourseGroupByTid(@Param("tid") String tid, @Param("season") String season);



	@Select("select c.*,t.name as  `teacher.name` , t.id as `teacher.id`  from course c ,teacher t where c.teacherId = t.id and t.id = #{tid}")
	List<Course> findAllByTid(String tid);

    @Select("select c.*,t.name as  `teacher.name` , t.id as `teacher.id`  from course c ,teacher t where c.teacherId = t.id and t.id = #{tid} and c.season = #{season}")
    List<Course> findAllByTidSeason(@Param("tid")String tid, @Param("season") String season);

	@SelectProvider(type=BaseDaoTemplate.class,method="findCourseByCondition")
	List<Course> findByCondition(PageVO pageVO);



    @Update("update course set `stuEvalAvgScore` = #{stuEvalAvgScore} ,`teaEvalAvgScore`=#{teaEvalAvgScore} ,`leaEvalAvgScore`=#{leaEvalAvgScore} " +
            ",`stuEvalScores`=#{stuEvalScores} ,`teaEvalScores`=#{teaEvalScores} ,`leaEvalScores` = #{leaEvalScores}," +
            "`stuEvalLevelCnts`=#{stuEvalLevelCnts},`teaEvalLevelCnts`=#{teaEvalLevelCnts},`leaEvalLevelCnts`=#{leaEvalLevelCnts} , `mtime` = now() where cid = #{cid} and cno = #{cno}")
    void updateStatisticalData(Course course);


}
