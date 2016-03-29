package com.tqe.service;

import java.util.ArrayList;
import java.util.List;

import com.tqe.po.*;
import org.springframework.stereotype.Service;

@Service
public class EvalServiceImpl extends BaseService<EvalTable>{
	
	/**
	 * 保存学生评教表
	 * @param stuTable
	 * @throws Exception
	 */
	public void saveStuTable(StuResultTable stuTable) throws Exception{
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
	public void saveTeaTable(TeaResultTable teaTable) throws Exception{
		try {
			evalDao.saveTeaTable(teaTable);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 保存教师评价学生表
	 * @throws Exception
	 */
	public void saveTeaStuTable(TeaStuResultTable teaStuTable) throws Exception{
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
	public void saveLeaTable(LeaResultTable leaTable) {
		try {
			evalDao.saveLeaTable(leaTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据学生评教表Id取出学生评教表
	 */
	public StuResultTable getStuTableById(Integer stuTableId) {
		StuResultTable stuTable = evalDao.getStuTableById(stuTableId);
		stuTable.setStudent(studentDao.getById(stuTable.getSid()));
		stuTable.setBatches(batchesDao.getById(stuTable.getBid()));
		stuTable.setCourse(courseDao.getById(stuTable.getCid(),stuTable.getCno()));
		return stuTable;
	}
	
	/**
	 * 根据教师评教表Id取出教师评教表
	 */
	public TeaResultTable getTeaTableById(Integer teaTableid) {
		TeaResultTable teaTable = evalDao.getTeaTableById(teaTableid);
		teaTable.setTeacher(teacherDao.getById(teaTable.getTid()));
		teaTable.setBatches(batchesDao.getById(teaTable.getBid()));
		teaTable.setCourse(courseDao.getById(teaTable.getCid(),teaTable.getCno()));
		return teaTable;
	}
	
	/**
	 * 根据领导评教表Id取出教师评教表
	 */
	public LeaResultTable getLeaTableById(Integer leaTableId) {
		LeaResultTable leaTable = evalDao.getLeaTableById(leaTableId);
		leaTable.setLeader(leaderDao.getById(leaTable.getLid()));
		leaTable.setBatches(batchesDao.getById(leaTable.getBid()));
		leaTable.setCourse(courseDao.getById(leaTable.getCid(), leaTable.getCno()));
		return leaTable;
	}
	
	public TeaStuResultTable getTeaStuTableById(Integer id) {
		TeaStuResultTable table = evalDao.getTeaStuTableById(id);
		table.setBatches(batchesDao.getById(table.getBid()));
		table.setCourse(courseDao.getById(table.getCid(), table.getCno()));
		return table;
	}


	
	/**
	 * 得到学生已经评教过得课程Ids
	 * @param sid
	 * @param bid
	 * @return
	 */
	public List<String> getAllStuTablecids(String sid,Integer bid){
		return evalDao.findAllStuTablecids(sid,bid);
	}
	
	/**
	 * 得到 cid 和cno bid 对应的课程的所有 学生评教信息
	 * @param cid 课程号
	 * @param cno 课序号
	 * @param bid 批次号
	 * @return
	 */
	public List<StuResultTable> findAllStuTableByCidAndBid(String cid, Integer cno, Integer bid){
		List<StuResultTable> list = evalDao.findAllStuTableByCourse(cid,cno,bid);
		for(StuResultTable stuTable : list){
			stuTable.setStudent(studentDao.getById(stuTable.getSid()));
			stuTable.setBatches(batchesDao.getById(stuTable.getBid()));
		}
		return list; 
	}
	
	/**
	 * 根据 sid 和 bid 获取对应 教师评价学生 所有结果
	 * @param bid 批次号
	 * @return
	 */
	public List<TeaStuResultTable> findAllTeaStuTableBySid(String sid,Integer bid){
		List<TeaStuResultTable> list = evalDao.findAllTeaStuTableBySid(sid,bid);
		for(TeaStuResultTable t:list){
			t.setCourse(courseDao.getById(t.getCid(), t.getCno()));
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
	public List<TeaResultTable> findAllTeaTableByCidAndBid(String cid, Integer cno,
                                                           Integer bid) {
		
		List<TeaResultTable> list = evalDao.findAllTeaTableByCourse(cid,cno,bid);
		for(TeaResultTable teaTable : list){
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
	public List<LeaResultTable> findAllTeaLableByCidAndBid(String cid, Integer cno,
                                                           Integer bid) {
		List<LeaResultTable> list = evalDao.findAllLeaTableByCourse(cid,cno,bid);
		for(LeaResultTable leaTable : list){
			leaTable.setLeader(leaderDao.getById(leaTable.getLid()));
			leaTable.setBatches(batchesDao.getById(leaTable.getBid()));
		}
		return list; 
	}



	public void update(EvalTable eTable) {
		evalTableDao.update(eTable);
	}


    public List<StuResultTable> findAllStuTableWithEvalTable(String cid, Integer cno, Integer bid) {
        List<StuResultTable> resultTableList = evalDao.findAllStuTableWithJSONString(cid,cno,bid);
        deSerializableResultTable(new ArrayList<ResultTable>(resultTableList));
        return resultTableList;
    }

    public List<TeaResultTable> findAllTeaTableWithEvalTable(String cid, Integer cno, Integer bid) {
        List<TeaResultTable> resultTableList = evalDao.findAllTeaTableWithJSONString(cid, cno, bid);
        deSerializableResultTable(new ArrayList<ResultTable>(resultTableList));
        return resultTableList;
    }

    public List<LeaResultTable> findAllLeaTableWithEvalTable(String cid, Integer cno, Integer bid) {
        List<LeaResultTable> resultTableList = evalDao.findAllLeaTableWithJSONString(cid, cno, bid);
        deSerializableResultTable(new ArrayList<ResultTable>(resultTableList));
        return resultTableList;
    }

    /**
     * 反序列化 评教结果表的评价内容
     */
    private void deSerializableResultTable(List<ResultTable> resultTableList){
        if(resultTableList!=null && !resultTableList.isEmpty()){
            for(ResultTable resultTable : resultTableList){
                resultTable.setEvalTable(EvalTable.json2Object(resultTable.getJsonString()));
            }
        }
    }
}
