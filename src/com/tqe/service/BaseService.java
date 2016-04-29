package com.tqe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tqe.base.vo.PageVO;
import com.tqe.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tqe.po.Clazz;
import com.tqe.po.Department;
import com.tqe.po.Major;

@Component
@Transactional
public abstract class BaseService<E> {
	@Resource(name="adminDao")
	public AdminDao adminDao;
	
	@Autowired
	public TeacherDao teacherDao;
	
	@Autowired
	public BatchesDao batchesDao;
	
	@Autowired
	public CourseDao courseDao;
	
	@Autowired
	public EvalTableDao evalTableDao;
	
	@Autowired
	public StudentDao studentDao;
	
	@Autowired
	public EvalDao evalDao;
	
	@Autowired
	public DepartmentDao departmentDao;
	
	@Autowired
	public MajorDao majorDao;
	
	@Autowired
	public ClassDao classDao;
	
	@Autowired
	public PrivilegeDao privilegeDao;
	
	@Autowired
	public LeaderDao leaderDao;

	@Autowired
	public ScDao scDao;

    @Autowired
    public CourseBatchDao courseBatchDao;

    @Autowired
    public StudentSeasonDao studentSeasonDao;

    @Autowired
    public BatchScoreDao batchScoreDao;
	
	public  E getById(Integer id){
		return null;
	}
	public  void save(E e){
		
	}
	
	public  List<E> findAll(){
		return null;
	}
	
	/**
	 * 将Department List转换成Map 通过Name 来获取Id
	 * @param dlist
	 * @return
	 */
	public Map<String,Integer> convertDepListToMap(List<Department> dlist){
		Map<String,Integer> map = new HashMap<String, Integer>();
		if(dlist!=null && dlist.size()>0){
				for(Department d : dlist){
					map.put(d.getName(),d.getId());
				}
		}
		return map;
	}
	
	/**
	 * 将Major List转换成Map 通过Name 来获取Id
	 * @param dlist
	 * @return
	 */
	public Map<String,Integer> convertMajListToMap(List<Major> dlist){
		Map<String,Integer> map = new HashMap<String, Integer>();
		if(dlist!=null && dlist.size()>0){
				for(Major m : dlist){
					map.put(m.getName(),m.getId());
				}
		}
		return map;
	}
	
	/**
	 * 将Clazz List转换成Map 通过Name 来获取Id
	 * @param dlist
	 * @return
	 */
	public Map<String,Integer> convertClaListToMap(List<Clazz> dlist){
		Map<String,Integer> map = new HashMap<String, Integer>();
		if(dlist!=null && dlist.size()>0){
				for(Clazz c : dlist){
					map.put(c.getName(),c.getId());
				}
		}
		return map;
	}


}
