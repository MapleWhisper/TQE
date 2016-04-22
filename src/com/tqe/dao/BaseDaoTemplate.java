package com.tqe.dao;

import com.tqe.base.vo.PageVO;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class BaseDaoTemplate<T> {

    private final static  int DEFAULT_LIMIT = 300;

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
        if(StringUtils.isNoneBlank(filters.get("grade"))){
            WHERE("grade = #{filters.grade}");
        }
		return  SQL()+ LIMIT(DEFAULT_LIMIT);
		
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
		return  SQL() + LIMIT(DEFAULT_LIMIT);
		
	}
	
	public String findCourseByCondition(PageVO pageVO){

		Map<String,String> filters = pageVO.getFilters();
		BEGIN();
		SELECT("c.cid,c.cno,c.name,c.stuNumber,c.peroid,c.credit,c.attr,c.examMode,c.nature,c.teacherId,c.department," +
                "c.campus,c.season,c.combine,c.departmentId, c.stuEvalAvgScore,c.teaEvalAvgScore,c.leaEvalAvgScore, t.name as `teacher.name`");
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
        if(StringUtils.isNotBlank(filters.get("tid"))){
            WHERE("t.id = #{filters.tid}");
        }
        if(StringUtils.isNotBlank(filters.get("season"))){
            WHERE("c.season = #{filters.season}");
        }

		return  SQL()+LIMIT(DEFAULT_LIMIT);
		
	}

   public String findBatchAll(PageVO pageVO){
        Map<String,String> filters = pageVO.getFilters();
        BEGIN();
        SELECT("*");
        FROM("batches b");
        if(StringUtils.isNotBlank(filters.get("season"))){
             WHERE("b.season = #{filters.season}");
        }
        return  SQL() + LIMIT(DEFAULT_LIMIT);

    }

    public String findEvalTableAll(PageVO pageVO){
        Map<String,String> filters = pageVO.getFilters();
        BEGIN();
        SELECT("*");
        FROM("evalTable e");
        if(StringUtils.isNotBlank(filters.get("type"))){
            WHERE("e.type = #{filters.type}");
        }
        return  SQL() + LIMIT(DEFAULT_LIMIT);

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
