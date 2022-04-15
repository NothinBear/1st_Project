package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.DeliveryInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DeliveryController extends MultiActionController {

	// serviceFacade 참조변수 선언
	private SalesServiceFacade salesServiceFacade;
	
	public void setSalesServiceFacade(SalesServiceFacade salesServiceFacade) {
		this.salesServiceFacade = salesServiceFacade;
	}
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	public ModelAndView searchDeliveryInfoList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<DeliveryInfoTO> deliveryInfoList = salesServiceFacade.getDeliveryInfoList();

			map.put("gridRowJson", deliveryInfoList);
			map.put("errorCode", 0);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		
		return new ModelAndView("jsonView",map);
	}

	// batchListProcess

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		HashMap<String, Object> map = new HashMap<>();

		try {

			List<DeliveryInfoTO> deliveryTOList = gson.fromJson(batchList, new TypeToken<ArrayList<DeliveryInfoTO>>() {
			}.getType());


			HashMap<String, Object> resultMap = salesServiceFacade.batchDeliveryListProcess(deliveryTOList);

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView searchDeliverableContractList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();

		String ableContractInfo =request.getParameter("ableContractInfo");
		
		try {
			
			HashMap<String,String> ableSearchConditionInfo = gson.fromJson(ableContractInfo, new TypeToken<HashMap<String,String>>() {
			}.getType());
			
			ArrayList<ContractInfoTO> deliverableContractList = null;
			
			deliverableContractList = salesServiceFacade.getDeliverableContractList(ableSearchConditionInfo);
			
			map.put("gridRowJson", deliverableContractList);
			map.put("errorCode", 0);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView deliver(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String,Object> resultMap = new HashMap<>();
		

		String contractDetailNo = request.getParameter("contractDetailNo");

		try {

			resultMap = salesServiceFacade.deliver(contractDetailNo);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}

		return new ModelAndView("jsonView",resultMap);
	}

}
