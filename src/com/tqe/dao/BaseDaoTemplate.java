package com.tqe.dao;

import com.tqe.base.vo.PageVO;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class BaseDaoTemplate<T> {

	public void delete(T obj){
	}
	
	public void findAll(T obj){
		
	}
	
	public String findStudentByCondition(PageVO pageVO){
		Map<String,String> filters = pageVO.getFilters();
		BEGIN();
		SELECT("*");
		FROM("student");
		if(StringUtils.isNotBlank(filters.get("did"))){
			WHERE("departmentid = #{filters.did}");
		}
		if(StringUtils.isNoneBlank(filters.get("cid"))){
			WHERE("classid = #{filters.cid}");
		}
		if(StringUtils.isNotBlank(filters.get("mid"))){
			WHERE("majorid = #{filters.mid}");
		}
		if(StringUtils.isNotBlank(filters.get("sname"))){
			WHERE("name = #{filters.sname}");
		}
		if(StringUtils.isNotBlank(filters.get("sid"))){
			WHERE("sid = #{filters.sid}");
		}
		return  SQL()+ LIMIT(300);
		
	}
	
	public String findTeacherByCondition(PageVO pageVO){
		Map<String,String> filters = pageVO.getFilters();
		BEGIN();
		SELECT("*");
		FROM("teacher");
		if(StringUtils.isNotBlank(filters.get("did"))){
			WHERE("departmentid = #{filters.did}");
		}
		if(StringUtils.isNotBlank(filters.get("tname"))){
			WHERE("name = #{filters.tname}");
		}
		if(StringUtils.isNotBlank(filters.get("tid"))){
			WHERE("id = #{filters.tid}");
		}
		return  SQL() + LIMIT(300);
		
	}
	
	public String findCourseByCondition(PageVO pageVO){

		Map<String,String> filters = pageVO.getFilters();
		BEGIN();
		SELECT("c.* , t.name as `teacher.name`");
		FROM("course c");
		FROM("teacher t");
		if(StringUtils.isNotBlank(filters.get("did"))){
			WHERE("c.departmentid = #{filters.did}");
		}

		WHERE("c.teacherid = t.id");
		if(StringUtils.isNotBlank(filters.get("cname"))){
			WHERE("c.name like "+convertContains(filters.get("cname")));
		}
		if(StringUtils.isNotBlank(filters.get("cid"))){
			WHERE("c.cid = #{filters.cid}");
		}
		if(StringUtils.isNotBlank(filters.get("tname"))){
			WHERE("t.name = #{filters.tname}");
		}

		return  SQL()+LIMIT(300);
		
	}

	public static String convertContains(String s){

		return " '%"+s+"%' ";
	}

	public static String convertStartWith(String s ){
		return " '%"+s+"' ";

	}

	public static String LIMIT(Integer take){
		return " limit "+ take;
	}

	public static String LIMIT(Integer offset,Integer take){
		return " limit "+ offset +" , "+take ;
	}
	
	
}
