package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.DeliveryInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/sales/*")
public class DeliveryController  {

	@Autowired
	private SalesServiceFacade salesServiceFacade;
	private ModelMap modelMap = new ModelMap();
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환
	
	@RequestMapping(value="/searchDeliveryInfoList.do", method=RequestMethod.POST)
	public ModelMap searchDeliveryInfoList(HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<DeliveryInfoTO> deliveryInfoList = salesServiceFacade.getDeliveryInfoList();

			modelMap.put("gridRowJson", deliveryInfoList);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		
		return modelMap;
	}

	// batchListProcess
	@RequestMapping(value="/batchListProcess.do", method=RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		try {

			List<DeliveryInfoTO> deliveryTOList = gson.fromJson(batchList, new TypeToken<ArrayList<DeliveryInfoTO>>() {
			}.getType());


			HashMap<String, Object> resultMap = salesServiceFacade.batchDeliveryListProcess(deliveryTOList);

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/searchDeliverableContractList.do", method=RequestMethod.POST)
	public ModelMap searchDeliverableContractList(HttpServletRequest request, HttpServletResponse response) {


		String ableContractInfo =request.getParameter("ableContractInfo");
		
		try {
			
			HashMap<String,String> ableSearchConditionInfo = gson.fromJson(ableContractInfo, new TypeToken<HashMap<String,String>>() {
			}.getType());
			
			ArrayList<ContractInfoTO> deliverableContractList = null;
			
			deliverableContractList = salesServiceFacade.getDeliverableContractList(ableSearchConditionInfo);
			
			modelMap.put("gridRowJson", deliverableContractList);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/deliver.do", method=RequestMethod.POST)
	public ModelMap deliver(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String,Object> resultMap = new HashMap<>();
		String contractDetailNo = request.getParameter("contractDetailNo");

		try {

			resultMap = salesServiceFacade.deliver(contractDetailNo);
			modelMap.put("gridRowJson", resultMap);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}

		return modelMap;
	}

}
