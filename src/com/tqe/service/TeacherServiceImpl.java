package com.tqe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tqe.dao.TeacherDao;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import com.tqe.po.User;

@Service
public class TeacherServiceImpl extends BaseService<Teacher>{
	@Resource(name="teacherDao")
	private TeacherDao teacherDao;
	
	public Teacher getById(String id) {
		return  teacherDao.getById(id);
	}
	
	@Override
	public void save(Teacher e) {
		teacherDao.save(e);
	}
	
	public boolean saveAll(List<Teacher> list){
		boolean f = false;
		Map<String,Integer> dMap = convertDepListToMap(departmentDao.findAll());
		try {
			if(list!=null){
				for(Teacher t:list){
					if(t.getId()!=null){ 
						processTeaData(dMap, t);   //教师数据预处理
						save(t);					//保存教师到数据库
					}
					
				}
				f= true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
		
	}
	
	@Override
	public List<Teacher> findAll() {
		
		return teacherDao.findAll();
	}
	
	public Teacher login(User user) {
		return teacherDao.login(user);
	}

	public List<Teacher> findByPage(int start, int length) {
		return teacherDao.findByPage(start ,length);
	}

	public List<Teacher> findByCondition(HashMap<String, String> condition) {
		return teacherDao.findByCondition(condition);
	}
	
	/**
	 * 处理教师数据
	 * @param dMap 
	 * @param mMap
	 * @param cMap
	 * @param s
	 */
	private void processTeaData(Map<String, Integer> dMap, Teacher tea) {
		tea.setDepartmentId(dMap.get(tea.getDepartment()));
	}
}
