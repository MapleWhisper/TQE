package com.tqe.dao;

import java.util.List;

import com.tqe.base.vo.PageVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Leader;
import com.tqe.po.User;

@Repository
public interface LeaderDao extends BaseDao<Leader>{
	@Select("select * from leader where id = #{id}")
	public Leader getById(@Param("id")Integer id);
	
	/*
	@Insert(" insert into admin values(null,#{username},#{password},#{name},#{position}) ")
	public void save(Admin	admin);
	*/
	
	@Select("select * from leader")
	public List<Leader> findAll(PageVO type);

	@Select("select * from leader where username = #{username} and password = #{password}")
	public Leader login(User user);
	
	@Update("update leader set password = #{password} where id = #{id}")
	public int updatePwd(User user);
	
	@Delete("delete from leader where id = #{id}")
	public void delete(Integer id);
	
}
