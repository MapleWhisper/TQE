package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Admin;
import com.tqe.po.User;

@Repository
public interface AdminDao extends BaseDao<Admin>{
	@Select("select * from admin where id = #{id}")
	public Admin getById(@Param("id")int id);
	
	@Insert(" insert into admin values(null,#{username},#{password},#{name},#{position}) ")
	public void save(Admin	admin);
	
	@Select("select * from admin")
	public List<Admin> findAll();

	@Select("select * from admin where username = #{username} and password = #{password}")
	public Admin login(User user);
	
	@Update("update admin set password = #{password} where id = #{id}")
	public int updatePwd(User user);
	
	@Delete("delete from admin where id = #{id}")
	public void delete(int id);
	
}
