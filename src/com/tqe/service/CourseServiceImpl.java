package com.tqe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tqe.dao.CourseDao;
import com.tqe.po.Course;
import com.tqe.po.Course;

@Service
public class CourseServiceImpl implements BaseService<Course>{
		@Resource
		private CourseDao courseDao;
		@Override
		public List<Course> findAll() {
			return courseDao.findAll();
		}
		@Override
		public Course getById(Integer id) {
		// TODO Auto-generated method stub
			return null;
		}
		
		public Course getById(Integer cid,Integer cno) {
			return courseDao.getById(cid ,cno);
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
}
