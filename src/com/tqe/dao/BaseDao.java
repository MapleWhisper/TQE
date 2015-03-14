package com.tqe.dao;

import java.util.List;

public interface BaseDao<E> {
	public List<E> findAll();
	public E getById(Integer id);
	public void save(E e);
	public void  delete(int id);
	
}
