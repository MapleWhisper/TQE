package com.tqe.utils;

import com.tqe.base.enums.EvalResultLevel;
import com.tqe.po.EvalTable;
import com.tqe.po.ResultTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * Created by Maple on 2016/4/21.
 */
public class ResultTableUtils {

    /**
     * 对评教结果表 求平均成绩
     */
    public static double calcAvgScore(List<ResultTable> resultTables) {
        int scoreSum = 0;
        int tableSize = resultTables.size();


        for (ResultTable table : resultTables) {
            scoreSum += table.getScore();
        }

        if (scoreSum > 0 && tableSize > 0) {
            return scoreSum / tableSize;
        }
        return 0;
    }

    /**
     * 对评教结果表 求等级统计
     * levelCnt[优,良,中,差]
     */
    public static Integer[] calcLevelCnt(List<ResultTable> resultTables) {
        Integer levelCnt[] = new Integer[]{0, 0, 0, 0};

        for (ResultTable table : resultTables) {      //遍历评教表
            if (table.getLevel().equals(EvalResultLevel.BEST.getName())) {
                levelCnt[0]++;
            }
            if (table.getLevel().equals(EvalResultLevel.GOOD.getName())) {
                levelCnt[1]++;
            }
            if (table.getLevel().equals(EvalResultLevel.AVERAGE.getName())) {
                levelCnt[2]++;
            }
            if (table.getLevel().equals(EvalResultLevel.BAD.getName())) {
                levelCnt[3]++;
            }
        }
        return levelCnt;
    }

    public static List<Integer> getScoreList(List<ResultTable> resultTables) {
        List<Integer> scoreList = new ArrayList<Integer>();
        for (ResultTable table : resultTables) {
            scoreList.add(table.getScore());
        }
        return scoreList;
    }

    /**
     * 按批次 对评教结果表 计算每个批次的平均得分
     */
    public static List<Double> getAvgScoreList(List<ResultTable> resultTables) {
        List<Double> avgScoreList = new ArrayList<>();
        Map<Integer, List<Double>> map = new HashMap<>();
        for (ResultTable table : resultTables) {
            if (map.get(table.getBid()) == null) {
                map.put(table.getBid(), new ArrayList<Double>());
            }
            map.get(table.getBid()).add((double) table.getScore());
        }
        for (List<Double> list : map.values()) {
            avgScoreList.add(avgList(list));
        }

        return avgScoreList;
    }

    /**
     * 对List的元素求平均值
     */
    public static double avgList(List<Double> list) {
        int cnt = 0;
        double sum = 0L;
        double avg = 0L;
        for (double d : list) {
            if (d == 0) {
                continue;
            }
            cnt++;
            sum += d;
        }
        if (cnt > 0 && sum > 0) {
            avg = sum / cnt;
        }
        //保留两位小数
        avg = Double.parseDouble(String.format("%.2f", avg));
        return avg;
    }

    public static  EvalTable processTableItemScoreAndLevelCnt(List<ResultTable> resultTables) {
        //每个表项的平均得分 和 等级统计 结果 ，还有表项的内容
        Map<String, List<Integer>> tableItemLevelMap = new HashMap<String, List<Integer>>();
        Map<String, Integer> tableItemScoreMap = new HashMap<String, Integer>();
        Map<String,EvalTable.EvalTableItem> tableItemMap = new HashMap<String,EvalTable.EvalTableItem>();
        for (ResultTable table : resultTables) {
            processTableItemCnt(table.getEvalTable(), tableItemLevelMap, tableItemScoreMap , tableItemMap);
        }
        return postProcessTableItemCnt(resultTables, tableItemLevelMap, tableItemScoreMap , tableItemMap);
    }


    /**
     *  将评教表单项的 统计内容放到 表单里
     */
    private static EvalTable postProcessTableItemCnt(List<ResultTable> resultTables, Map<String, List<Integer>> tableItemLevelMap, Map<String, Integer> tableItemScoreMap, Map<String, EvalTable.EvalTableItem> tableItemMap) {
        int tableSize = resultTables.size();
        EvalTable table = new EvalTable();
        if (resultTables.size() > 0) {
            table = resultTables.get(0).getEvalTable();  //随意取出一个评教结果表 取出评教表

            /*
            for (EvalTable.EvalTableItem tableItem : table.getTableItemList()) {
                String key = tableItem.getContext();
                if (tableSize > 0) {
                    tableItem.setAvgScore(tableItemScoreMap.get(key) / tableSize);
                }
                tableItem.setScoreLevelCnts(tableItemLevelMap.get(key));
            }
            */
            table.getTableItemList().clear();   //清空 从结果Map 获取表项的key 放入结果表中
            if(tableSize==0) tableSize=1;
            for(String tableItemKey : tableItemScoreMap.keySet()){
                EvalTable.EvalTableItem tableItem = new EvalTable.EvalTableItem();
                tableItem.setContext(tableItemKey);
                tableItem.setAvgScore(tableItemScoreMap.get(tableItemKey) / tableSize);
                tableItem.setScoreLevelCnts(tableItemLevelMap.get(tableItemKey));
                tableItem.setMaxLevel(tableItemMap.get(tableItemKey).getMaxLevel());
                int percent =   (int)(( (double)tableItem.getAvgScore()/tableItem.getMaxLevel()  )*100)   ;
                tableItem.setPercent( percent ) ;
                table.getTableItemList().add(tableItem);
            }

        }
        table.setJsonString(null);
        return table;


    }

    /**
     *  统计评教表单项的等级统计和得分
     */
    private static void processTableItemCnt(
            EvalTable evalTable, Map<String, List<Integer>> tableItemLevelMap,
            Map<String, Integer> tableItemScoreMap,
            Map<String, EvalTable.EvalTableItem> tableItemMap){

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
            //根据答案 和在评分中的索引位置 得到答案的等级 A B C D 例如 40 30 20 10  如果 评分是10分 等级就是D（差）
            int levelIndex = levels.indexOf(ans + "");
            if(levelIndex<0 || levelIndex > 3){
                throw  new IllegalArgumentException("统计评教表单项的等级时出错！ 答案不是评教等级之中的任何一个！tableItem:"+tableItem);
            }
            List<Integer> list = tableItemLevelMap.get(key);
            list.set(levelIndex,list.get(levelIndex)+1);

            tableItemScoreMap.put(key,tableItemScoreMap.get(key)+tableItem.getAns());

            tableItem.setMaxLevel(Integer.parseInt(levels.get(0)));
            tableItemMap.put(key,tableItem);
        }
    }

    /**
     * 根据table的问题，构建问题对应的评教结果表的所有回答
     * 其中 每个结果表的 nameList 都是相同的，同一批次的结果表 才适用
     */
    public static List<Pair<String,List<String>>> buildQuestionWithAnsPairList(
            List<String> questionNameList,
            List<ResultTable> resultTables
    ){


        if(questionNameList==null){
            return null;
        }
        List<Pair<String,List<String>>> pairList = new ArrayList<Pair<String, List<String>>>();
        //对于每个问题 加入所有结果表的回答
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
     * 如果结果表的中评教表不相同 那么使用改方法统计 回答的问题
     * 性能问题比较严重，慎重使用
     */
    public static List<Pair<String, List<String>>> buildQuestionWithAnsPairList(List<ResultTable> resultTableList) {
        HashMap<String,List<String>> map = new HashMap<String, List<String>>();
        for(ResultTable table : resultTableList){
            List<EvalTable.EvalItem> itemList = table.getEvalTable().getQuestionList();

            if(itemList!=null && !itemList.isEmpty()){
                for(EvalTable.EvalItem questionItem : itemList){
                    String key = questionItem.getContext();
                    String ans = questionItem.getAns();
                    if(!map.containsKey(key)){
                        map.put(key,new ArrayList<String>());
                    }
                    map.get(key).add(ans);
                }
            }

        }
        List<Pair<String, List<String>>> resultList = new ArrayList<Pair<String, List<String>>>();
        for(Map.Entry<String,List<String>> entry: map.entrySet()){
            Pair<String,List<String>>  pair = new MutablePair<String, List<String>>(entry.getKey(),entry.getValue());
            resultList.add(pair);
        }
        return resultList;
    }
}
