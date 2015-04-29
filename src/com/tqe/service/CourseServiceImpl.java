package com.tqe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tqe.dao.CourseDao;
import com.tqe.po.Course;
import com.tqe.po.Teacher;

@Service
public class CourseServiceImpl extends BaseService<Course>{
	
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
								continue;
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
		 * 返回所有学生已经选课的列表，如果学生已经评价了课程，那么设置课程已评价
		 * @param sid
		 * @return
		 */
		public List<Course> findAllBySId(Integer sid,Integer bid) {
			List<Course> list = courseDao.findAllBySid(sid);
			List<String> cids = evalDao.findAllStuTablecids(sid,bid);
			for(Course c: list){
				if(cids.contains(c.getCid())){
					c.setEvaled(true);
				}
			}
			return list;
		}
		
		/**
		 * 返回所有教师可以评价的课程，如果教师已经评价了课程，那么设置课程已评价
		 * @param sid
		 * @return
		 */
		public List<Course> findAllByTId(String tid, Integer bid) {
			List<Course> list = courseDao.findAllByTid(tid);
			List<String> cids = evalDao.findAllTeaTablecids(tid,bid);
			for(Course c: list){
				if(cids.contains(c.getCid()+c.getCno())){
					c.setEvaled(true);
				}
			}
			return list;
		}

		public List<Course> findByCondition(HashMap<String, String> condition) {
			return courseDao.findByCondition(condition);
		}
		
		/**
		 * 处理课程数据
		 * @param dMap 
		 * @param mMap
		 * @param cMap
		 * @param s
		 */
		private void processCouData(Map<String, Integer> dMap, Course cou) {
			cou.setDepartmentId(dMap.get(cou.getDepartment()));
		}

}
