package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.dao.OrderDAO;
import com.estimulo.logistics.material.to.OrderInfoTO;
public class OrderApplicationServiceImpl implements OrderApplicationService {

	// DAO 참조변수 선언
	private OrderDAO orderDAO;
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO=orderDAO;
	}
	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

        HashMap<String,Object> resultMap = null;
        
			resultMap = orderDAO.getOrderList(startDate, endDate);
			
		return resultMap;
	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpNoArr) {

        HashMap<String,Object> resultMap = null;

		/* 	gson.fromJson 썼을 때
		 * 	String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
			resultMap = orderDAO.getOrderDialogInfo(mrpNoList);
		*/	
			String mrpNoList = mrpNoArr.replace("[", "").replace("]", "").replace("\"", "");
			resultMap = orderDAO.getOrderDialogInfo(mrpNoList);

		return resultMap;
	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {

		HashMap<String,Object> resultMap = null;

			String mpsNoList = mrpGaNoArr.toString().replace("[", "").replace("]", "");
			resultMap = orderDAO.order(mpsNoList);

    	return resultMap;
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		// TODO Auto-generated method stub
		
        HashMap<String,Object> resultMap = null;

			resultMap = orderDAO.optionOrder(itemCode, itemAmount);

    	return resultMap;
		
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {

		ArrayList<OrderInfoTO> orderInfoListOnDelivery = null;

			orderInfoListOnDelivery = orderDAO.getOrderInfoListOnDelivery();
			
		return orderInfoListOnDelivery;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {

		ArrayList<OrderInfoTO> orderInfoList  = null;

			orderInfoList = orderDAO.getOrderInfoList(startDate,endDate);
			
		return orderInfoList;
		
	}

	@Override
	public HashMap<String,Object> checkOrderInfo(ArrayList<String> orderNoArr) {
		// TODO Auto-generated method stub
		
		HashMap<String,Object> resultMap = new HashMap<>();
		
			String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
			resultMap = orderDAO.updateOrderInfo(orderNoList);

		return resultMap;
	}
}
