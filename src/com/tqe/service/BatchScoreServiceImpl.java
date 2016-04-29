package com.tqe.service;

import com.tqe.base.enums.UserType;
import com.tqe.po.BatchScore;
import com.tqe.po.Batches;
import com.tqe.po.Student;
import com.tqe.utils.SystemUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Maple on 2016/4/29.
 */
@Service
public class BatchScoreServiceImpl extends BaseService<BatchScore> {

    public List<BatchScore> findAll(String id) {
        return batchScoreDao.findAll(id);
    }


    public BatchScore getById(String id , Integer bid) {
        return batchScoreDao.getById(id,bid);
    }

    /**
     * 更新教师或者学生每批次的平均得分
     * 使用增量更新 不存在批次成绩的就保存
     * 最后更新当前批次的得分统计
     */
    public void updateTeaScore(String tid){
        //this.update(tid, UserType.TEACHER);
        List<Integer> bids = courseBatchDao.findBidByTid(tid);      //得到所有评教结果已经存在的批次
        List<BatchScore> batchScoreList = this.findAll(tid);        //得到当前和数据库已经保存的 每学期得分

        Batches curBatch = batchesDao.getAvailableBatches(SystemUtils.getSeason());
        if(curBatch==null) return ;
        if(bids.isEmpty()) return;
        for(Integer bid : bids){
            if(bid.equals(curBatch.getId())){   //如果之前学期没有统计的 那么就插入统计数据
                continue;
            }
            boolean isExist = false;
            for(BatchScore bs : batchScoreList){
                if(bs.getBid().equals(bid)){
                    isExist = true;     //说明数据库已经存在对应学期的评教成绩了
                    break;
                }
            }
            if(!isExist){
                doUpdateTeaScore(tid,bid);
            }

        }
        doUpdateTeaScore(tid,curBatch.getId());

    }

    private void doUpdateTeaScore(String tid, Integer bid){
        BatchScore batchScore = this.getById(tid,bid);
        BatchScore bs = courseBatchDao.getTeacherBatchScore(tid,bid);
        if(batchScore == null){     //如果数据库不存在 那么就插入
            batchScoreDao.save(bs);
        }else{
            batchScoreDao.update(bs);
        }
    }

    public void updateStuScore(String sid){
        this.update(sid, UserType.TEACHER);
    }

    private void update(String id, UserType userType){
        switch (userType){
            case STUDENT:
                Student stu = studentDao.getById(id);
                if(stu==null){
                    return ;
                }
                if(DateUtils.isSameDay(stu.getMtime(),null)){

                }
                break;
            case TEACHER:
                break;
            default:
                return ;
        }
    }
}
