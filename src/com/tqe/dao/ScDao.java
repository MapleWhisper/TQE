package com.tqe.dao;

import com.tqe.base.vo.PageVO;
import com.tqe.po.SC;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScDao extends BaseDao<SC>{
	@Override
	@Select("select * from sc limit 1000")
	List<SC> findAll(PageVO type);

	@Override
	@Insert("insert into sc values( #{cid}, #{cno},#{sid})")
	void save(SC sc);

    @Select("select count(*) from sc where cid = #{cid} and cno = #{cno}")
    int totalStuNumByCidAndCno(@Param("cid")String cid,@Param("cno")Integer cno);
}
