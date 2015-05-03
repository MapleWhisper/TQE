package com.tqe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqe.dao.StudentDao;
import com.tqe.po.Clazz;
import com.tqe.po.Department;
import com.tqe.po.Major;
import com.tqe.po.Student;
import com.tqe.po.User;

@Service
public class StudentServiceImpl extends BaseService<Student>{
		
		/**
		 * 得到所有的学生列表
		 * 不推荐使用
		 */
		@Override
		public List<Student> findAll() {
			return studentDao.findAll();
		}
		
		
		/**
		 * 根据查询条件得到所有的学生列表
		 * @param condition
		 * @return
		 */
		public List<Student> findByCondition(HashMap<String,String> condition) {
			return studentDao.findByCondition(condition);
		}
		
		/**
		 * 根据学生的id得到学生
		 * @param sid
		 * @return
		 */
		public Student getById(String sid) {
			return studentDao.getById(sid);
		}
		
		/**
		 * 根据学生的id得到学生姓名
		 * @param sid
		 * @return
		 */
		public Student getNameById(String sid) {
			return studentDao.getNameById(sid);
		}
		
		/**
		 * 保存学生信息
		 */
		public void save(Student e) {
			studentDao.save(e);
		}
		
		
		/**
		 * 从excel得到的学生数据
		 * 导入到数据库
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
		 * 处理学生数据 外键关系
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

		/**
		 * 学生登录
		 * @param user
		 * @return
		 */
		public Student login(User user) {
			return studentDao.login(user);
		}

		
		/**
		 * 根据课程号 选出所有选了这门课的学生
		 * 如果改教师在改批次已经评价过学生了
		 * 那么就设置状态不可评价
		 * @param cid
		 * @param cno
		 * @return
		 */
		public List<Student> findAllByCId(String cid, Integer cno,String tid,Integer bid ) {
			List<Student> studentList = studentDao.findAllByCId(cid,cno);
			List<String> sidList = evalDao.findAllSidsByCidTid(cid,cno,tid,bid);	//选出对应课程 教师号 和 批次 已经评价过得学生Ids
			for(Student stu : studentList){		//状态为不可评价
				if(sidList.contains(stu.getSid())){
					stu.setEvaled(true);
				}
			}
			return studentList;
		}
		
		
		/**
		 * 
		 * @param sid
		 * @param cid
		 * @param cno
		 * @return
		 */
		public boolean isCoursePermitted(String sid, String cid, Integer cno) {
			return false;
		}
		
}
