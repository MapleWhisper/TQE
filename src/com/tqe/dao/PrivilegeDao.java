
package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.tqe.po.Privilege;

@Repository
public interface PrivilegeDao extends BaseDao<Privilege>{
	
	@Select("select * from privilege order by url")
	public List<Privilege> findAll();
	
	@Select("select * from privilege p  where p.adm = 1")
	public List<Privilege> findAdminAll();
	
	@Select("select * from privilege p where p.stu = 1")
	public List<Privilege> findStudentAll();
	
	@Select("select * from privilege p where p.tea = 1")
	public List<Privilege> findTeacherAll();
	
	@Select("select * from privilege p where p.lea = 1")
	public List<Privilege> findLeaderAll();
	
	@Select("select * from privilege where id = #{id}")
	public Privilege getById(Integer id);
	
	@Update("update privilege set stu = #{stu} , tea = #{tea} , adm = #{adm} where id = #{id}")
	public void update(Privilege p);

	
}
