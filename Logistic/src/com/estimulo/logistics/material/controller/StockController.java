package com.estimulo.logistics.material.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.material.serviceFacade.MaterialServiceFacade;
import com.estimulo.logistics.material.serviceFacade.MaterialServiceFacadeImpl;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;
import com.estimulo.system.common.exception.DataAccessException;
import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StockController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(StockController.class);

	// serviceFacade 참조변수 선언
	MaterialServiceFacade purchaseSF = MaterialServiceFacadeImpl.getInstance();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchStockList(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : searchStockList 시작");
		}

		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<StockTO> stockList = purchaseSF.getStockList();

			map.put("gridRowJson", stockList);
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
			logger.debug("StockController : searchStockList 종료");
		}
		return null;
	}

	public ModelAndView searchStockLogList(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : searchStockList 시작");
		}

		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		try {
			out = response.getWriter();

			ArrayList<StockLogTO> stockLogList = purchaseSF.getStockLogList(startDate,endDate);

			map.put("gridRowJson", stockLogList);
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
			logger.debug("StockController : searchStockList 종료");
		}
		return null;
	}
	
	public ModelAndView warehousing(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : warehousing 시작");
		}

		String orderNoListStr = request.getParameter("orderNoList");

		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());	

		HashMap<String, Object> resultMap = new HashMap<>();
		
		PrintWriter out = null;

		try {
			out = response.getWriter();

			resultMap = purchaseSF.warehousing(orderNoArr);

		} catch (IOException e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			resultMap.put("errorCode", -2);
			resultMap.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(resultMap));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : warehousing 종료");
		}
		return null;
	}
	
	public ModelAndView safetyAllowanceAmountChange(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : safetyAllowanceAmountChange 시작");
		}

		String itemCode  = request.getParameter("itemCode");
		String itemName  = request.getParameter("itemName");
		String safetyAllowanceAmount  = request.getParameter("safetyAllowanceAmount");

		System.out.println("itemCode:"+itemCode+"itemName:"+itemName+"safetyAllowanceAmount:"+safetyAllowanceAmount);

		HashMap<String, Object> resultMap = new HashMap<>();
		
		PrintWriter out = null;

		try {
			out = response.getWriter();

			resultMap = purchaseSF.changeSafetyAllowanceAmount(itemCode,itemName,safetyAllowanceAmount);

		} catch (IOException e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			resultMap.put("errorCode", -2);
			resultMap.put("errorMsg", e2.getMessage());

		} finally {
			
			out.println(gson.toJson(resultMap));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("StockController : safetyAllowanceAmountChange 종료");
		}
		return null;
	}
	
	public ModelAndView getStockChart(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("StockController : stockChart 시작");
		}

		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<StockChartTO> stockList = purchaseSF.getStockChart();

			map.put("gridRowJson", stockList);
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
			logger.debug("StockController : stockChart 종료");
		}
		return null;
	}
}
