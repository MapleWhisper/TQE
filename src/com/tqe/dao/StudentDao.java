package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.tqe.po.Student;

public interface StudentDao extends BaseDao<Student>{
	@Select("select * from student where sid = #{sid}")
	public Student getById(int sid);
	
	@Insert("INSERT INTO `tqe`.`student` (`sid`, `name`, `password`,`sex`, `brithday`, `idNumber`, `nation`, `politicalStatus`, `language`, `department`, `major`, `clazz`, `field`, `educationBackground`, `grade`, `hasRoll`, `atSchool`, `style`,campus) "
			+ "VALUES (#{sid}, #{name},#{password}, #{sex}, #{brithday}, #{idNumber}, #{nation}, #{politicalStatus}, #{language}, #{department}, #{major}, #{clazz}, #{field}, #{educationBackground}, #{grade}, #{hasRoll}, #{atSchool}, #{style},#{campus});")
	public void save(Student	Student);
	
	@Select("select * from student")
	public List<Student> findAll();
}
