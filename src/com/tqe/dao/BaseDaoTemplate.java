package com.tqe.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;
import static org.apache.ibatis.jdbc.SqlBuilder.AND;
import static org.apache.ibatis.jdbc.SqlBuilder.OR;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;






import java.util.Map;

import org.aspectj.weaver.ast.And;
import org.springframework.util.StringUtils;


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
		if(StringUtils.hasText(sname)){
			WHERE("name = #{sname}");
		}
		if(StringUtils.hasText(sid)){
			WHERE("sid = #{sid}");
		}
		return  SQL();
		
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
		if(StringUtils.hasText(tname)){
			WHERE("name = #{tname}");
		}
		if(StringUtils.hasText(tid)){
			WHERE("id = #{tid}");
		}
		return  SQL();
		
	}
	
	public String findCourseByCondition(Map<String, String> parameters){
		@SuppressWarnings("unused")
		String did = parameters.get("did");
		String cname = parameters.get("cname");
		String cid = parameters.get("cid");
		String tname = parameters.get("tname");
		
		BEGIN();
		SELECT("c.* , t.name as `teacher.name`");
		FROM("course c");
		FROM("teacher t");
		WHERE("c.departmentid = #{did}");
		WHERE("c.teacherid = t.id");
		if(StringUtils.hasText(cname)){
			cname = "'%"+cname+"%'";
			WHERE("c.name like "+cname);
			
		}
		if(StringUtils.hasText(cid)){
			WHERE("c.cid = #{cid}");
		}
		if(StringUtils.hasText(tname)){
			WHERE("t.name = #{tname}");
		}
		return  SQL();
		
	}
	
	
}
