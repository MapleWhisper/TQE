package com.tqe.dao;

import com.tqe.po.Department;
import com.tqe.po.SC;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScDao extends BaseDao<SC>{
	@Override
	@Select("select * from sc limit 1000")
	List<SC> findAll();

	@Override
	@Insert("insert into sc values( #{cid}, #{cno},#{sid})")
	void save(SC sc);

}
