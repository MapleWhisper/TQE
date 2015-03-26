package com.tqe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.tqe.dao.AdminDao;
import com.tqe.dao.BatchesDao;
import com.tqe.dao.CourseDao;
import com.tqe.dao.EvalTableDao;
import com.tqe.dao.StudentDao;
import com.tqe.dao.TeacherDao;


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
	
	public abstract E getById(Integer id);
	
	public abstract void save(E e);
	
	public abstract List<E> findAll();
}
