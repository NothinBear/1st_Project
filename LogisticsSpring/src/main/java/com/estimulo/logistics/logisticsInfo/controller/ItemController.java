package com.estimulo.logistics.logisticsInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import com.estimulo.logistics.logisticsInfo.to.ItemInfoTO;
import com.estimulo.logistics.logisticsInfo.to.ItemTO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ItemController extends MultiActionController {
	
	private LogisticsInfoServiceFacade logisticsInfoServiceFacade;
	
	public void setLogisticsInfoServiceFacade(LogisticsInfoServiceFacade logisticsInfoServiceFacade) {
	
		this.logisticsInfoServiceFacade=logisticsInfoServiceFacade;
	}
	// GSON �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 JSON 蹂��솚

	public ModelAndView searchItem(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String itemClassification = request.getParameter("itemClassification");
		String itemGroupCode = request.getParameter("itemGroupCode");
		String minPrice = request.getParameter("minPrice");
		String maxPrice = request.getParameter("maxPrice");

		ArrayList<ItemInfoTO> itemInfoList = null;
		String[] paramArray = null;

		HashMap<String, Object> map = new HashMap<>();

		try {

			switch (searchCondition) {

			case "ALL":
				paramArray = null;
				break;
			case "ITEM_CLASSIFICATION":
				paramArray = new String[] { itemClassification };
				break;
			case "ITEM_GROUP_CODE":
				paramArray = new String[] { itemGroupCode };
				break;
			case "STANDARD_UNIT_PRICE":
				paramArray = new String[] { minPrice, maxPrice };
				break;

			}

			itemInfoList = logisticsInfoServiceFacade.getItemInfoList(searchCondition, paramArray);

			map.put("gridRowJson", itemInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}  
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView getStandardUnitPrice(HttpServletRequest request, HttpServletResponse response) {

		String itemCode = request.getParameter("itemCode");

		int price = 0;
		
		HashMap<String, Object> map = new HashMap<>();

		try {

			price = logisticsInfoServiceFacade.getStandardUnitPrice(itemCode);

			map.put("gridRowJson", price);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView getStandardUnitPriceBox(HttpServletRequest request, HttpServletResponse response) {

		String itemCode = request.getParameter("itemCode");
	

		int price = 0;
		
		HashMap<String, Object> map = new HashMap<>();

		try {
			price = logisticsInfoServiceFacade.getStandardUnitPriceBox(itemCode);

			map.put("gridRowJson", price);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<ItemTO> itemTOList = gson.fromJson(batchList, new TypeToken<ArrayList<ItemTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();

		try {

			HashMap<String, Object> resultMap = logisticsInfoServiceFacade.batchItemListProcess(itemTOList);

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

}
