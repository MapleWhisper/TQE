package com.tqe.dao;

import java.util.HashMap;
import java.util.List;

import com.tqe.base.vo.PageVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Student;
import com.tqe.po.User;

@Repository
public interface StudentDao extends BaseDao<Student>{
	@Select("select * from student where sid = #{sid}")
	public Student getById(String sid);
	
	@Select("select sid,name from student where sid = #{sid}")
	public Student getNameById(String sid);
	
	@Insert("INSERT INTO `tqe`.`student` (`sid`, `name`, `password`,`sex`, `brithday`, `idNumber`, `nation`, `politicalStatus`, `language`, `department`, `major`, `clazz`, `field`, `educationBackground`, `grade`, `hasRoll`, `atSchool`, `style`,`campus`,`departmentid`,`majorid`,`classid`) "
			+ "VALUES (#{sid}, #{name},#{password}, #{sex}, #{brithday}, #{idNumber}, #{nation}, #{politicalStatus}, #{language}, #{department}, #{major}, #{clazz}, #{field}, #{educationBackground}, #{grade}, #{hasRoll}, #{atSchool}, #{style},#{campus},#{departmentId},#{majorId},#{classId});")
	public void save(Student	Student);
	
	@Select("select * from student")
	public List<Student> findAll();
	
	@Select("select * from student where sid = #{username} and password = #{password}")
	public Student login(User user);
	
	@Update("update student set password = #{password} where sid = #{id}")
	public void updatePwd(User user);
	
	//@Select("select * from student where departmentid = #{did} and majorid = #{mid} and classid = #{cid}")
	@SelectProvider(type=BaseDaoTemplate.class,method="findStudentByCondition")
	public List<Student> findByPageVO(PageVO pageVO);
	
	@Select("select count(*) from sc where sc.sid = #{sid} and sc.cid = #{cid} and sc.cno = #{cno} limit 1")
	public Integer isCoursePermitted(@Param("sid")String sid, @Param("cid")String cid, @Param("cno")Integer cno);

	@Select("select s.sid,s.name,s.sex from student s , sc where sc.cid = #{cid} and sc.cno =#{cno} and s.sid = sc.sid order by s.sid")
	public List<Student> findAllByCId(@Param("cid")String cid, @Param("cno")Integer cno);

	
}
