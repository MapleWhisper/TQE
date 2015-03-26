package com.tqe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tqe.dao.CourseDao;
import com.tqe.po.Course;

@Service
public class CourseServiceImpl extends BaseService<Course>{
	
		@Override
		public List<Course> findAll() {
			return courseDao.findAll();
		}
		
		public Course getById(String cid,Integer cno) {
			Course tem = new Course();
			tem.setCid(cid);
			tem.setCno(cno);
			Course c = courseDao.getById(tem);
			c.setTeacher(teacherDao.getById(c.getTeacherId()));
			return c;
		}
		
		public void save(Course e) {
			courseDao.save(e);
		}
		
		public boolean saveAll(List<Course> list,String season){
			boolean f = false;
			try {
				if(list!=null){
					for(Course c:list){
						if(c.getCid()!=null && c.getCno()!=null){
							try {
								c.setSeason(season);
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
		public List<Course> findAll(Integer sid) {
			List<Course> list = courseDao.findAllBySid(sid);
			List<String> cids = evalDao.getAllStuTablecids(sid);
			for(Course c: list){
				if(cids.contains(c.getCid())){
					c.setEvaled(true);
				}
			}
			return list;
		}
}
