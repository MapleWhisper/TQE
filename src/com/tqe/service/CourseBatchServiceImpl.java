package com.tqe.service;

import com.alibaba.fastjson.JSON;
import com.tqe.base.enums.EvalResultLevel;
import com.tqe.base.enums.UserType;
import com.tqe.po.*;
import org.apache.commons.lang3.StringUtils;
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
            courseBatch.setStuQuestionList(buildQuestionWithAnsPairList(stuTable.getQuestionNameList(), new ArrayList<ResultTable>(stuTableList)));
        }
        if(teaTableList.size()>0){
            EvalTable teaTable = EvalTable.json2Object(teaTableList.get(0).getJsonString());
            courseBatch.setTeaQuestionList(buildQuestionWithAnsPairList(teaTable.getQuestionNameList(),new ArrayList<ResultTable>(teaTableList)));
        }
        if(leaTableList.size()>0){
            EvalTable leaTable = EvalTable.json2Object(leaTableList.get(0).getJsonString());
            courseBatch.setLeaQuestionList(buildQuestionWithAnsPairList(leaTable.getQuestionNameList(), new ArrayList<ResultTable>(leaTableList)));
        }
        return courseBatch;
    }

    private List<Pair<String,List<String>>> buildQuestionWithAnsPairList(List<String> questionNameList,List<ResultTable> resultTables){
        if(questionNameList==null){
            return null;
        }
        List<Pair<String,List<String>>> pairList = new ArrayList<Pair<String, List<String>>>();
        //对于每个问题 加入说有学生的回答
        for(int i=0;i<questionNameList.size();i++){
            Pair<String,List<String>> questionWithAnsListPair = new MutablePair<String, List<String>>(questionNameList.get(i), new ArrayList<String>());
            for(int j=0;j<resultTables.size();j++){
                questionWithAnsListPair.getValue().add(resultTables.get(j).getQuestionAnsList().get(i));
            }
            pairList.add(questionWithAnsListPair);
        }
        return pairList;
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
                //数据数据库已经存在该记录 并且选课的总人数 和 评教的人数都没有发生变化 那么暂时不需要重新统计
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
                courseBatch.setTeaEvalTableJsonString(tableItemListJsonString);
                break;
            case LEADER:
                courseBatch.setLeaEvalAvgScore(avgScore);
                courseBatch.setLeaEvalLevelCnts(levelCnts);
                courseBatch.setLeaEvalTableJsonString(tableItemListJsonString);
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
        table.setJsonString(null);
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
                List<Integer> list = Arrays.asList(0,0,0,0);
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
