package com.tqe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.tqe.po.Department;

@Repository
public interface DepartmentDao extends BaseDao<Department>{
	@Override
	@Select("select * from department")
	public List<Department> findAll();

	@Override
	@Insert("insert into department values(null,#{name})")
	void save(Department department);

	/**
	 * �ҵ�����רҵ��ѧԺ�б�
	 */
	@Select("select * from department d where d.id in (select  departmentid from major)")
	List<Department> findDepListContainMajors();

	@Select("select * from department d where exists (select  departmentid from course c where c.departmentid = d.id)")
	List<Department> findDepListContainCourse();
}
