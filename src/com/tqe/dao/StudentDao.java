package com.tqe.dao;

import com.tqe.base.vo.PageVO;
import com.tqe.po.Student;
import com.tqe.po.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends BaseDao<Student>{
	@Select("select * from student where sid = #{sid}")
	Student getById(String sid);
	
	@Select("select sid,name from student where sid = #{sid}")
	Student getNameById(String sid);
	
	@Insert("INSERT INTO `tqe`.`student` (`sid`, `name`, `password`,`sex`, `birthday`, `idNumber`, `nation`, `politicalStatus`, `language`, `department`, `major`, `clazz`, `field`, `educationBackground`, `grade`, `hasRoll`, `atSchool`, `style`,`campus`,`departmentid`,`majorid`,`classid` , `mtime`) "
			+ "VALUES (#{sid}, #{name},#{password}, #{sex}, #{birthday}, #{idNumber}, #{nation}, #{politicalStatus}, #{language}, #{department}, #{major}, #{clazz}, #{field}, #{educationBackground}, #{grade}, #{hasRoll}, #{atSchool}, #{style},#{campus},#{departmentId},#{majorId},#{classId} , now()  );")
	void save(Student student);
	
	@Select("select * from student")
	List<Student> findAll();
	
	@Select("select * from student where sid = #{username} and password = #{password}")
	Student login(User user);
	
	@Update("update student set password = #{password} where sid = #{id}")
	void updatePwd(User user);
	
	//@Select("select * from student where departmentid = #{did} and majorid = #{mid} and classid = #{cid}")
	@SelectProvider(type=BaseDaoTemplate.class,method="findStudentByCondition")
    List<Student> findByPageVO(PageVO pageVO);
	
	@Select("select count(*) from sc where sc.sid = #{sid} and sc.cid = #{cid} and sc.cno = #{cno} limit 1")
	Integer isCoursePermitted(@Param("sid")String sid, @Param("cid")String cid, @Param("cno")Integer cno);

	@Select("select s.sid,s.name,s.sex from student s , sc where sc.cid = #{cid} and sc.cno =#{cno} and s.sid = sc.sid order by s.sid")
	List<Student> findAllByCId(@Param("cid")String cid, @Param("cno")Integer cno);

    @Update("update student s set s.avgScore = ( select avg(score) from teaStuTable t where t.sid = s.sid and t.score > 0 ) , s.mtime = now() where sid = #{sid} ")
    void updateStuAvgScore(String sid);
}
