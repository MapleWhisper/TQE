package com.tqe.dao.SqlProvider;

import com.tqe.dao.BaseDaoTemplate;
import com.tqe.po.Batches;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

/**
 * Created by Maple on 2016/4/19.
 */
public class BatchesDaoSqlProvider extends BaseDaoTemplate{

    //select * from batches b where #{date} > b.beginDate and #{date} < b.endDate and id != #{id};
    public String checkDateConflict(Map<String, Object> params){
        Integer id = (Integer) params.get("id");
        BEGIN();
        SELECT("*");
        FROM("batches b");
        WHERE("#{date} > b.beginDate");
        WHERE("#{date} < b.endDate");
        if(id!=null && id >0){
            WHERE("b.id != #{id}");
        }
        return  SQL()+LIMIT(1);
    }


    public String checkDateRangeConflict(Map<String, Object> params){
        Integer id = (Integer) params.get("id");
        BEGIN();
        SELECT("*");
        FROM("batches b");
        WHERE("#{beginDate} <= b.beginDate");
        WHERE("#{endDate} >= b.endDate");
        if(id!=null && id >0){
            WHERE("b.id != #{id}");
        }
        return  SQL()+LIMIT(1);
    }


}
