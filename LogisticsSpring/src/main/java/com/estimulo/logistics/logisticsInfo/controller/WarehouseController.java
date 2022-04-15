package com.estimulo.logistics.logisticsInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class WarehouseController extends MultiActionController {

	// serviceFacade 참조변수 선언
	private LogisticsInfoServiceFacade logisticsInfoServiceFacade;

	public void setLogisticsInfoServiceFacade(LogisticsInfoServiceFacade logisticsInfoServiceFacade) {
	
		this.logisticsInfoServiceFacade=logisticsInfoServiceFacade;
	}

	// GSON �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚

	public ModelAndView getWarehouseList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();	
		try {
			ArrayList<WarehouseTO> WarehouseTOList = logisticsInfoServiceFacade.getWarehouseInfoList();
			map.put("gridRowJson", WarehouseTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 

		return new ModelAndView("jsonView",map);
	}

	
	public ModelAndView modifyWarehouseInfo(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		HashMap<String, Object> map = new HashMap<>();
		try {
			WarehouseTO WarehouseTO = gson.fromJson(batchList, WarehouseTO.class);
			logisticsInfoServiceFacade.modifyWarehouseInfo(WarehouseTO);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		} 
		return new ModelAndView("jsonView",map);
	}
	
	
	public ModelAndView findLastWarehouseCode(HttpServletRequest request, HttpServletResponse response){

		HashMap<String, Object> map = new HashMap<>();
		try {
			String warehouseCode = logisticsInfoServiceFacade.findLastWarehouseCode();
			map.put("lastWarehouseCode", warehouseCode);
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
