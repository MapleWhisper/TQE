package com.tqe.service;

import java.util.List;
import java.util.Map;

import com.tqe.base.vo.PageVO;
import com.tqe.po.Batches;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tqe.po.Course;

@Service
public class CourseServiceImpl extends BaseService<Course>{

    Log logger = LogFactory.getLog(CourseServiceImpl.class);
		@Override
		public List<Course> findAll() {
			return courseDao.findAll();
		}
		
		public Course getById(String cid,Integer cno) {
			Course c = courseDao.getById(cid,cno);
			c.setTeacher(teacherDao.getById(c.getTeacherId()));
			return c;
		}
		
		public void save(Course e) {
			courseDao.save(e);
		}
		
		/**
		 * 保存所有课程到数据库
		 * @param list
		 * @param season	课程季度
		 * @return
		 */
		public boolean saveAll(List<Course> list,String season){
			boolean f = false;
			Map<String,Integer> dMap = convertDepListToMap(departmentDao.findAll());
			try {
				if(list!=null){
					for(Course c:list){
						if(c.getCid()!=null && c.getCno()!=null){
							try {
								c.setSeason(season);
								processCouData(dMap, c);
								save(c);
							} catch (Exception e) {
                                logger.warn(e.getMessage(),e);
							}
						}
					}
					f= true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return f;
			
		}
		
		/**
		 * 返回所有学生已经选课的列表，
		 * 只返回当前学期可以评教的课程 之前学期的课程不再显示
		 * 如果学生已经评价了课程，那么设置课程已评价
		 * @param sid 学生Id
		 * @param batches   当前可以评教的批次
		 * @return
		 */
		public List<Course> findAllBySId(String sid,Batches batches) {
			List<Course> list = courseDao.findAllBySid(sid, batches.getSeason());
			List<String> cids = evalDao.findAllStuTablecids(sid, batches.getId());
			for(Course c: list){	//如果课程已经评价 那么设置课程已评价
				if(cids.contains(c.getCid())){
					c.setIsEvaled(true);
				}
			}
			return list;
		}
		
		/**
		 * 返回所有教师可以评价的课程组
		 * 如果教师已经评价了课程，那么设置课程已评价
		 * @return
		 */
		public List<Course> findAllByTId(String tid, Integer bid) {
			List<Course> list = courseDao.findAllByTId(tid);
			List<String> cids = evalDao.findAllTeaTablecids(tid,bid);
			for(Course c: list){
				if(cids.contains(c.getCid()+c.getCno())){
					c.setIsEvaled(true);
				}
			}
			return list;
		}
		
		/**
		 * 返回所有教师教的所有课程
		 * 如果教师已经评价了课程，那么设置课程已评价
		 * @return
		 */
		public List<Course> findAllByTid(String tid, Integer bid) {
			List<Course> list = courseDao.findAllByTid(tid);
			/*
			List<String> cids = evalDao.findAllTeaTablecids(tid,bid);
			for(Course c: list){
				if(cids.contains(c.getCid()+c.getCno())){
					c.setIsEvaled(true);
				}
			}
			*/
			return list;
		}

		public List<Course> findByCondition(PageVO pageVO) {
			return courseDao.findByCondition(pageVO);
		}
		
		/**
		 * 处理课程数据
		 * @param dMap 部门信息
		 */
		private void processCouData(Map<String, Integer> dMap, Course cou) {
			cou.setDepartmentId(dMap.get(cou.getDepartment()));
		}

}
