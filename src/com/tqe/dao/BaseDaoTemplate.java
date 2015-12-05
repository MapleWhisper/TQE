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
	
	public String findStudentByCondition(Map<String, String> parameters){
		@SuppressWarnings("unused")
		String did = parameters.get("did");
		String mid = parameters.get("mid");
		String cid = parameters.get("cid");
		String sname = parameters.get("sname");
		String sid = parameters.get("sid");
		//System.out.println(did+"-"+mid+"-"+cid+"-"+sname+"-"+sid);
		BEGIN();
		SELECT("*");
		FROM("student");
		WHERE("departmentid = #{did}");
		
		if(!cid.equals("0")){
			WHERE("classid = #{cid}");
		}
		if(!mid.equals("0")){
			WHERE("majorid = #{mid}");
		}
		if(StringUtils.isNotBlank(sname)){
			WHERE("name = #{sname}");
		}
		if(StringUtils.isNotBlank(sid)){
			WHERE("sid = #{sid}");
		}
		return  SQL()+ LIMIT(300);
		
	}
	
	public String findTeacherByCondition(Map<String, String> parameters){
		@SuppressWarnings("unused")
		String did = parameters.get("did");
		String tname = parameters.get("tname");
		String tid = parameters.get("tid");
		BEGIN();
		SELECT("*");
		FROM("teacher");
		WHERE("departmentid = #{did}");
		if(StringUtils.isNotBlank(tname)){
			WHERE("name = #{tname}");
		}
		if(StringUtils.isNotBlank(tid)){
			WHERE("id = #{tid}");
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
