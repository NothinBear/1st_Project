package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.OrderInfoTO;

public interface OrderApplicationService {
	
	public HashMap<String,Object> getOrderList(String startDate, String endDate);

	public HashMap<String,Object> getOrderDialogInfo(String mrpNoArr);

	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr);
	
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	public HashMap<String,Object> checkOrderInfo(ArrayList<String> orderNoArr);

}
