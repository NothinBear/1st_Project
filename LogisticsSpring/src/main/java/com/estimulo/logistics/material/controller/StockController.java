package com.estimulo.logistics.material.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.material.serviceFacade.MaterialServiceFacade;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StockController extends MultiActionController {

	private MaterialServiceFacade MaterialSF;
	public void setMaterialServiceFacade (MaterialServiceFacade MaterialSF) {
		this.MaterialSF=MaterialSF;
	}
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchStockList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			ArrayList<StockTO> stockList = MaterialSF.getStockList();

			map.put("gridRowJson", stockList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}

		return new ModelAndView("jsonView",map);
	}

	public ModelAndView searchStockLogList(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : searchStockList 시작");
		}

		HashMap<String, Object> map = new HashMap<>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		try {

			ArrayList<StockLogTO> stockLogList = MaterialSF.getStockLogList(startDate,endDate);

			map.put("gridRowJson", stockLogList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView warehousing(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : warehousing 시작");
		}

		String orderNoListStr = request.getParameter("orderNoList");

		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());	

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			resultMap = MaterialSF.warehousing(orderNoArr);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",resultMap);
	}
	
	public ModelAndView safetyAllowanceAmountChange(HttpServletRequest request, HttpServletResponse response) {

		String itemCode  = request.getParameter("itemCode");
		String itemName  = request.getParameter("itemName");
		String safetyAllowanceAmount  = request.getParameter("safetyAllowanceAmount");

		System.out.println("itemCode:"+itemCode+"itemName:"+itemName+"safetyAllowanceAmount:"+safetyAllowanceAmount);

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			resultMap = MaterialSF.changeSafetyAllowanceAmount(itemCode,itemName,safetyAllowanceAmount);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",resultMap);
	}
	
	public ModelAndView getStockChart(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("StockController : stockChart 시작");
		}

		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<StockChartTO> stockList = MaterialSF.getStockChart();

			map.put("gridRowJson", stockList);
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
