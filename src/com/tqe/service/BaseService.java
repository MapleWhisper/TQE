package com.tqe.service;

import java.util.List;


public interface BaseService<E> {
	public E getById(Integer id);
	
	public void save(E e);
	
	public List<E> findAll();
}
