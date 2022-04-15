package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;

import org.springframework.ui.ModelMap;

import com.estimulo.logistics.material.to.OrderInfoTO;

public interface OrderApplicationService {
	
	public ModelMap getOrderList(String startDate, String endDate);

	public ModelMap getOrderDialogInfo(String mrpNoArr);

	public ModelMap order(ArrayList<String> mrpGaNoArr);
	
	public ModelMap optionOrder(String itemCode, String itemAmount);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr);

}
