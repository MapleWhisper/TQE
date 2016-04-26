package com.tqe.service;

import com.alibaba.fastjson.JSON;
import com.tqe.base.enums.UserType;
import com.tqe.po.*;
import com.tqe.utils.ResultTableUtils;
import com.tqe.utils.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseBatchServiceImpl extends BaseService<CourseBatch>{

    @Autowired
    private EvalServiceImpl evalService;

    Log logger = LogFactory.getLog(CourseBatchServiceImpl.class);


    public CourseBatch getById(String cid,Integer cno ,Integer bid) {
        return courseBatchDao.getById(cid,cno,bid);
    }

    /**
     * 显示每个问题对应 所有学生的回答
     */
    public CourseBatch getByIdWithQuestList(String cid,Integer cno ,Integer bid) {
        CourseBatch courseBatch = courseBatchDao.getById(cid,cno,bid);
        List<StuResultTable> stuTableList = evalService.findAllStuTableWithEvalTable(cid, cno, bid);
        List<TeaResultTable> teaTableList = evalService.findAllTeaTableByCidAndBid(cid, cno, bid);
        List<LeaResultTable> leaTableList = evalService.findAllLeaTableWithEvalTable(cid, cno, bid);
        if(stuTableList.size()>0){
            EvalTable stuTable = EvalTable.json2Object(stuTableList.get(0).getJsonString());
            courseBatch.setStuQuestionList(ResultTableUtils.buildQuestionWithAnsPairList(stuTable.getQuestionNameList(), new ArrayList<ResultTable>(stuTableList)));
        }
        if(teaTableList.size()>0){
            EvalTable teaTable = EvalTable.json2Object(teaTableList.get(0).getJsonString());
            courseBatch.setTeaQuestionList(ResultTableUtils.buildQuestionWithAnsPairList(teaTable.getQuestionNameList(),new ArrayList<ResultTable>(teaTableList)));
        }
        if(leaTableList.size()>0){
            EvalTable leaTable = EvalTable.json2Object(leaTableList.get(0).getJsonString());
            courseBatch.setLeaQuestionList(ResultTableUtils.buildQuestionWithAnsPairList(leaTable.getQuestionNameList(), new ArrayList<ResultTable>(leaTableList)));
        }
        return courseBatch;
    }



    /**
     * 重新分析 课程评教的统计信息
     */
	public void reAnalyseCourseBatch(String cid,Integer cno ,Integer bid){
        if(StringUtils.isBlank(cid) || cno < 0 || bid <0){
            throw new IllegalArgumentException("cid cno bid 不能为空! cid :"+cid+" cno:"+cno+" bid:"+bid);
        }
        int stuEvalTotal = scDao.totalStuNumByCidAndCno(cid,cno);
        int stuEvalCnt = evalDao.cntStuEvalByCidCnoBid(cid, cno, bid);
        CourseBatch courseBatch = courseBatchDao.getById(cid,cno,bid);
        if(courseBatch!=null){
            Course course = courseDao.getById(cid,cno);
            Batches batch = batchesDao.getById(bid);
            if(course==null || batch==null) return;
            if(!course.getSeason().equals(SystemUtils.getSeason()) && courseBatch.getMtime().getTime() > batch.getEndDate().getTime()){
                //如果都不是当前学期了 并且课程最后一次修改时间是在课程批次结束之后 该courseBatch就再也不需要更新了
                return ;
            }else{
                //一天只能更新一次！
                if(DateUtils.isSameDay(courseBatch.getMtime(),new Date())){
                    return ;
                }
            }
        }else {  //数据库不存在改记录 那么分析后创建记录
            courseBatch = new CourseBatch(cid,cno,bid);
        }

        doAnalyseCourseBatch(courseBatch);
        courseBatch.setStuEvalTotal(stuEvalTotal);
        courseBatch.setStuEvalCnt(stuEvalCnt);

        saveOrUpdate(courseBatch);
    }

    /**
     * 计算平均得分
     */
    private void doAnalyseCourseBatch(CourseBatch courseBatch){
        //取出所有的评教表
        List<StuResultTable> stuTableList = evalService.findAllStuTableWithEvalTable(courseBatch.getCid(), courseBatch.getCno(), courseBatch.getBid());
        List<TeaResultTable> teaTableList = evalService.findAllTeaTableWithEvalTable(courseBatch.getCid(), courseBatch.getCno(), courseBatch.getBid());
        List<LeaResultTable> leaTableList = evalService.findAllLeaTableWithEvalTable(courseBatch.getCid(), courseBatch.getCno(), courseBatch.getBid());

        // 学生评教 教师评教 领导评教分别处理
        List<ResultTable> resultTableList = new ArrayList<ResultTable>();
        resultTableList.addAll(stuTableList);
        setStatisticsData(resultTableList, courseBatch, UserType.STUDENT);

        resultTableList.clear();
        resultTableList.addAll(teaTableList);
        setStatisticsData(resultTableList, courseBatch, UserType.TEACHER);

        resultTableList.clear();
        resultTableList.addAll(leaTableList);
        setStatisticsData(resultTableList, courseBatch, UserType.LEADER);

    }


    /**
     *  设置统计数据
     */
    private  void setStatisticsData(List<ResultTable> resultTables , CourseBatch courseBatch , UserType userType){

        // 平均分
        double avgScore = ResultTableUtils.calcAvgScore(resultTables);

        //等级统计
        Integer[] levelCnt = ResultTableUtils.calcLevelCnt(resultTables);

        //表单项的 平均分和 等级统计
        EvalTable resultTable = ResultTableUtils.processTableItemScoreAndLevelCnt(resultTables);
        String tableItemListJsonString = JSON.toJSONString(resultTable);
        List<Integer> levelCnts = new ArrayList<Integer>( Arrays.asList(levelCnt));

        //将得到的统计数据注入到 courseBatch对象中
        switch (userType){
            case STUDENT:   //学生评价老师
                courseBatch.setStuEvalAvgScore(avgScore);
                courseBatch.setStuEvalLevelCnts(levelCnts);
                courseBatch.setStuEvalTableJsonString(tableItemListJsonString);
                break;
            case TEACHER:
                courseBatch.setTeaEvalAvgScore(avgScore);
                courseBatch.setTeaEvalLevelCnts(levelCnts);
                courseBatch.setTeaEvalTableJsonString(tableItemListJsonString);
                break;
            case LEADER:
                courseBatch.setLeaEvalAvgScore(avgScore);
                courseBatch.setLeaEvalLevelCnts(levelCnts);
                courseBatch.setLeaEvalTableJsonString(tableItemListJsonString);
                break;
            default:
                logger.error("unknown userType :"+userType+"  in courseBatch:"+courseBatch);
        }
    }




    public void saveOrUpdate(CourseBatch courseBatch){

        CourseBatch c = courseBatchDao.getById(courseBatch.getCid(),courseBatch.getCno(),courseBatch.getBid());

        if(c==null){
            courseBatchDao.save(courseBatch);
        }else{
            courseBatchDao.update(courseBatch);
        }
    }
}
