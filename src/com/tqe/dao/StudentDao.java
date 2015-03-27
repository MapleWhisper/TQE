package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Student;
import com.tqe.po.User;

@Repository
public interface StudentDao extends BaseDao<Student>{
	@Select("select * from student where sid = #{sid}")
	public Student getById(String sid);
	
	@Insert("INSERT INTO `tqe`.`student` (`sid`, `name`, `password`,`sex`, `brithday`, `idNumber`, `nation`, `politicalStatus`, `language`, `department`, `major`, `clazz`, `field`, `educationBackground`, `grade`, `hasRoll`, `atSchool`, `style`,campus) "
			+ "VALUES (#{sid}, #{name},#{password}, #{sex}, #{brithday}, #{idNumber}, #{nation}, #{politicalStatus}, #{language}, #{department}, #{major}, #{clazz}, #{field}, #{educationBackground}, #{grade}, #{hasRoll}, #{atSchool}, #{style},#{campus});")
	public void save(Student	Student);
	
	@Select("select * from student")
	public List<Student> findAll();
	
	@Select("select * from student where sid = #{username} and password = #{password}")
	public Student login(User user);
	
	@Update("update student set password = #{password} where sid = #{id}")
	public void updatePwd(User user);
}
