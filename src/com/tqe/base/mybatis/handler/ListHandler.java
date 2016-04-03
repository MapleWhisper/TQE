package com.tqe.base.mybatis.handler;

import com.google.common.base.CaseFormat;
import com.tqe.po.Course;
import com.tqe.po.CourseBatch;
import com.tqe.po.EvalTable;
import com.tqe.po.ResultTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * 将数据库中 ,xx,yy,zz 的字符串结构转换成 List结构
 * 类说明：StringArrayHandler
 */
public class ListHandler extends BaseTypeHandler<List> {

    private static final Log logger = LogFactory.getLog(ListHandler.class);

    /**
     * String ==> List 白名单
     * 需要过滤的Class DO
     */
    private static  List<Class> targetClassFilterList;
    static {
        targetClassFilterList = new ArrayList<Class>();
        targetClassFilterList.add(CourseBatch.class);
        targetClassFilterList.add(EvalTable.class);
        targetClassFilterList.add(ResultTable.class);
        targetClassFilterList.add(Course.class);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
        if(parameter==null || parameter.isEmpty()){
            ps.setString(i,"");
        }else{
            ps.setString(i, StringUtils.join(parameter, ','));
        }
    }

    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (isTargetField(columnName)){
            String string = rs.getString(columnName);
            if (null==string){
                string= StringUtils.EMPTY;
            }
            String[] split = StringUtils.split(string, ',');
            if (split.length==0){
                return Collections.emptyList();
            }
            List result;
            boolean isNumeric = false;
            String replace = string.replace(",", "");
            if (StringUtils.isNumeric(replace)){
                isNumeric=true;
                result=new ArrayList<Integer>(split.length);
            }else {
                result=new ArrayList<String>(split.length);
            }
            for (String id : split) {
                if (isNumeric){
                    result.add(Integer.valueOf(id));
                }else {
                    result.add(id);
                }
            }
            return result;
        }else {
            logger.error("column value case to List fail , please make sure that you do realy need this ListHandler! columnName;"+columnName);
            throw new IllegalArgumentException("字段内容转换List失败! 原因:1.请检查该字段是否需要转换为List\n2.该字段是否在targetClassFilterList 白名单中\n columnName:"+columnName);
        }
    }

    @Override
    public List getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getResult(rs,columnIndex);
    }

    @Override
    public List getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getResult(cs,columnIndex);
    }


    private boolean isTargetField(String columnName){
        //columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
        Field field = null;
        boolean find = false;
        for(Class targetCls : targetClassFilterList){
            try {
                field = targetCls.getDeclaredField(columnName);
                if(field!=null){
                    find = true;
                    break;
                }
            } catch (NoSuchFieldException e) {
            }
        }
        return find;
    }
}
