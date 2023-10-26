package com.jci.dao_phase2;

import java.util.List;

import com.jci.model.PCSORequestLetter;


public interface PCSOReqLetterDao {

	public void create(PCSORequestLetter requestLetter);
	public List<PCSORequestLetter> getAllRequestLetters();
	public PCSORequestLetter getAllRequestLetter(int reqId);
	public void delete(int reqId);
	
	
	

}
