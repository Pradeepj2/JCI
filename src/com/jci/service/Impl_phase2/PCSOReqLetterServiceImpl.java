package com.jci.service.Impl_phase2;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jci.dao_phase2.PCSOReqLetterDao;
import com.jci.model.PCSORequestLetter;
import com.jci.service_phase2.PCSOReqLetterService;


@Service
public class PCSOReqLetterServiceImpl implements PCSOReqLetterService{

	
	@Autowired
	PCSOReqLetterDao genReqLetterDao;
	
	public void create(PCSORequestLetter requestLetter) {
		genReqLetterDao.create(requestLetter);
	}
	
	public List<PCSORequestLetter> getLetters(){
		return genReqLetterDao.getAllRequestLetters();
	}
	
	public void delete(int id) {
		genReqLetterDao.delete(id);
	}

	
	


		


}
