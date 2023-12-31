package com.jci.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jci.dao.RulingMarketDao;
import com.jci.model.RulingMarket;
import com.jci.service.RulingMarketService;

@Service
public class RulingMarketServiceImpl implements RulingMarketService {

	@Autowired
	RulingMarketDao rulingDao;

	@Override
	public void create(RulingMarket rulingMarket) {
		rulingDao.create(rulingMarket);
	}

	@Override
	public void update(RulingMarket rulingMarket) {
		rulingDao.update(rulingMarket);
	}

	@Override
	public RulingMarket edit(int id) {
		return rulingDao.edit(id);
	}

	@Override
	public void delete(int id) {
		rulingDao.delete(id);
	}

	@Override
	public RulingMarket find(int id) {
		return rulingDao.find(id);
	}

	@Override
	public List<RulingMarket> getAll() {
		return rulingDao.getAll();
	}

	@Override
	public boolean submitform(RulingMarket rulingMarket) {
		return rulingDao.submitform(rulingMarket);
	}

}
