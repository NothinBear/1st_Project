package com.estimulo.logistics.material.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.logistics.material.serviceFacade.MaterialServiceFacade;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/material/*")
public class StockController  {
	@Autowired
	private MaterialServiceFacade MaterialSF;
	private ModelMap modelMap = new ModelMap();
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	@RequestMapping(value="/searchStockList.do" , method=RequestMethod.POST)
	public ModelMap searchStockList(HttpServletRequest request, HttpServletResponse response) {


		try {
			ArrayList<StockTO> stockList = MaterialSF.getStockList();

			modelMap.put("gridRowJson", stockList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}

		return modelMap;
	}
	@RequestMapping(value="/searchStockLogList.do" , method=RequestMethod.POST)
	public ModelMap searchStockLogList(HttpServletRequest request, HttpServletResponse response) {


		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		try {

			ArrayList<StockLogTO> stockLogList = MaterialSF.getStockLogList(startDate,endDate);

			modelMap.put("gridRowJson", stockLogList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping(value="/warehousing.do", method=RequestMethod.POST)
	public ModelMap warehousing(HttpServletRequest request, HttpServletResponse response) {

		String orderNoListStr = request.getParameter("orderNoList");

		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());	

		try {

			modelMap = MaterialSF.warehousing(orderNoArr);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping(value="/safetyAllowanceAmountChange.do", method=RequestMethod.POST)
	public ModelMap safetyAllowanceAmountChange(HttpServletRequest request, HttpServletResponse response) {

		String itemCode  = request.getParameter("itemCode");
		String itemName  = request.getParameter("itemName");
		String safetyAllowanceAmount  = request.getParameter("safetyAllowanceAmount");

		System.out.println("itemCode:"+itemCode+"itemName:"+itemName+"safetyAllowanceAmount:"+safetyAllowanceAmount);

		try {

			modelMap = MaterialSF.changeSafetyAllowanceAmount(itemCode,itemName,safetyAllowanceAmount);
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	@RequestMapping(value="/getStockChart.do", method=RequestMethod.POST)
	public ModelMap getStockChart(HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<StockChartTO> stockList = MaterialSF.getStockChart();

			modelMap.put("gridRowJson", stockList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
}
