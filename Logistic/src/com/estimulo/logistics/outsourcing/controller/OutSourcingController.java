package com.estimulo.logistics.outsourcing.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.outsourcing.serviceFacade.OutSourcingServiceFacadeImpl;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;
import com.estimulo.logistics.outsourcing.serviceFacade.OutSourcingServiceFacade;
import com.estimulo.system.common.exception.DataAccessException;
import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OutSourcingController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(OutSourcingController.class);

	// serviceFacade 참조변수 선언
	private static OutSourcingServiceFacade OutSourcingServiceFacade = OutSourcingServiceFacadeImpl.getInstance();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchOutSourcingList(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("OutSourcingController : searchOutSourcingList 시작");
		}
		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String customerCode = request.getParameter("customerCode");
		String itemCode = request.getParameter("itemCode");
		String materialStatus = request.getParameter("materialStatus");
		ArrayList<OutSourcingTO> outSourcingList;
		try {
			out = response.getWriter();
			outSourcingList = OutSourcingServiceFacade.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);
			map.put("outSourcingList", outSourcingList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("OutSourcingController : searchOutSourcingList 종료");
		}
		return null;
	}
}
