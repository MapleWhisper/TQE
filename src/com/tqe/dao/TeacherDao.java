package com.tqe.dao;

import java.util.HashMap;
import java.util.List;

import com.tqe.base.vo.PageVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Teacher;
import com.tqe.po.User;

@Repository
public interface TeacherDao extends BaseDao<Teacher>{
	@Select("select * from teacher where id = #{id}")
	public Teacher getById(@Param("id")String id);
	
	@Insert("INSERT INTO `tqe`.`teacher` (`id`, `name`,`password`, `sex`, `brithday`, `phone`, `email`, `addr`, `postId`, `nation`, `idType`, `idNumber`, `politicalStatus`, `department`, `title`, `folk`, `workDate`,`departmentid`) "
			+ "	VALUES (#{id}, #{name},#{password}, #{sex}, #{brithday}, #{phone}, #{email}, #{addr}, #{postId}, #{nation}, #{idType}, #{idNumber}, #{politicalStatus}, #{department}, #{title}, #{folk}, #{workDate}, #{departmentId})")
	public void save(Teacher teacher);
	
	@Select("select * from teacher")
	public List<Teacher> findAll();
	
	@Select("select * from teacher where id = #{username} and password = #{password}")
	public Teacher login(User user);
	
	@Update("update teacher set password = #{password} where id = #{id}")
	public int updatePwd(User user);
	
	@Select("select * from teacher limit  #{start},#{length}")
	public List<Teacher> findByPage(@Param("start")int start, @Param("length")int length);
	
	@SelectProvider(type=BaseDaoTemplate.class,method="findTeacherByCondition")
	public List<Teacher> findByPageVO(PageVO pageVO);
}
