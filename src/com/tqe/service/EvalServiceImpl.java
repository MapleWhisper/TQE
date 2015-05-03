package com.tqe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tqe.po.EvalTable;
import com.tqe.po.LeaTable;
import com.tqe.po.TeaStuTable;
import com.tqe.po.StuTable;
import com.tqe.po.TeaTable;

@Service
public class EvalServiceImpl extends BaseService<EvalTable>{
	
	/**
	 * 保存学生评教表
	 * @param stuTable
	 * @throws Exception
	 */
	public void saveStuTable(StuTable stuTable) throws Exception{
			Integer isCoursePermited = studentDao.isCoursePermitted(stuTable.getSid(),stuTable.getCid(),stuTable.getCno());
			if(isCoursePermited>=1){		//判断学生是否可以评价该课程
				evalDao.saveStuTable(stuTable);
			}else{
				throw new IllegalAccessException("学生还有选择门课，不能评价！");
			}
	}
	
	/**
	 * 保存教师评教表
	 * @param teaTable
	 * @throws Exception
	 */
	public void saveTeaTable(TeaTable teaTable) throws Exception{
		try {
			evalDao.saveTeaTable(teaTable);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 保存教师评价学生表
	 * @param teaTable
	 * @throws Exception
	 */
	public void saveTeaStuTable(TeaStuTable teaStuTable) throws Exception{
		try {
			evalDao.saveTeaStuTable(teaStuTable);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 保存领导评教表
	 * @param leaTable	领导评教表
	 * @throws Exception
	 */
	public void saveLeaTable(LeaTable leaTable) {
		try {
			evalDao.saveLeaTable(leaTable);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 根据学生评教表Id取出学生评教表
	 * @param stuTableId
	 * @return
	 */
	public StuTable getStuTableById(Integer stuTableId) {
		StuTable stuTable = evalDao.getStuTableById(stuTableId);
		stuTable.setStudent(studentDao.getById(stuTable.getSid()));
		stuTable.setBatches(batchesDao.getById(stuTable.getBid()));
		stuTable.setCourse(courseDao.getById(stuTable.getCid(),stuTable.getCno()));
		return stuTable;
	}
	
	/**
	 * 根据教师评教表Id取出教师评教表
	 * @param teaTableid
	 * @return
	 */
	public TeaTable getTeaTableById(Integer teaTableid) {
		TeaTable teaTable = evalDao.getTeaTableById(teaTableid);
		teaTable.setTeacher(teacherDao.getById(teaTable.getTid()));
		teaTable.setBatches(batchesDao.getById(teaTable.getBid()));
		teaTable.setCourse(courseDao.getById(teaTable.getCid(),teaTable.getCno()));
		return teaTable;
	}
	
	/**
	 * 根据领导评教表Id取出教师评教表
	 * @param teaTableid
	 * @return
	 */
	public LeaTable getLeaTableById(Integer leaTableId) {
		LeaTable leaTable = evalDao.getLeaTableById(leaTableId);
		leaTable.setLeader(leaderDao.getById(leaTable.getLid()));
		leaTable.setBatches(batchesDao.getById(leaTable.getBid()));
		leaTable.setCourse(courseDao.getById(leaTable.getCid(), leaTable.getCno()));
		return leaTable;
	}

	
	/**
	 * 得到学生已经评教过得课程Ids
	 * @param sid
	 * @param bid
	 * @return
	 */
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

	
	/**
	 * 得到 cid 和cno bid 对应的课程的所有 领导评教信息
	 * @param cid 课程号
	 * @param cno 课序号
	 * @param bid 批次号
	 * @return
	 */
	public List<LeaTable> findAllTeaLableByCourse(String cid, Integer cno,
			Integer bid) {
		List<LeaTable> list = evalDao.findAllLeaTableByCourse(cid,cno,bid);
		for(LeaTable leaTable : list){
			leaTable.setLeader(leaderDao.getById(leaTable.getLid()));
			leaTable.setBatches(batchesDao.getById(leaTable.getBid()));
		}
		return list; 
	}
	
	public void update(EvalTable eTable) {
		evalTableDao.update(eTable);
	}


	
	



	
	
}
