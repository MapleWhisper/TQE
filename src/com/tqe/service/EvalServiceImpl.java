package com.tqe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tqe.po.EvalTable;
import com.tqe.po.StuTable;
import com.tqe.po.TeaTable;

@Service
public class EvalServiceImpl extends BaseService<EvalTable>{
	public void saveStuTable(StuTable stuTable) throws Exception{
		try {
			evalDao.saveStuTable(stuTable);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void saveTeaTable(TeaTable teaTable) throws Exception{
		try {
			evalDao.saveTeaTable(teaTable);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public StuTable getStuTableById(Integer stuTableId) {
		StuTable stuTable = evalDao.getStuTableById(stuTableId);
		stuTable.setStudent(studentDao.getById(stuTable.getSid()));
		stuTable.setBatches(batchesDao.getById(stuTable.getBid()));
		stuTable.setCourse(courseDao.getById(stuTable.getCid(),stuTable.getCno()));
		return stuTable;
	}
	
	public TeaTable getTeaTableById(Integer teaTableid) {
		TeaTable teaTable = evalDao.getTeaTableById(teaTableid);
		teaTable.setTeacher(teacherDao.getById(teaTable.getTid()));
		teaTable.setBatches(batchesDao.getById(teaTable.getBid()));
		teaTable.setCourse(courseDao.getById(teaTable.getCid(),teaTable.getCno()));
		return teaTable;
	}
	
	public List<String> getAllStuTablecids(Integer sid,Integer bid){
		return evalDao.findAllStuTablecids(sid,bid);
	}
	
	/**
	 * 得到 cid 和cno bid 对应的课程的所有 学生评教信息
	 * @param cid 课程号
	 * @param cno 课序号
	 * @param bid 批次号
	 * @return
	 */
	public List<StuTable> findAllStuTableByCourse(String cid,Integer cno,Integer bid){
		System.out.println(cid+"-"+cno);
		List<StuTable> list = evalDao.findAllStuTableByCourse(cid,cno,bid);
		for(StuTable stuTable : list){
			stuTable.setStudent(studentDao.getById(stuTable.getSid()));
			stuTable.setBatches(batchesDao.getById(stuTable.getBid()));
		}
		return list; 
	}
	
	/**
	 * 得到 cid 和cno bid 对应的课程的所有 教师评教信息
	 * @param cid 课程号
	 * @param cno 课序号
	 * @param bid 批次号
	 * @return
	 */
	public List<TeaTable> findAllTeaTableByCourse(String cid, Integer cno,
			Integer bid) {
		
		List<TeaTable> list = evalDao.findAllTeaTableByCourse(cid,cno,bid);
		for(TeaTable teaTable : list){
			teaTable.setTeacher(teacherDao.getById(teaTable.getTid()));
			teaTable.setBatches(batchesDao.getById(teaTable.getBid()));
		}
		return list; 
	}

	public void update(EvalTable eTable) {
		evalTableDao.update(eTable);
	}



	
	
}
