package com.estimulo.system.basicInfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.basicInfo.serviceFacade.BasicInfoServiceFacade;
import com.estimulo.system.basicInfo.serviceFacade.BasicInfoServiceFacadeImpl;
import com.estimulo.system.basicInfo.to.FinancialAccountAssociatesTO;
import com.estimulo.system.common.exception.DataAccessException;
import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FinancialAccountAssociatesController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(FinancialAccountAssociatesController.class);

	// serviceFacade 참조변수 선언
	BasicInfoServiceFacade cooperatorSF = BasicInfoServiceFacadeImpl.getInstance();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	public ModelAndView searchFinancialAccountAssociatesList(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("FinancialAccountAssociatesController :  searchFinancialAccountAssociatesList 시작");
		}

		String searchCondition = request.getParameter("searchCondition");

		String workplaceCode = request.getParameter("workplaceCode");

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {

			out = response.getWriter();

			financialAccountAssociatesList = cooperatorSF.getFinancialAccountAssociatesList(searchCondition,
					workplaceCode);

			map.put("gridRowJson", financialAccountAssociatesList);
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
			logger.debug("FinancialAccountAssociatesController :  searchFinancialAccountAssociatesList 종료");
		}
		return null;
	}

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("FinancialAccountAssociatesController : batchListProcess 시작");
		}

		String batchList = request.getParameter("batchList");

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = gson.fromJson(batchList,
				new TypeToken<ArrayList<FinancialAccountAssociatesTO>>() {
				}.getType());

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {

			out = response.getWriter();

			HashMap<String, Object> resultMap = cooperatorSF
					.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);

			map.put("result", resultMap);
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
			logger.debug("FinancialAccountAssociatesController : batchListProcess 종료");
		}
		return null;
	}

}
