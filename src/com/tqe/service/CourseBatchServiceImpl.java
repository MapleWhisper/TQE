package com.tqe.service;

import com.alibaba.fastjson.JSON;
import com.tqe.base.enums.EvalResultLevel;
import com.tqe.base.enums.UserType;
import com.tqe.po.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

            if(courseBatch.getStuEvalCnt()==stuEvalCnt && courseBatch.getStuEvalTotal() == stuEvalTotal){
                //数据数据库已经存在该记录 并且选课的总人数 和 评教的人数都没有发生变化 那么就不需要改变
                return ;
            }else{  //如果数据有变化 重新分析数据
                doAnalyseCourseBatch(courseBatch);
            }

        }else {  //数据库不存在改记录 那么分析数据后创建
            courseBatch = new CourseBatch(cid,cno,bid);
            doAnalyseCourseBatch(courseBatch);

        }

        courseBatch.setStuEvalTotal(stuEvalTotal);
        courseBatch.setStuEvalCnt(stuEvalCnt);

        saveOrUpdate(courseBatch);
    }

    /**
     * 计算平均得分
     */
    private void doAnalyseCourseBatch(CourseBatch courseBatch){
        //学生评教平均得分
        List<StuResultTable> stuTableList = evalService.findAllStuTableWithEvalTable(courseBatch.getCid(), courseBatch.getCno(), courseBatch.getBid());
        List<TeaResultTable> teaTableList = evalService.findAllTeaTableWithEvalTable(courseBatch.getCid(), courseBatch.getCno(), courseBatch.getBid());
        List<LeaResultTable> leaTableList = evalService.findAllLeaTableWithEvalTable(courseBatch.getCid(), courseBatch.getCno(), courseBatch.getBid());
        List<ResultTable> resultTableList = new ArrayList<ResultTable>();
        resultTableList.addAll(stuTableList);
        setAvgScoreAndLevelCnt(resultTableList, courseBatch, UserType.STUDENT);

        resultTableList.clear();
        resultTableList.addAll(teaTableList);
        setAvgScoreAndLevelCnt(resultTableList, courseBatch, UserType.TEACHER);

        resultTableList.clear();
        resultTableList.addAll(leaTableList);
        setAvgScoreAndLevelCnt(resultTableList, courseBatch, UserType.LEADER);

    }

    private  void setAvgScoreAndLevelCnt(List<ResultTable> resultTables , CourseBatch courseBatch ,UserType userType){


        int scoreSum  = 0;
        double avgScore = 0;
        int levelGoodCnt = 0;
        int levelBestCnt = 0;
        int levelAvgCnt = 0;
        int levelBadCnt = 0;
        int tableSize = resultTables.size();

        Map<String,List<Integer>> tableItemLevelMap = new HashMap<String,List<Integer>>();
        Map<String,Integer> tableItemScoreMap = new HashMap<String, Integer>();

        for(ResultTable table : resultTables){      //遍历评教表
            scoreSum += table.getScore();
            if(table.getLevel().equals(EvalResultLevel.BEST.getName())){
                levelBestCnt++;
            }
            if(table.getLevel().equals(EvalResultLevel.GOOD.getName())){
                levelGoodCnt++;
            }
            if(table.getLevel().equals(EvalResultLevel.AVERAGE.getName())){
                levelAvgCnt++;
            }
            if(table.getLevel().equals(EvalResultLevel.BAD.getName())){
                levelBadCnt++;
            }
            processTableItemCnt(table.getEvalTable(),tableItemLevelMap,tableItemScoreMap);
        }

        if(tableSize>0){
            avgScore = scoreSum/tableSize;
        }

        EvalTable table = postProcessTableItemCnt(resultTables, tableItemLevelMap, tableItemScoreMap, tableSize);
        String tableItemListJsonString = JSON.toJSONString(table);

        List<Integer> levelCnts = new ArrayList<Integer>(Arrays.asList(levelBestCnt,levelGoodCnt,levelAvgCnt,levelBadCnt));

        switch (userType){
            case STUDENT:   //学生评价老师
                courseBatch.setStuEvalAvgScore(avgScore);
                courseBatch.setStuEvalLevelCnts(levelCnts);
                courseBatch.setStuEvalTableJsonString(tableItemListJsonString);
                break;
            case TEACHER:
                courseBatch.setTeaEvalAvgScore(avgScore);
                courseBatch.setTeaEvalLevelCnts(levelCnts);
                break;
            case LEADER:
                courseBatch.setLeaEvalAvgScore(avgScore);
                courseBatch.setLeaEvalLevelCnts(levelCnts);
                break;
            default:
                logger.error("unknown userType :"+userType+"  in setAvgScoreAndLevelCnt:"+courseBatch);
        }
    }

    /**
     *  将评教表单项的 统计内容放到 表单里
     */
    private EvalTable postProcessTableItemCnt(List<ResultTable> resultTables, Map<String, List<Integer>> tableItemLevelMap, Map<String, Integer> tableItemScoreMap, int tableSize) {
        EvalTable table = new EvalTable();
        if (resultTables.size() > 0) {
            table = resultTables.get(0).getEvalTable();  //随意取出一个评教结果表 取出评教表

            for (EvalTable.EvalTableItem tableItem : table.getTableItemList()) {
                String key = tableItem.getContext();
                if (tableSize > 0) {
                    tableItem.setAvgScore(tableItemScoreMap.get(key) / tableSize);
                }
                tableItem.setScoreLevelCnts(tableItemLevelMap.get(key));
            }
        }
        return table;


    }

    /**
     *  统计评教表单项的等级统计和得分
     */
    private void processTableItemCnt(
            EvalTable evalTable , Map<String,List<Integer>> tableItemLevelMap,Map<String,Integer> tableItemScoreMap){
        List<EvalTable.EvalTableItem> tableItemList = evalTable.getTableItemList();
        for(EvalTable.EvalTableItem tableItem : tableItemList){

            String key = tableItem.getContext();
            if(tableItemLevelMap.get(key)==null){
                List<Integer> list = new ArrayList<Integer>(4);
                Collections.fill(list,0);
                tableItemLevelMap.put(key,list);
            }
            if(tableItemScoreMap.get(key)==null){
                tableItemScoreMap.put(key,0);
            }
            String level = tableItem.getLevel();
            Integer ans = tableItem.getAns();
            List<String> levels = Arrays.asList(StringUtils.split(level, " "));
            int levelIndex = levels.indexOf(ans + "");  //得出答案的等级 A B C D 的 索引
            if(levelIndex<0 || levelIndex > 3){
                throw  new IllegalArgumentException("统计评教表单项的等级时出错！ 答案不是评教等级之中的任何一个！tableItem:"+tableItem);
            }
            List<Integer> list = tableItemLevelMap.get(key);
            list.set(levelIndex,list.get(levelIndex)+1);

            tableItemScoreMap.put(key,tableItemScoreMap.get(key)+tableItem.getAns());


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
