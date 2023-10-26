package com.jci.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.id.SequenceIdentityGenerator.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.node.IntNode;
import com.google.gson.Gson;
import com.jci.model.Contractgeneration;
import com.jci.model.EntryDerivativePrice;
import com.jci.model.EntryofGradeCompositionModel;
import com.jci.model.EntryofpcsoModel;
import com.jci.model.PCSORequestLetter;
import com.jci.model.PcsoDateModel;
import com.jci.model.RoDispatchModel;
import com.jci.model.StateList;
import com.jci.service.DistrictService;
import com.jci.service_phase2.ContractGenerationService2;
import com.jci.service_phase2.EntryDerivativePriceService2;
import com.jci.service_phase2.EntryofGradeCompositionService;
import com.jci.service_phase2.PCSOReqLetterService;
import com.jci.service.StateService;
import com.jci.service.Impl.SendMail;
import com.jci.service_phase2.PcsoentryService;
import com.jci.service_phase2.RoDispatchService;

@Transactional
@Repository
@Controller
public class Controller_V {

	@Autowired
	PcsoentryService pcsoentryservice;

	@Autowired
	ContractGenerationService2 contractGenerationService2;

	@Autowired
	EntryDerivativePriceService2 entryDerivativePriceService2;

	@Autowired
	EntryofGradeCompositionService entryofGradeCompositionService;

	@Autowired
	RoDispatchService roDispatchService;

	@Autowired
	StateService stateList;

	@Autowired
	DistrictService districtService;

//	@Autowired
//	DistrictService districtService;

	@Autowired
	SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	PCSOReqLetterService genReqLetterService;

	@RequestMapping("/pcsoRequestLetter")
	public ModelAndView pcsoRequestLetter(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("PCSORequestLetter");
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("generatePCSORequest")
	public ModelAndView generatePCSORequestLetter(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ParseException {
		ModelAndView mv = new ModelAndView("PCSORequestLetter");
		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			mv = new ModelAndView("index");
			return mv;
		}

		PCSORequestLetter requestLetter = new PCSORequestLetter();

		String referenceno = request.getParameter("referenceno");
		double system_qty = Double.parseDouble(request.getParameter("sysAvailQty"));
		double req_qty = Double.parseDouble(request.getParameter("reqQty"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// String dateString = request.getParameter("date");
//		Date convertTodateDate = simpleDateFormat.parse(dateString);
//		String creation_date = simpleDateFormat.format(convertTodateDate);

		Date creationDateTime = new Date();
//		requestLetter.setCreationDateTime(creationDateTime);

		requestLetter.setReference_no(referenceno);
		requestLetter.setSys_avail_qty(system_qty);
		requestLetter.setReq_qty(req_qty);
		requestLetter.setCreation_date(creationDateTime);

		genReqLetterService.create(requestLetter);
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> Record created successfully.</div>\r\n" + "");

		return new ModelAndView(new RedirectView("pcsoRequestLetterList.obj"));
	}

	@RequestMapping("pcsoRequestLetterList")
	public ModelAndView requestList() {
		ModelAndView mv = new ModelAndView("PCSORequestLetterList");
		List<PCSORequestLetter> letters = genReqLetterService.getLetters();

		mv.addObject("letters", letters);

		return mv;
	}

	@RequestMapping("pcsoRequestDelete")
	public ModelAndView requestDelete(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ParseException {
		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}

		int id = Integer.parseInt(request.getParameter("reqId"));
		genReqLetterService.delete(id);
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> Record deleted successfully.</div>\r\n" + "");

		return new ModelAndView(new RedirectView("pcsoRequestLetterList.obj"));

	}

	// ---------------------------------------------------------
	// Entry Of PCSO
	// ---------------------------------------------------------
	@RequestMapping("entryofpcso")
	public ModelAndView EntryofpcsoModel(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("entryofpcso");
		String referenceno = request.getParameter("referenceno");
		if (referenceno != null) {
			String referencedate = request.getParameter("referencedate");
			String pcsodate = request.getParameter("pcsodate");
			mv.addObject("referenceno", referenceno);
			mv.addObject("referencedate", referencedate);
			mv.addObject("pcsodate", pcsodate);

		}
		final List<Object[]> allentryofpcsolist = this.pcsoentryservice.getAlldata();
		List<String> allRequestLetterRefNo = this.pcsoentryservice.getAllRequest();
		mv.addObject("entryofpcsolist", allentryofpcsolist);
		mv.addObject("allRefNo", allRequestLetterRefNo);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("entryofpcsosave")
	public ModelAndView saveUserMid(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("entryofpcsosave");
		if (username == null) {
			mv = new ModelAndView("index");
		}

		try {
			List<EntryofpcsoModel> ll = new ArrayList<EntryofpcsoModel>();
			int count = Integer.valueOf(request.getParameter("count"));
			String referenceno = request.getParameter("referenceno");
			String referencedate = request.getParameter("referencedate");
			String pcsodate = request.getParameter("pcsodate");
			String deliveryPeriod = request.getParameter("deliveryPeriod");
			String juteRatio = request.getParameter("juteRatio");
			String letterRef = request.getParameter("letterRefNo");
			Double pcsoQty = Double.parseDouble(request.getParameter("pcsoQty"));

//			EntryofpcsoModel entryofpcso = new EntryofpcsoModel();

			// set default fields one time and modify when its needed

			///////////////////////////////////////////////

			for (int c = 0; c < count; c++) {

				EntryofpcsoModel entryofpcsoCopy = new EntryofpcsoModel();
				entryofpcsoCopy.setCreated_date(new Date());
				entryofpcsoCopy.setDeliveryPeriod(deliveryPeriod);
				entryofpcsoCopy.setJuteRatio(juteRatio);
				entryofpcsoCopy.setLetterPath("/localsystem");
				entryofpcsoCopy.setLetterRef(letterRef);
				entryofpcsoCopy.setPcso_date(pcsodate);
				entryofpcsoCopy.setPcsoQty(pcsoQty);
				entryofpcsoCopy.setReference_date(referencedate);
				entryofpcsoCopy.setReference_no(referenceno);

				int millcode = Integer.parseInt(request.getParameter("millcode" + c));
				String millname = request.getParameter("millname" + c);
				Double tallocation = Double.parseDouble(request.getParameter("totalallocation" + c));
				if (tallocation != 0.0 && tallocation != null) {
					entryofpcsoCopy.setMill_code(millcode);
					entryofpcsoCopy.setMill_name(millname);
					entryofpcsoCopy.setAllocatedQty(tallocation);
					ll.add(entryofpcsoCopy);

				}

			}

			mv.addObject("entryofpcso", ll);
			mv.addObject("referenceno", referenceno);
			mv.addObject("referencedate", referencedate);
			mv.addObject("pcsodate", pcsodate);
			mv.addObject("letterRef", letterRef);
			mv.addObject("juteRatio", juteRatio);
			mv.addObject("deliveryPeriod", deliveryPeriod);
			mv.addObject("pcsoQty", pcsoQty);

		} catch (Exception e) {
			System.out.println(e);
		}

		return mv;
	}

	@RequestMapping("saveentryofpcsodata")
	public ModelAndView entryofpcsosave(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = (String) request.getSession().getAttribute("usrname");
		try {

			int count = Integer.valueOf(request.getParameter("count"));
			Date date = new Date();
			String referenceno = request.getParameter("referenceno");
			String referencedate = request.getParameter("referencedate");
			String pcsodate = request.getParameter("pcsodate");
			String sumoftotalallocation = request.getParameter("sumoftotalallocation");
			Double pcsoQty = Double.parseDouble(request.getParameter("pcsoQty"));

			String deliveryPeriod = request.getParameter("deliveryPeriod");
			String juteRatio = request.getParameter("juteRatio");
			String letterRef = request.getParameter("letterRefNo");

			for (int i = 0; i < count; i++) {

				// default values
				EntryofpcsoModel entryofpcso = new EntryofpcsoModel();
				entryofpcso.setDeliveryPeriod(deliveryPeriod);
				entryofpcso.setJuteRatio(juteRatio);
				entryofpcso.setLetterPath("/localsystem");
				entryofpcso.setLetterRef(letterRef);
				entryofpcso.setReference_no(referenceno);
				entryofpcso.setReference_date(referencedate);
				entryofpcso.setPcso_date(pcsodate);
				entryofpcso.setCreated_date(date);
				entryofpcso.setPcsoQty(pcsoQty);

				// dynamic values
				int millcode = Integer.parseInt(request.getParameter("millcode" + i));
				String millname = request.getParameter("millname" + i);
				Double tallocation = Double.parseDouble(request.getParameter("totalallocation" + i));
				entryofpcso.setMill_code(millcode);
				entryofpcso.setMill_name(millname);
				entryofpcso.setAllocatedQty(tallocation);
				pcsoentryservice.create(entryofpcso);
			}
			redirectAttributes.addFlashAttribute("msg",
					"<div class=\"alert alert-success\"><b>Success !</b> Record created successfully.</div>\r\n" + "");

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		if (username == null) {
			return new ModelAndView("index");
		}
		return new ModelAndView(new RedirectView("pcsolist.obj"));
	}

	@RequestMapping("pcsolist")
	public ModelAndView pcsolist(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("pcsolist");
		List<EntryofpcsoModel> pcso = pcsoentryservice.getAllPcso();
		mv.addObject("pcsolist", pcso);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("pcsoDelete")
	public ModelAndView pcsoDelete(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ParseException {
		String username = (String) request.getSession().getAttribute("usrname");

		int id = Integer.parseInt(request.getParameter("pcsorefid"));
		pcsoentryservice.delete(id);
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> List deleted successfully.</div>\r\n" + "");

		if (username == null) {
			return new ModelAndView("index");
		}
		return new ModelAndView(new RedirectView("pcsolist.obj"));

	}

	@RequestMapping("updatePcso")
	public ModelAndView updatePcso(HttpServletRequest request) throws ParseException {
		int refId = Integer.parseInt(request.getParameter("pcsorefid"));

		ModelAndView mv = new ModelAndView("editPcso");
		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			mv = new ModelAndView("index");
		}

		EntryofpcsoModel entryofpcso = pcsoentryservice.getPcso(refId);
		System.out.println(entryofpcso.toString());
		mv.addObject("pcso", entryofpcso);

		return mv;
	}

	@RequestMapping("updatesavePcso")
	public ModelAndView updatesavePcso(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ParseException {
		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}

		int refid = Integer.parseInt(request.getParameter("pcsorefid"));

		EntryofpcsoModel entryofpcsoModel = this.pcsoentryservice.getPcso(refid);

		Double totalallocation = Double.parseDouble(request.getParameter("totalallocation"));

		entryofpcsoModel.setAllocatedQty(totalallocation);

		pcsoentryservice.create(entryofpcsoModel);

		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> PCSO Record updated successfully.</div>\r\n" + "");
		return new ModelAndView(new RedirectView("pcsolist.obj"));
	}

	@RequestMapping("contractgenerationPCSOWise")
	public ModelAndView contractgenerationShow(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("contractgeneration");

		if (username == null) {
			mv = new ModelAndView("index");
			return mv;
		}

		List<String> pcso = pcsoentryservice.getAllDates();
		List<String> gradeCompositionLable = pcsoentryservice.getAllLables();
		mv.addObject("pcsoDates", (Object) pcso);
		mv.addObject("gradeCompositionLable", gradeCompositionLable);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "contractgenerationPcsoWiseSave", method = { RequestMethod.POST })
	public void saveContractGenerationPcsoWise(HttpServletRequest request, @RequestBody Map<String, Object> requestBody)
			throws IOException, ParseException {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("contractgeneration");
//		if (username == null) {
//			return new ModelAndView("index");	
//		}

		List<Map<String, String>> millDetails = (List<Map<String, String>>) requestBody.get("millDetails");

		List<String> pcsoDate = (List<String>) requestBody.get("pcsoDate");
		int refId = (Integer) request.getSession().getAttribute("userId");

		// current financial year

		LocalDate obj = LocalDate.now();
		// LocalDate obj = LocalDate.of(2020, 1, 8)

		int currentyear = obj.getYear();
		int nextyear = 0;
		int month = obj.getMonthValue();

		if (month >= 7) {
			nextyear = currentyear + 1;
		} else {
			nextyear = currentyear;
			currentyear -= 1;
		}

		String cropYear = currentyear + "-" + nextyear;

		//

		String joinDate = String.join(",", pcsoDate);

		String contractIdn = (String) requestBody.get("contractIdn");
		String contractQty = (String) requestBody.get("contractQty");
		String contractdate = (String) requestBody.get("contractdate");
		String deliveryType = (String) requestBody.get("deliveryType");
		String gradeComp = (String) requestBody.get("gradeComp");
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		for (Map<String, String> millDetail : millDetails) {
			Contractgeneration contractgeneration = new Contractgeneration();
			Double contractedValue = 0.0;
			int millCode = Integer.parseInt(millDetail.get("millCode"));

			contractgeneration.setPcso_date(joinDate);
			contractgeneration.setContract_identification_no(contractIdn);
			contractgeneration.setContract_qty(contractQty);
			contractgeneration.setContract_date(contractdate);
			contractgeneration.setDelivery_type(deliveryType);
			contractgeneration.setContract_no("JCI/" + millCode + "/" + cropYear + "/" + contractIdn);	
			contractgeneration.setContract_value(Double.parseDouble(millDetail.get("contractedValue")));
			contractgeneration.setCreated_date(new Date());
			contractgeneration.setCreated_by(refId);
			contractgeneration.setGrade_composition(gradeComp);
			contractgeneration.setMill_code(millCode);
			contractgeneration.setMill_name(millDetail.get("millName"));
			contractgeneration.setMill_qty((Double.parseDouble(millDetail.get("Qty"))));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate currentDate = LocalDate.now();
			LocalDate tenDaysAfter = currentDate.plusDays(10); // Add 10 days
			contractgeneration.setPayment_duedate(tenDaysAfter.format(formatter));
			System.out.println();
			System.out.println();
			System.out.println(contractgeneration.toString());
			System.out.println();
			System.out.println();
			contractGenerationService2.create(contractgeneration);
		}

//		return mv;
	}

//
//	@ResponseBody
//	@RequestMapping(value = "pcsoDates", method = RequestMethod.GET)
//	public List<String> pcsoDates(final HttpServletRequest request) {
//		List<String> pcso = pcsoentryservice.getAllDates();
//		return pcso;
//	}
//

	@ResponseBody
	@RequestMapping(value = "pcso_details", method = RequestMethod.GET)
	public String pcso_details(final HttpServletRequest request) {

		String outerArray = request.getParameter("pcso_dates");
		String gradeComp = request.getParameter("gradeComp");
		String deliveryType = request.getParameter("deliveryType");

		outerArray = outerArray.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "'");

		final String[] dateArray = outerArray.split(",");
		final List<String> list = Arrays.asList(dateArray);

		ModelAndView pcso = contractGenerationService2.pcso_details(list, gradeComp, deliveryType);
		Gson gson = new Gson();
		return gson.toJson(pcso);
	}

	//////////////////////////////////////////////////////////////////
	// Divyam code ends
	//////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////
	// Pradeep code Starts
	//////////////////////////////////////////////////////////////////

	// ---------------------------------------------------------
	// Entry Of Derivative Price
	// ---------------------------------------------------------

	@RequestMapping("entry_derivativeprice")
	public ModelAndView ViewEDPrice(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		List<StateList> Liststate = stateList.getAll();
		ModelAndView mv = new ModelAndView("entry_derivativeprice2");
		mv.addObject("Liststate", Liststate);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("editentryderivativeprice")
	public ModelAndView editEDP(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("editentryderivativeprice2");
		int der_id = Integer.parseInt(request.getParameter("der_id"));
		EntryDerivativePrice entryDerivativePrice = this.entryDerivativePriceService2.findEDPBYId(der_id);
		mv.addObject("derivativePrice", entryDerivativePrice);
		List<StateList> liststate = stateList.getAll();
		mv.addObject("Liststate", liststate);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("updateEDPrice")
	public ModelAndView updateEDC(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = (String) request.getSession().getAttribute("usrname");
		try {
			int der_id = Integer.parseInt(request.getParameter("der_id"));
			// System.out.println("===id model==>>>>>>>>>>>=== " + der_id);
			EntryDerivativePrice entryDerivativePrice = this.entryDerivativePriceService2.findEDPBYId(der_id);

			String grade1 = request.getParameter("grade1");
			String grade2 = request.getParameter("grade2");
			String grade3 = request.getParameter("grade3");
			String grade4 = request.getParameter("grade4");
			String grade5 = request.getParameter("grade5");
			String grade6 = request.getParameter("grade6");

			entryDerivativePrice.setDer_id(der_id);

			entryDerivativePrice.setGrade1(grade1 != "" ? grade1 : "0");
			entryDerivativePrice.setGrade2(grade2 != "" ? grade2 : "0");
			entryDerivativePrice.setGrade3(grade3 != "" ? grade3 : "0");
			entryDerivativePrice.setGrade4(grade4 != "" ? grade4 : "0");
			entryDerivativePrice.setGrade5(grade5 != "" ? grade5 : "0");
			entryDerivativePrice.setGrade6(grade6 == null || grade6 == "" ? "0" : grade6);

			Date date = new Date();
			SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateFormater = simpleDateTimeFormat.format(date);
			Date updateDate = null;

			try {
				updateDate = simpleDateTimeFormat.parse(dateFormater);
			} catch (Exception e) {
				// TODO: handle exception
			}

			entryDerivativePrice.setUpdateDateAndTime(updateDate);
			this.entryDerivativePriceService2.update(entryDerivativePrice);
			redirectAttributes.addFlashAttribute("msg",
					"<div class=\"alert alert-success\"><b>Success !</b> Record saved successfully.</div>\r\n" + "");
		} catch (Exception e) {
			System.out.println(e);
		}
		if (username == null) {
			return new ModelAndView("index");
		}
		return new ModelAndView(new RedirectView("entryderivativepricelist.obj"));
	}

	@RequestMapping("entryderivativepricelist")
	public ModelAndView EntryDerivativePrice(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		List<EntryDerivativePrice> edp = entryDerivativePriceService2.getAllEDP();
		ModelAndView mv = new ModelAndView("entryderivativepricelist2");
		mv.addObject("edp", edp);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("entryderivativepriceDelete")
	public ModelAndView entryderivativepriceDelete(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ParseException {
		String username = (String) request.getSession().getAttribute("usrname");
		// ModelAndView mv = new ModelAndView("entryderivativepricelist2");
		int id = Integer.parseInt(request.getParameter("der_id"));
		entryDerivativePriceService2.delete(id);
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> List deleted successfully.</div>\r\n" + "");
		if (username == null) {
			return new ModelAndView("index");
		}
		return new ModelAndView(new RedirectView("entryderivativepricelist.obj"));

	}

	@RequestMapping("saveEDPrice")
	public ModelAndView derivativePriceHandler(HttpServletRequest request) {

		String crop_year = request.getParameter("crop_year");
		String delivery_type = request.getParameter("delivery_type");
		String state_id = request.getParameter("state");
		String state_code = stateList.getStateCode(state_id);
		String state_name = stateList.find(Integer.parseInt(state_id)).getState_name();
		int refId = (Integer) request.getSession().getAttribute("userId");

		Date date = new Date();
		SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormater = simpleDateTimeFormat.format(date);
		Date created_Date = null;

		try {
			created_Date = simpleDateTimeFormat.parse(dateFormater);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String status = "0";

		String[] district_ids = request.getParameterValues("district");

		for (String s : district_ids) {
			List<String> disStrings = Arrays.asList(s.split("-"));
			String idString = disStrings.get(0);
			String nameString = disStrings.get(1);
			String codeString = disStrings.get(2);

			EntryDerivativePrice entryDerivativePriceTossa = new EntryDerivativePrice();
			EntryDerivativePrice entryDerivativePriceWhite = new EntryDerivativePrice();
			EntryDerivativePrice entryDerivativePriceMesta = new EntryDerivativePrice();
			EntryDerivativePrice entryDerivativePriceBimli = new EntryDerivativePrice();

			entryDerivativePriceTossa.setCrop_year(crop_year);
			entryDerivativePriceTossa.setDelivery_type(delivery_type);
			entryDerivativePriceTossa.setState_id(state_id);
			entryDerivativePriceTossa.setState_name(state_name);
			entryDerivativePriceTossa.setState(state_code);
			entryDerivativePriceTossa.setDistrict_id(idString);
			entryDerivativePriceTossa.setDistrict_name(nameString);
			entryDerivativePriceTossa.setDistrict(codeString);
			entryDerivativePriceTossa.setCreated_by(refId);
			entryDerivativePriceTossa.setCreation_date(created_Date);
			entryDerivativePriceTossa.setStatus(status);

			entryDerivativePriceWhite.setCrop_year(crop_year);
			entryDerivativePriceWhite.setDelivery_type(delivery_type);
			entryDerivativePriceWhite.setState_id(state_id);
			entryDerivativePriceWhite.setState_name(state_name);
			entryDerivativePriceWhite.setState(state_code);
			entryDerivativePriceWhite.setDistrict_id(idString);
			entryDerivativePriceWhite.setDistrict_name(nameString);
			entryDerivativePriceWhite.setDistrict(codeString);
			entryDerivativePriceWhite.setCreated_by(refId);
			entryDerivativePriceWhite.setCreation_date(created_Date);
			entryDerivativePriceWhite.setStatus(status);

			entryDerivativePriceMesta.setCrop_year(crop_year);
			entryDerivativePriceMesta.setDelivery_type(delivery_type);
			entryDerivativePriceMesta.setState_id(state_id);
			entryDerivativePriceMesta.setState_name(state_name);
			entryDerivativePriceMesta.setState(state_code);
			entryDerivativePriceMesta.setDistrict_id(idString);
			entryDerivativePriceMesta.setDistrict_name(nameString);
			entryDerivativePriceMesta.setDistrict(codeString);
			entryDerivativePriceMesta.setCreated_by(refId);
			entryDerivativePriceMesta.setCreation_date(created_Date);
			entryDerivativePriceMesta.setStatus(status);

			entryDerivativePriceBimli.setCrop_year(crop_year);
			entryDerivativePriceBimli.setDelivery_type(delivery_type);
			entryDerivativePriceBimli.setState_id(state_id);
			entryDerivativePriceBimli.setState_name(state_name);
			entryDerivativePriceBimli.setState(state_code);
			entryDerivativePriceBimli.setDistrict_id(idString);
			entryDerivativePriceBimli.setDistrict_name(nameString);
			entryDerivativePriceBimli.setDistrict(codeString);
			entryDerivativePriceBimli.setCreated_by(refId);
			entryDerivativePriceBimli.setCreation_date(created_Date);
			entryDerivativePriceBimli.setStatus(status);

			// System.out.println(entryDerivativePrice.toString());

			String tgr1 = request.getParameter("tgr1") != "" ? request.getParameter("tgr1") : "0";
			String tgr2 = request.getParameter("tgr2") != "" ? request.getParameter("tgr2") : "0";
			;
			String tgr3 = request.getParameter("tgr3") != "" ? request.getParameter("tgr3") : "0";
			;
			String tgr4 = request.getParameter("tgr4") != "" ? request.getParameter("tgr4") : "0";
			;
			String tgr5 = request.getParameter("tgr5") != "" ? request.getParameter("tgr5") : "0";
			;
			String wgr1 = request.getParameter("wgr1") != "" ? request.getParameter("wgr1") : "0";
			;
			String wgr2 = request.getParameter("wgr2") != "" ? request.getParameter("wgr2") : "0";
			;
			String wgr3 = request.getParameter("wgr3") != "" ? request.getParameter("wgr3") : "0";
			;
			String wgr4 = request.getParameter("wgr4") != "" ? request.getParameter("wgr4") : "0";
			;
			String wgr5 = request.getParameter("wgr5") != "" ? request.getParameter("wgr5") : "0";
			;
			String mgr1 = request.getParameter("mgr1") != "" ? request.getParameter("mgr1") : "0";
			;
			String mgr2 = request.getParameter("mgr2") != "" ? request.getParameter("mgr2") : "0";
			;
			String mgr3 = request.getParameter("mgr3") != "" ? request.getParameter("mgr3") : "0";
			;
			String mgr4 = request.getParameter("mgr4") != "" ? request.getParameter("mgr4") : "0";
			;
			String mgr5 = request.getParameter("mgr5") != "" ? request.getParameter("mgr5") : "0";
			;
			String mgr6 = request.getParameter("mgr6") != "" ? request.getParameter("mgr6") : "0";
			;
			String bgr1 = request.getParameter("bgr1") != "" ? request.getParameter("bgr1") : "0";
			;
			String bgr2 = request.getParameter("bgr2") != "" ? request.getParameter("bgr2") : "0";
			;
			String bgr3 = request.getParameter("bgr3") != "" ? request.getParameter("bgr3") : "0";
			;
			String bgr4 = request.getParameter("bgr4") != "" ? request.getParameter("bgr4") : "0";
			;
			String bgr5 = request.getParameter("bgr5") != "" ? request.getParameter("bgr5") : "0";
			;
			String bgr6 = request.getParameter("bgr6") != "" ? request.getParameter("bgr6") : "0";
			;

			entryDerivativePriceTossa.setJute_variety("Tossa (New)");
			entryDerivativePriceTossa.setGrade1(tgr1);
			entryDerivativePriceTossa.setGrade2(tgr2);
			entryDerivativePriceTossa.setGrade3(tgr3);
			entryDerivativePriceTossa.setGrade4(tgr4);
			entryDerivativePriceTossa.setGrade5(tgr5);
			entryDerivativePriceTossa.setGrade6("0");

			entryDerivativePriceWhite.setJute_variety("White (New)");
			entryDerivativePriceWhite.setGrade1(wgr1);
			entryDerivativePriceWhite.setGrade2(wgr2);
			entryDerivativePriceWhite.setGrade3(wgr3);
			entryDerivativePriceWhite.setGrade4(wgr4);
			entryDerivativePriceWhite.setGrade5(wgr5);
			entryDerivativePriceWhite.setGrade6("0");

			entryDerivativePriceMesta.setJute_variety("Mesta");
			entryDerivativePriceMesta.setGrade1(mgr1);
			entryDerivativePriceMesta.setGrade2(mgr2);
			entryDerivativePriceMesta.setGrade3(mgr3);
			entryDerivativePriceMesta.setGrade4(mgr4);
			entryDerivativePriceMesta.setGrade5(mgr5);
			entryDerivativePriceMesta.setGrade6(mgr6);

			entryDerivativePriceBimli.setJute_variety("Bimli");
			entryDerivativePriceBimli.setGrade1(bgr1);
			entryDerivativePriceBimli.setGrade2(bgr2);
			entryDerivativePriceBimli.setGrade3(bgr3);
			entryDerivativePriceBimli.setGrade4(bgr4);
			entryDerivativePriceBimli.setGrade5(bgr5);
			entryDerivativePriceBimli.setGrade6(bgr6);

			entryDerivativePriceService2.create(entryDerivativePriceTossa);
			entryDerivativePriceService2.create(entryDerivativePriceWhite);
			entryDerivativePriceService2.create(entryDerivativePriceMesta);
			entryDerivativePriceService2.create(entryDerivativePriceBimli);

		}

		return new ModelAndView(new RedirectView("entryderivativepricelist.obj"));
	}

	// ---------------------------------------------------------
	// Entry Of Grade Composition
	// ---------------------------------------------------------

	@RequestMapping("entry_gradecomposition")
	public ModelAndView ViewGradeComposition(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView("entryofgradecomposition");
		if (username == null) {
			mv = new ModelAndView("index");
		}

		List<Object> allJuteCombination = entryofGradeCompositionService.getAllJuteCombination();
		mv.addObject("allJuteVariety", allJuteCombination);
		return mv;
	}

	@RequestMapping("saveGradeComp")
	public ModelAndView saveGradeComposition(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		String username = (String) request.getSession().getAttribute("usrname");
		ModelAndView mv = new ModelAndView(new RedirectView("entry_gradecomposition.obj"));
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> Added successfully.</div>\r\n" + "");
		if (username == null) {
			mv = new ModelAndView("index");
		}

		String lableName = request.getParameter("labelname");
		String cropYear = request.getParameter("crop_year");
		int size = Integer.parseInt(request.getParameter("size"));
		Double availableQty = Double.parseDouble(request.getParameter("available_qty"));
		int refId = (Integer) request.getSession().getAttribute("userId");

		Date date = new Date();
		SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormater = simpleDateTimeFormat.format(date);
		Date created_Date = null;

		try {
			created_Date = simpleDateTimeFormat.parse(dateFormater);
		} catch (Exception e) {
			// TODO: handle exception
		}

		for (int i = 1; i <= size; i++) {
			EntryofGradeCompositionModel entryofGradeCompositionModel = new EntryofGradeCompositionModel();
			String system_compositionString = request.getParameter("system" + i);
			String proposed_compositionString = request.getParameter("proposed" + i);
			Double ProposedValue = Double.parseDouble(proposed_compositionString);
			Double systemValue = Double.parseDouble(system_compositionString);
			entryofGradeCompositionModel.setJute_combination(request.getParameter("variety" + i));
			entryofGradeCompositionModel.setSystem_composition(systemValue);
			entryofGradeCompositionModel.setProposed_composition(ProposedValue);
			entryofGradeCompositionModel.setCrop_year(cropYear);
			entryofGradeCompositionModel.setAvailable_qty(availableQty);
			entryofGradeCompositionModel.setLabel_name(lableName);
			entryofGradeCompositionModel.setCreated_by(refId);
			entryofGradeCompositionModel.setCreated_date(created_Date);

			entryofGradeCompositionService.create(entryofGradeCompositionModel);
		}

		return mv;
	}

	@RequestMapping("entryofgradecompositiondelete")
	public ModelAndView entryofgradecompositiondelete(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ParseException {
		String username = (String) request.getSession().getAttribute("usrname");
		// ModelAndView mv = new ModelAndView("entryderivativepricelist2");
		BigInteger gradeId = new BigInteger(request.getParameter("grade_id"));
		entryofGradeCompositionService.delete(gradeId);
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b> List deleted successfully.</div>\r\n" + "");
		if (username == null) {
			return new ModelAndView("index");
		}
		return new ModelAndView(new RedirectView("entrygradecompositionlist.obj"));

	}

	@RequestMapping("entrygradecompositionlist")
	public ModelAndView EntryGradeComposition(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		List<EntryofGradeCompositionModel> egc = entryofGradeCompositionService.getAllEGC();

		ModelAndView mv = new ModelAndView("entryofgradecompositionlist");
		mv.addObject("egc", egc);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("editentryofgradecomposition")
	public ModelAndView EditeEntryOfGradeComposition(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		List<Object> allJuteCombination = entryofGradeCompositionService.getAllJuteCombination();
		BigInteger gradeId = new BigInteger(request.getParameter("grade_id"));
//		BigInteger gradeId = new BigInteger(request.getParameter("grade_id"));
		EntryofGradeCompositionModel egc = (EntryofGradeCompositionModel) entryofGradeCompositionService.Edit(gradeId);

		ModelAndView mv = new ModelAndView("editGradeComposition");
		mv.addObject("allJuteVariety", allJuteCombination);
		mv.addObject("egc", egc);
		if (username == null) {
			mv = new ModelAndView("index");
		}
		return mv;
	}

	@RequestMapping("updateGradeComp")
	public ModelAndView updateEGC(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}

		Date date = new Date();
		SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormater = simpleDateTimeFormat.format(date);
		Date updatedDate = null;

		try {
			updatedDate = simpleDateTimeFormat.parse(dateFormater);
		} catch (Exception e) {
			// TODO: handle exception
		}

		BigInteger grade_id = new BigInteger(request.getParameter("grade_id"));
		String juteVariety = (String) request.getParameter("juteVariety");
		double newProposedComposition = Double.parseDouble(request.getParameter("grade"));
		EntryofGradeCompositionModel entryofGradeCompositionModel = entryofGradeCompositionService
				.getSpecificGradeComposition(grade_id);
		entryofGradeCompositionModel.setProposed_composition(newProposedComposition);
		entryofGradeCompositionModel.setUpdateDateAndTime(updatedDate);
		entryofGradeCompositionModel.setJute_combination(juteVariety);
		entryofGradeCompositionService.update(entryofGradeCompositionModel);
		return new ModelAndView(new RedirectView("entrygradecompositionlist.obj"));
	}

	// ---------------------------------------------------------
	// Ro Dispatch Instruction
	// ---------------------------------------------------------

	@RequestMapping("roDispatchInstruction")
	public ModelAndView viewRoDispatcher(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}
		List<String> loadAllDpc = roDispatchService.loadAllDpc();
		List<String> loadAllDiNo = roDispatchService.loadAllDiNo();
		// int countAvaiResult = roDispatchService.getCountOfAvailableEntries();
		ModelAndView mv = new ModelAndView("roDispatchInstruction");
		mv.addObject("loadAllDpc", loadAllDpc);
		mv.addObject("loadAllDiNo", loadAllDiNo);
		// mv.addObject("count",countAvaiResult);

		return mv;
	}

	@RequestMapping("saveRoDi")
	public ModelAndView saveRoDispatch(HttpServletRequest request, @ModelAttribute RoDispatchModel roDispatchModel,
			RedirectAttributes redirectAttributes) throws ParseException {

		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}

		Date date = new Date();
		roDispatchModel.setCreationDate(date);
		String lastDateOFShipmentString = request.getParameter("lastDateOfShipment");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outPutDate = new SimpleDateFormat("dd-MM-yyyy");

		Date fomatedDate = dateFormat.parse(lastDateOFShipmentString);
		String finalStringDate = outPutDate.format(fomatedDate);
		roDispatchModel.setLastDateOfShipment(finalStringDate);
		System.out.println(roDispatchModel.toString());
		this.roDispatchService.create(roDispatchModel);
		redirectAttributes.addFlashAttribute("msg",
				"<div class=\"alert alert-success\"><b>Success !</b>data successfully.</div>\r\n" + "");

		return new ModelAndView(new RedirectView("roDispatchList.obj"));
	}

	@RequestMapping("roDispatchList")
	public ModelAndView roDiList(HttpServletRequest request) {

		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}

		List<RoDispatchModel> allDi = roDispatchService.getAllRoDi();

		ModelAndView mv = new ModelAndView("diRoList");
		mv.addObject("roDiList", allDi);
		return mv;
	}

	// ---------------------------------------------------------
	// Generation Of Credit Notes
	// ---------------------------------------------------------

	@RequestMapping("generationOfCreditNote")
	public ModelAndView generationOfCreditNotes(HttpServletRequest request) {

		String username = (String) request.getSession().getAttribute("usrname");
		if (username == null) {
			return new ModelAndView("index");
		}

		return new ModelAndView("generationOfCreditNote");

	}

	// for specific district values

	@ResponseBody
	@RequestMapping(value = { "pIconForSelected" }, method = { RequestMethod.GET })
	public String getFoosBySimplePathForSelected(final HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("usrname");
		final String state = request.getParameter("F_District");
		String state_code = "";
		if (state != "") {
			state_code = stateList.getStateCode(state);
		}
		final String crop_year = request.getParameter("cropYear");
		final String delivery_type = request.getParameter("delivery_type");
//			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//			System.out.println(state_code + " " + state  + " " + crop_year + " " + delivery_type + " ");
//			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		final Gson gson = new Gson();
		if (state_code == "")
			return "entry_derivativeprice.obj";
		return gson.toJson((Object) districtService.getSpecificDistricts(state_code, crop_year, delivery_type));
	}

	@ResponseBody
	@RequestMapping(value = { "isValidateLabel" }, method = { RequestMethod.GET })
	public String validateEmail(final HttpServletRequest request) {
		final String labelName = request.getParameter("label");
		return this.entryofGradeCompositionService.isValidLabelName(labelName) + "";
	}

	@ResponseBody
	@RequestMapping(value = { "isValid_Identification_No" }, method = { RequestMethod.GET })
	public String Valid_Identification_No(final HttpServletRequest request) {
		final String contractIdn = request.getParameter("contractIdn");
		return this.contractGenerationService2.isValidContractIdn(contractIdn) + "";
	}

	@ResponseBody
	@RequestMapping(value = { "getContractDetails" }, method = { RequestMethod.GET })
	public String getContractDetails(final HttpServletRequest request) {
		final String diNo = request.getParameter("diNo");
		List<Object> loadFullContractDetails = this.roDispatchService.loadFullContractDetails(diNo);
		int count = this.roDispatchService.getCountOfAvailableEntries(diNo);
		Map<String, Object> mpMap = new HashMap<>();
		mpMap.put("contractDetails", loadFullContractDetails);
		mpMap.put("count", count);
		Gson gson = new Gson();

		return gson.toJson(mpMap);

	}

	@ResponseBody
	@RequestMapping(value = { "getRequestLetterDetails" }, method = { RequestMethod.GET })
	public String getRequestLetterDetails(final HttpServletRequest request) {
		final String refNo = request.getParameter("refNo");
		Object loadAllDetailsOfLetter = this.pcsoentryservice.loadAllDetailsOfLetter(refNo);

		Gson gson = new Gson();
		return gson.toJson(loadAllDetailsOfLetter);

	}

}

//	  ******************************************>>>>>>>>Code ends here<<<<<<<<<<*********************************************************
//	  ******************************************>>>>>>>>Code ends here<<<<<<<<<<*********************************************************
//	  ******************************************>>>>>>>>Code ends here<<<<<<<<<<*********************************************************
//	  ******************************************>>>>>>>>Code ends here<<<<<<<<<<*********************************************************
