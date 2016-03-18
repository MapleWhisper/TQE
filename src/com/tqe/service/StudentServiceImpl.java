package com.tqe.service;

import java.util.List;
import java.util.Map;

import com.tqe.base.vo.PageVO;
import com.tqe.po.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends BaseService<Student>{

		@Autowired
		private DepartmentServiceImpl departmentService;
		
		/**
		 * �õ����е�ѧ���б�
		 * ���Ƽ�ʹ��
		 */
		@Override
		public List<Student> findAll() {
			return studentDao.findAll();
		}
		
		
		/**
		 * ���ݲ�ѯ�����õ����е�ѧ���б�
		 * @return
		 */
		public List<Student> findByPageVO(PageVO pageVO) {
			List<Student> stuList  = studentDao.findByPageVO(pageVO);
			if(stuList!=null){
				//���ܱ�����...
				for(Student stu : stuList){
					stu.setIdNumber(null);
					stu.setPassword(null);
				}
			}
			return stuList;
		}
		
		/**
		 * ����ѧ����id�õ�ѧ��
		 * @param sid
		 * @return
		 */
		public Student getById(String sid) {
			return studentDao.getById(sid);
		}
		
		/**
		 * ����ѧ����id�õ�ѧ������
		 * @param sid
		 * @return
		 */
		public Student getNameById(String sid) {
			return studentDao.getNameById(sid);
		}
		
		/**
		 * ����ѧ����Ϣ
		 */
		public void save(Student e) {
			studentDao.save(e);
		}
		
		
		/**
		 * ��excel�õ���ѧ������
		 * ���뵽���ݿ�
		 * @param list
		 * @return
		 */
		public boolean saveAll(List<Student> list){
			
			Map<String,Integer> dMap = convertDepListToMap(departmentDao.findAll());	
			Map<String,Integer> mMap = convertMajListToMap(majorDao.findAll());
			Map<String,Integer> cMap = convertClaListToMap(classDao.findAll());
			boolean f = false;
			try {
				if(list!=null){
					for(Student s:list){
						if(s.getSid()!=null){
							boolean reload  = processStuData(dMap, mMap, cMap, s);
							if(reload){		//�������ѧ�����ݵ�ͬʱ������ ѧԺ רҵ �༶����Ϣ ��ô���¼���Map
								dMap = convertDepListToMap(departmentDao.findAll());
								mMap = convertMajListToMap(majorDao.findAll());
								cMap = convertClaListToMap(classDao.findAll());
							}
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
		 * ����ѧ������ �����ϵ
		 * @param dMap ѧԺ��Ϣ
		 * @param mMap רҵ��Ϣ
		 * @param cMap �༶��Ϣ
		 * @param s ѧ��
		 */
		private boolean processStuData(Map<String, Integer> dMap,
				Map<String, Integer> mMap, Map<String, Integer> cMap, Student s) {
			String m = s.getMajor();
			boolean reload =false;
			if(m.endsWith(".")){		//���רҵ���� "." ������ ��ô�Ͱ� "." ȥ��
				s.setMajor(m.substring(0, m.length()-1));
			}
			//�����ѧ���Ĳ��Ų����� ��ô����ò���
			if(StringUtils.isNoneBlank(s.getDepartment()) && !dMap.containsKey(s.getDepartment()) ) {
				departmentDao.save(new Department(s.getDepartment()));
				dMap = convertDepListToMap(departmentDao.findAll());	//���¼���
				reload = true;
			}
			s.setDepartmentId(dMap.get(s.getDepartment()));

			//���ѧ����רҵ������ ��ô�����רҵ
			if(StringUtils.isNotBlank(s.getMajor()) && !mMap.containsKey(s.getMajor())){
				Major major = new Major();
				major.setName(s.getMajor());
				major.setDepartmentId(dMap.get(s.getDepartment()));
				majorDao.save(major);
				mMap = convertMajListToMap(majorDao.findAll());	//���¼���
				reload = true;
			}
			s.setMajorId(mMap.get(s.getMajor()));

			//���ѧ���İ༶��Ϣ������ ��ô����ð༶
			if(StringUtils.isNotBlank(s.getClazz()) && !cMap.containsKey(s.getClazz())){
				Clazz clazz = new Clazz();
				clazz.setName(s.getClazz());
				clazz.setMajorId(mMap.get(s.getMajor()));
				clazz.setDepartmentId(dMap.get(s.getDepartment()));
                clazz.setGrade(s.getGrade());
				classDao.save(clazz);
				cMap = convertClaListToMap(classDao.findAll());
				reload = true;
			}
			s.setClassId(cMap.get(s.getClazz()));
			return reload;
		}

		/**
		 * ѧ����¼
		 * @param user
		 * @return
		 */
		public Student login(User user) {
			return studentDao.login(user);
		}

		
		/**
		 * ���ݿγ̺� ѡ������ѡ�����ſε�ѧ��
		 * ����Ľ�ʦ�ڸ������Ѿ����۹�ѧ����
		 * ��ô������״̬��������
		 * @param cid
		 * @param cno
		 * @return
		 */
		public List<Student> findAllByCId(String cid, Integer cno,String tid,Integer bid ) {
			List<Student> studentList = studentDao.findAllByCId(cid,cno);
			List<String> sidList = evalDao.findAllSidsByCidTid(cid,cno,tid,bid);	//ѡ����Ӧ�γ� ��ʦ�� �� ���� �Ѿ����۹���ѧ��Ids
			for(Student stu : studentList){		//״̬Ϊ��������
				if(sidList.contains(stu.getSid())){
					stu.setIsEvaled(true);
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
