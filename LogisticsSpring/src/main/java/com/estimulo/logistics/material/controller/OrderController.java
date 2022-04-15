package com.estimulo.logistics.material.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.material.serviceFacade.MaterialServiceFacade;
import com.estimulo.logistics.material.to.OrderInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OrderController extends MultiActionController {

	private MaterialServiceFacade MaterialSF;
	public void setMaterialServiceFacade (MaterialServiceFacade MaterialSF) {
		this.MaterialSF=MaterialSF;
	}
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	public ModelAndView checkOrderInfo(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> resultMap = new HashMap<>();
		String orderNoListStr = request.getParameter("orderNoList");
		
		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());
		try {
			
			resultMap=MaterialSF.checkOrderInfo(orderNoArr);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",resultMap);
		
	}
	public ModelAndView getOrderList(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		HashMap<String, Object> resultMap = new HashMap<>();

		try {
			
			resultMap = MaterialSF.getOrderList(startDate, endDate);

		} catch (Exception e1) {
			
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		
		return new ModelAndView("jsonView",resultMap);
		
	}

	public ModelAndView openOrderDialog(HttpServletRequest request, HttpServletResponse response) {

		String mrpNoListStr = request.getParameter("mrpGatheringNoList");
	/*	ArrayList<String> mrpNoArr = gson.fromJson(mrpNoListStr, new TypeToken<ArrayList<String>>() {
		}.getType()); //제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
	*/
		System.out.println("@@@@@@"+mrpNoListStr);
		HashMap<String,Object> resultMap = new HashMap<>();

		try {
			
			resultMap = MaterialSF.getOrderDialogInfo(mrpNoListStr);
		//	resultMap = MaterialSF.getOrderDialogInfo(mrpNoArr);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		
		return new ModelAndView("jsonView",resultMap);
		
	}

	public ModelAndView showOrderInfo(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {

			ArrayList<OrderInfoTO> orderInfoList = MaterialSF.getOrderInfoList(startDate,endDate);
			map.put("gridRowJson", orderInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}

		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView searchOrderInfoListOnDelivery(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();;

		try {

			ArrayList<OrderInfoTO> orderInfoListOnDelivery = MaterialSF.getOrderInfoListOnDelivery();
			map.put("gridRowJson", orderInfoListOnDelivery);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView order(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringNoListStr = request.getParameter("mrpGatheringNoList");
		HashMap<String, Object> resultMap = new HashMap<>();

		 ArrayList<String> mrpGaNoArr = gson.fromJson(mrpGatheringNoListStr , new TypeToken<ArrayList<String>>() {
	      }.getType());
		 	//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		try {
///////////////////////////////시작부
			resultMap = MaterialSF.order(mrpGaNoArr);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",resultMap);
	}

	public ModelAndView optionOrder(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			String itemCode = request.getParameter("itemCode");
			String itemAmount = request.getParameter("itemAmount");

			resultMap = MaterialSF.optionOrder(itemCode, itemAmount);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",resultMap);
	}


}
