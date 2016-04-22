package com.tqe.service;

import java.util.List;

import com.tqe.base.vo.PageVO;
import org.springframework.stereotype.Service;

import com.tqe.po.Leader;
import com.tqe.po.User;

@Service
public class LeaderServiceImpl extends BaseService<Leader>{
	@Override
	public Leader getById(Integer id) {
		return leaderDao.getById(id);
	}
	
	@Override
	public void save(Leader lea) {
		leaderDao.save(lea);
	}

	public List<Leader> findAll(PageVO type) {
		return leaderDao.findAll(type);
	}

	public Leader login(User user) {
		return leaderDao.login(user);
	}

	public void delete(Integer id) {
		leaderDao.delete(id);
		
	}
}
