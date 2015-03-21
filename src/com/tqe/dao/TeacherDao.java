package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Teacher;
import com.tqe.po.User;

@Repository
public interface TeacherDao extends BaseDao<Teacher>{
	@Select("select * from teacher where id = #{id}")
	public Teacher getById(@Param("id")int id);
	
	@Insert("INSERT INTO `tqe`.`teacher` (`id`, `name`,`password`, `sex`, `brithday`, `phone`, `email`, `addr`, `postId`, `nation`, `idType`, `idNumber`, `politicalStatus`, `department`, `title`, `folk`, `workDate`) VALUES (#{id}, #{name},#{password}, #{sex}, #{brithday}, #{phone}, #{email}, #{addr}, #{postId}, #{nation}, #{idType}, #{idNumber}, #{politicalStatus}, #{department}, #{title}, #{folk}, #{workDate})")
	public void save(Teacher teacher);
	
	@Select("select * from teacher")
	public List<Teacher> findAll();
	
	@Select("select * from teacher where id = #{username} and password = #{password}")
	public Teacher login(User user);
	
	@Update("update teacher set password = #{password} where id = #{id}")
	public int updatePwd(User user);
	
	@Select("select * from teacher limit  #{start},#{length}")
	public List<Teacher> findByPage(@Param("start")int start, @Param("length")int length);
}
