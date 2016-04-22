package com.tqe.dao.SqlProvider;

import com.tqe.base.vo.PageVO;
import com.tqe.dao.BaseDaoTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

/**
 * Created by Maple on 2016/4/20.
 */
public class EvalDaoSqlProvider extends BaseDaoTemplate{

    //("select `id`,  `tid`,`sid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level`,`tname` from teaStutable where sid =#{sid}  and bid =#{bid} ")
    public String findTeaStuResultTable(PageVO pageVO){
        Map<String,String> filters = pageVO.getFilters();
        BEGIN();
        String selectString = "`id`,  `tid`,`sid`,  `eid`,  `cid`,  `cno`,  `bid`,  `score`,  `level`,`tname`";
        if(StringUtils.isNotBlank(filters.get("withJsonString"))){
            selectString += ", `jsonString` ";
        }
        SELECT(selectString);
        FROM("teaStutable t");
        if(StringUtils.isNotBlank(filters.get("sid"))){
            WHERE("t.sid = #{filters.sid}");
        }
        if(StringUtils.isNotBlank(filters.get("bid"))){
            WHERE("t.bid = #{filters.bid}");
        }
        if(StringUtils.isNotBlank(filters.get("season"))){
            WHERE("t.season = #{filters.season}");
        }
        return  SQL()+LIMIT(300);
    }
}
