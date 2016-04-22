package com.tqe.dao;

import java.util.List;

import com.tqe.base.vo.PageVO;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<E> {
	
	public List<E> findAll(PageVO type);
	
	
	public void save(E e);
	
	
	public void  delete(int id);
	
}
