package com.tqe.service;

import java.util.Date;
import java.util.List;

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

	public List<Batches> findAll() {
		return batchesDao.findAll();
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

	public Date getLatestDate(Integer id) {
		return batchesDao.getLatestDate(id);
	}
	
	/**
	 * 根据学期 season 得到所有的批次Bathces 列表
	 * @param season
	 * @return
	 */
	public List<Batches> findAllBySeason(String season) {
		return batchesDao.findAllBySeason(season);
	}
}
