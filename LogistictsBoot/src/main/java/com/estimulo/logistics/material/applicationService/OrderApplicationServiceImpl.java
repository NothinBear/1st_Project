package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.logistics.material.mapper.OrderDAO;
import com.estimulo.logistics.material.to.OrderInfoTO;

@Component
public class OrderApplicationServiceImpl implements OrderApplicationService {
	@Autowired
	private OrderDAO orderDAO;

	@Override
	public ModelMap getOrderList(String startDate, String endDate) {

		HashMap<String, String> param = new HashMap<>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		orderDAO.getOrderList(param);
		ModelMap modelMap = new ModelMap();
		modelMap.put("gridRowJson", param.get("RESULT"));
		modelMap.put("errorCode", param.get("ERROR_CODE"));
		modelMap.put("errorMsg", param.get("ERROR_MSG"));
		
		return modelMap;
	}

	@Override
	public ModelMap getOrderDialogInfo(String mrpNoArr) {

			String mrpNoList = mrpNoArr.replace("[", "").replace("]", "").replace("\"", "");
			HashMap<String, String> param = new HashMap<>();
			param.put("mrpNoList", mrpNoList);
			orderDAO.getOrderDialogInfo(param);
			
			ModelMap modelMap = new ModelMap();
			modelMap.put("gridRowJson", param.get("RESULT"));
			modelMap.put("errorCode", param.get("ERROR_CODE"));
			modelMap.put("errorMsg", param.get("ERROR_MSG"));
			
		return modelMap;
	}

	@Override
	public ModelMap order(ArrayList<String> mrpGaNoArr) {
			
			String mpsNoList = mrpGaNoArr.toString().replace("[", "").replace("]", "");
			HashMap<String, String> param = new HashMap<>();
			param.put("mpsNoList", mpsNoList);
			orderDAO.order(param);
			ModelMap modelMap = new ModelMap();
			modelMap.put("errorCode", param.get("ERROR_CODE"));
			modelMap.put("errorMsg", param.get("ERROR_MSG"));
			
    	return modelMap;
		
	}

	@Override
	public ModelMap optionOrder(String itemCode, String itemAmount) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> param = new HashMap<>();
		param.put("itemCode", itemCode);
		param.put("itemAmount", itemAmount);

		orderDAO.optionOrder(param);

		ModelMap modelMap = new ModelMap();
		modelMap.put("errorCode", param.get("ERROR_CODE"));
		modelMap.put("errorMsg", param.get("ERROR_MSG"));
		
    	return modelMap;
		
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {

		ArrayList<OrderInfoTO> orderInfoListOnDelivery = null;

			orderInfoListOnDelivery = orderDAO.getOrderInfoListOnDelivery();
			
		return orderInfoListOnDelivery;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {

		HashMap<String, String> param = new HashMap<>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
			
		return  orderDAO.getOrderInfoList(param);
		
	}

	@Override
	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr) {
		// TODO Auto-generated method stub
		
		String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
		HashMap<String, String> param = new HashMap<>();
		param.put("orderNoList", orderNoList);

		orderDAO.updateOrderInfo(param);
		
		ModelMap modelMap = new ModelMap();
		modelMap.put("errorCode", param.get("ERROR_CODE"));
		modelMap.put("errorMsg", param.get("ERROR_MSG"));
	
		return modelMap; 
	}
}
