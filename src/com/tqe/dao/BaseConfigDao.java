package com.tqe.dao;

import com.tqe.dao.BaseDao;
import com.tqe.po.BaseConfig;
import com.tqe.po.Clazz;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseConfigDao extends BaseDao<BaseConfig>{

	@Select(value="select * from baseconfig")
	List<BaseConfig> findAll();

    @Select("select * from baseconfig bc where bc.key = #{key} and bc.enable = 1")
    BaseConfig getByKey(String key);

    @Select("select bc.value from baseconfig bc where bc.key = #{key} and bc.enable = 1")
    String getConfigValue(String key);
}
