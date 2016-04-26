package com.tqe.service;

import com.alibaba.fastjson.JSON;
import com.tqe.base.vo.PageVO;
import com.tqe.po.*;
import com.tqe.utils.ResultTableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class StudentSeasonServiceImpl extends BaseService<CourseBatch> {

    @Autowired
    private EvalServiceImpl evalService;

    private static final Log logger = LogFactory.getLog(StudentSeasonServiceImpl.class);

    public StudentSeason getById(String sid, String season) {

        return studentSeasonDao.getById(sid, season);

    }



    public void saveOrUpdate(StudentSeason stuSeason) {
        StudentSeason s = studentSeasonDao.getById(stuSeason.getSid(), stuSeason.getSeason());
        if (s == null) {
            studentSeasonDao.save(stuSeason);
        } else {
            studentSeasonDao.update(stuSeason);
        }

    }


    public void reAnalyseStuSeason(String sid, String season) {
        if (StringUtils.isBlank(sid) || StringUtils.isBlank(season)) {
            throw new IllegalArgumentException("sid 和 season 不能为空");
        }
        PageVO pageVO = new PageVO();
        pageVO.getFilters().put("sid", sid);
        pageVO.getFilters().put("season", season);
        pageVO.getFilters().put("withJsonString", "true");

        StudentSeason stuSeason = studentSeasonDao.getById(sid, season);

        if (stuSeason == null) {
            stuSeason = new StudentSeason(sid, season);
        } else {
            //如果已经存在评教表了  但是一天只能 更新一次
            if (DateUtils.isSameDay(stuSeason.getMtime(), new Date())) {
                return;
            }
        }
        List<TeaStuResultTable> tableList = evalService.findTeaStuResultTable(pageVO, false);
        if (tableList.isEmpty()) {    //如果没有新的评教表 那么就返回
            return;
        }
        doAnalyseStuSeason(stuSeason, tableList);

    }

    private void doAnalyseStuSeason(StudentSeason stuSeason, List<TeaStuResultTable> tableList) {

        List<ResultTable> resultTableList = new ArrayList<ResultTable>(tableList);
        stuSeason.setResultTableNum(tableList.size());
        stuSeason.setAvgScore(ResultTableUtils.calcAvgScore(resultTableList));
        stuSeason.setLevelCnts(Arrays.asList(ResultTableUtils.calcLevelCnt(resultTableList)));

        stuSeason.setAvgScoreList(ResultTableUtils.getAvgScoreList(resultTableList));

        stuSeason.setResultTableJsonString(JSON.toJSONString(ResultTableUtils.processTableItemScoreAndLevelCnt(resultTableList)));

        stuSeason.setQuestionList(ResultTableUtils.buildQuestionWithAnsPairList(resultTableList));
        stuSeason.setQuestionListString(JSON.toJSONString(stuSeason.getQuestionList()));

        saveOrUpdate(stuSeason);
    }
}
