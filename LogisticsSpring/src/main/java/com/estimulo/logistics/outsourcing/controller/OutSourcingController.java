package com.estimulo.logistics.outsourcing.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;
import com.estimulo.logistics.outsourcing.serviceFacade.OutSourcingServiceFacade;

public class OutSourcingController extends MultiActionController {


	// serviceFacade 참조변수 선언
	private OutSourcingServiceFacade outSourcingServiceFacade;
	public void setOutSourcingServiceFacade(OutSourcingServiceFacade outSourcingServiceFacade) {
		this.outSourcingServiceFacade = outSourcingServiceFacade;
	}

	// GSON 라이브러리
	public ModelAndView searchOutSourcingList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String customerCode = request.getParameter("customerCode");
		String itemCode = request.getParameter("itemCode");
		String materialStatus = request.getParameter("materialStatus");
		ArrayList<OutSourcingTO> outSourcingList;
		try {
			outSourcingList = outSourcingServiceFacade.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);
			map.put("outSourcingList", outSourcingList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
}
