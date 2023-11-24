package com.jci.service_phase2;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.jci.model.Contractgeneration;

public interface ContractGenerationService2 {
	public void create(Contractgeneration contract);
	public List<Contractgeneration> getAllContract();
	public boolean isValidContractIdn(String contractIdn);
	public List<Object[]> getListOfGradesPrice(String deliveryType, String cropYear);
	public List<Object[]> getListOfGradeComposition(String gradeComp);
	ModelAndView pcso_details(List<String> pcso,String gradeComp , String deliveryType);
}
