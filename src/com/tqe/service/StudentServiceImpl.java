package com.tqe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tqe.dao.StudentDao;
import com.tqe.po.Clazz;
import com.tqe.po.Department;
import com.tqe.po.Major;
import com.tqe.po.Student;
import com.tqe.po.User;

@Service
public class StudentServiceImpl extends BaseService<Student>{
		@Resource
		private StudentDao StudentDao;
		
		
		@Override
		public List<Student> findAll() {
			return StudentDao.findAll();
		}
		
		public List<Student> findByCondition(HashMap<String,String> condition) {
			return StudentDao.findByCondition(condition);
		}
		
		public Student getById(String sid) {
			return StudentDao.getById(sid);
		}
		
		public void save(Student e) {
			StudentDao.save(e);
		}
		
		
		/**
		 * 保存学生数据到数据库
		 * @param list
		 * @return
		 */
		public boolean saveAll(List<Student> list){
			Map<String,Integer> dMap = convertDepListToMap(departmentDao.findAll());
			Map<String,Integer> mMap = convertMajListToMap(majorDao.findAll());
			Map<String,Integer> cMap = convertClaListToMap( clazzDao.findAll());
			boolean f = false;
			try {
				if(list!=null){
					for(Student s:list){
						if(s.getSid()!=null){
							processStuData(dMap, mMap, cMap, s);
							save(s);
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
		 * 处理学生数据
		 * @param dMap 
		 * @param mMap
		 * @param cMap
		 * @param s
		 */
		private void processStuData(Map<String, Integer> dMap,
				Map<String, Integer> mMap, Map<String, Integer> cMap, Student s) {
			String m = s.getMajor();
			if(m.endsWith(".")){		//如果专业是以 "." 结束的 那么就把 "." 去掉
				
				s.setMajor(m.substring(0, m.length()-1));
			}
			s.setDepartmentId(dMap.get(s.getDepartment()));
			s.setMajorId(mMap.get(s.getMajor()));
			s.setClassId(cMap.get(s.getClazz()));
		}

		public Student login(User user) {
			return StudentDao.login(user);
		}
		
}
