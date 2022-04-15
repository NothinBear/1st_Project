package com.estimulo.logistics.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.OrderInfoTO;

public interface OrderDAO {
	
	 public HashMap<String,Object> getOrderList(String startDate, String endDate);
	 
	 public HashMap<String,Object> getOrderDialogInfo(String mrpNoList);
	 
	 public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	 
	 public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	 public HashMap<String,Object> order(String mpsNoList);	 
	 
	 public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);

	public HashMap<String,Object> updateOrderInfo(String orderNoArr);
}
