package com.jci.dao_phase2;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.jci.model.Contractgeneration;

public interface ContractGenerationDao2 {
	void create(Contractgeneration contract);
	int highestDerivativePrice(String cropYear, String deliveryType);
	boolean isValidContractIdn(String contractIdn);
	ModelAndView pcso_details(List<String> pcsoDates,String gradeComp , String deliveryType);

}
