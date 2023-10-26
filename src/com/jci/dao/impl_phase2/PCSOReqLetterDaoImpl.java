package com.jci.dao.impl_phase2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jci.dao_phase2.PCSOReqLetterDao;
import com.jci.model.PCSORequestLetter;

@Transactional
@Repository
public class PCSOReqLetterDaoImpl implements PCSOReqLetterDao {

	@Autowired
	SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void create(PCSORequestLetter requestLetter) {
		currentSession().save(requestLetter);
	}

	public List<PCSORequestLetter> getAllRequestLetters() {
		return this.sessionFactory.getCurrentSession().createCriteria(PCSORequestLetter.class).addOrder(Order.desc("creation_date")).list();

	}

	public PCSORequestLetter getAllRequestLetter(int reqId) {
		return new PCSORequestLetter();
	}

	public void delete(int id) {
		PCSORequestLetter request =(PCSORequestLetter) currentSession().load(PCSORequestLetter.class, id);
		if (request != null) {
			currentSession().delete(request);
		}
	}

}
