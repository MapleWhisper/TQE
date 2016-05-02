package com.tqe.service;

import java.util.Date;
import java.util.List;

import com.tqe.base.enums.BatchStatus;
import com.tqe.base.vo.PageVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqe.dao.BatchesDao;
import com.tqe.po.Batches;

@Service
public class BatchesServiceImpl extends BaseService<Batches>{
	@Autowired
	private BatchesDao batchesDao;
	@Override
	public Batches getById(Integer id) {
		return  batchesDao.getById(id);
		

	}

    public Batches getByIdWithEvalTable(Integer id){
        Batches b = this.getById(id);
        if(b!=null){
            b.setStuEval(evalTableDao.getById(b.getStuEvalId()));
            b.setTeaEval(evalTableDao.getById(b.getTeaEvalId()));
            b.setLeadTval(evalTableDao.getById(b.getLeadEvalId()));
            b.setTeaStuEval(evalTableDao.getById(b.getTeaStuEvalId()));
            return b;
        }
        return null;
    }
	
	@Override
	public void save(Batches e) {
		batchesDao.save(e);
	}

	public List<Batches> findAll(PageVO pageVO) {
		return batchesDao.findAll(pageVO);
	}

	public void delete(int id) {
		batchesDao.delete(id);
		
	}

	public void update(Batches b) {
		batchesDao.update(b);
	}
	
	public Batches getAvailableBatches(String season){
		return batchesDao.getAvailableBatches(season);
	}


	
	/**
	 * 根据学期 season 得到所有的批次Bathces 列表
	 * @param season
	 * @return
	 */
	public List<Batches> findAllBySeason(String season) {
		return batchesDao.findAllBySeason(season);
	}

    /**
     * 检查新批次的时间是否和已经存在的批次的时间冲突
     * @return 检测到冲突的批次
     */
    public Batches checkDateConflict(Batches newBatch) {


        if(newBatch == null ){
            return null;
        }
        Batches conflictBatch = null;
        //检查 开始日期
        conflictBatch = batchesDao.checkDateConflict(newBatch.getId(),newBatch.getBeginDate());
        if(conflictBatch!=null) return conflictBatch;

        //检查截止 日期
        conflictBatch = batchesDao.checkDateConflict(newBatch.getId(), newBatch.getEndDate());
        if(conflictBatch!=null) return conflictBatch;

        //检查 日期 是否 包含了 其他批次的时间
        conflictBatch = batchesDao.checkDateRangeConflict(newBatch.getId(),newBatch.getBeginDate(),newBatch.getEndDate());
        return conflictBatch;


    }

    public void reAnalyseStatistic(Integer bid){
        if(bid==null || bid<0){
            throw  new IllegalArgumentException("unknown bid :"+bid);
        }
        Batches batch = batchesDao.getById(bid);
        if(batch==null){
            return ;
        }
        //如果 批次不是在进行中 并且评教批次的结束时间小于批次的最后修改时间 那么就更新
        if(!batch.getBatchStatus().equals(BatchStatus.RUNNING.getName())
                && batch.getMtime().getTime() > batch.getEndDate().getTime()){
            return ;
        }
        if(!DateUtils.isSameDay(batch.getMtime(),new Date())){
            batchesDao.updateStatistic(bid);
        }

    }
}
