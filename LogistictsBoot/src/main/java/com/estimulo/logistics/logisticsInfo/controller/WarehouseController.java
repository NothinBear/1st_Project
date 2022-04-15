package com.estimulo.logistics.logisticsInfo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/logisticsInfo/*")
public class WarehouseController  {

	@Autowired
	private LogisticsInfoServiceFacade logisticsInfoServiceFacade;
	private ModelMap modelMap = new ModelMap();
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚
	
	@RequestMapping(value = "/warehouseInfo.do", method = RequestMethod.POST)
	public ModelMap getWarehouseList(HttpServletRequest request, HttpServletResponse response) {

		try {
			ArrayList<WarehouseTO> WarehouseTOList = logisticsInfoServiceFacade.getWarehouseInfoList();
			modelMap.put("gridRowJson", WarehouseTOList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());
		} 

		return modelMap;
	}
	//구현안됨
	public ModelMap modifyWarehouseInfo(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		try {
			WarehouseTO WarehouseTO = gson.fromJson(batchList, WarehouseTO.class);
			logisticsInfoServiceFacade.modifyWarehouseInfo(WarehouseTO);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());
		} 
		return modelMap;
	}
	//구현 안됨
	public ModelMap findLastWarehouseCode(HttpServletRequest request, HttpServletResponse response){

		try {
			String warehouseCode = logisticsInfoServiceFacade.findLastWarehouseCode();
			modelMap.put("lastWarehouseCode", warehouseCode);
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
