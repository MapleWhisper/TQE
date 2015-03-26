package com.tqe.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<E> {
	public List<E> findAll();
	public void save(E e);
	public void  delete(int id);
	
}
