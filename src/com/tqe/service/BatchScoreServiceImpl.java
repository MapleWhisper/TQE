package com.tqe.service;

import com.tqe.base.enums.UserType;
import com.tqe.po.BatchScore;
import com.tqe.po.Batches;
import com.tqe.utils.SystemUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        updateScore(tid,UserType.TEACHER);

     }

    public void updateStuScore(String sid){
        this.updateScore(sid, UserType.STUDENT);
    }

    private void doUpdateScore(String id, Integer bid, UserType userType){
        BatchScore batchScore = this.getById(id,bid);
        BatchScore bs = null;
        if(userType.equals(UserType.TEACHER)){
            bs = courseBatchDao.getTeacherBatchScore(id,bid);
        }else if(userType.equals(UserType.STUDENT)){
            bs = evalDao.getStudentBatchScore(id,bid);
        }


        if(batchScore == null){     //如果数据库不存在 那么就插入
            batchScoreDao.save(bs);
        }else{
            batchScoreDao.update(bs);
        }
    }



    private void updateScore(String id, UserType userType){
        List<Integer> bids = new ArrayList<Integer>();
        switch (userType){
            case STUDENT:
                bids = evalDao.findTeaStuBidBySid(id);
                break;
            case TEACHER:
                bids = courseBatchDao.findBidByTid(id);      //得到所有评教结果已经存在的批次
                break;
            default:
                return ;
        }

        List<BatchScore> batchScoreList = this.findAll(id);        //得到当前和数据库已经保存的 每学期得分

        Batches curBatch = batchesDao.getAvailableBatches(SystemUtils.getSeason());
        if(bids.isEmpty()) return;
        for(Integer bid : bids){

            if(curBatch!=null && bid.equals(curBatch.getId())){   //如果之前学期没有统计的 那么就插入统计数据
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
                doUpdateScore(id,bid , userType);
            }

        }
        if(curBatch!=null){
            doUpdateScore(id,curBatch.getId() , userType);
        }
    }
}
