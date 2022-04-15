package com.estimulo.logistics.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.OrderDialogTempTO;
import com.estimulo.logistics.material.to.OrderInfoTO;
import com.estimulo.logistics.material.to.OrderTempTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class OrderDAOImpl extends IBatisSupportDAO implements OrderDAO {
	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		@SuppressWarnings("unchecked")
		ArrayList<OrderTempTO> orderList = (ArrayList<OrderTempTO>)getSqlMapClientTemplate().queryForList("order.getOrderList", map);
		
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson",orderList);
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG")); 
			
		return resultMap;
	}
	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpNoList) {

		HashMap<String, String> map = new HashMap<>();
		map.put("mrpNoList", mrpNoList);	
		
		@SuppressWarnings("unchecked")
		ArrayList<OrderDialogTempTO> orderDialogInfoList = (ArrayList<OrderDialogTempTO>)getSqlMapClientTemplate().queryForList("order.getOrderDialogInfo", map);
		
		HashMap<String,Object> resultMap = new HashMap<>();			
		resultMap.put("gridRowJson", orderDialogInfoList);
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG")); 	

		return resultMap;
	}
	@Override
	public HashMap<String,Object> order(String mpsNoList) {

		HashMap<String, String> map = new HashMap<>();
		map.put("mpsNoList", mpsNoList);
			
        getSqlMapClientTemplate().update("order.order", map);
        
        HashMap<String,Object> resultMap = new HashMap<>();
    	resultMap.put("errorCode",map.get("ERROR_CODE"));
    	resultMap.put("errorMsg", map.get("ERROR_MSG")); 
        	
		return resultMap;		
	}
	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {

		HashMap<String, String> map = new HashMap<>();
		map.put("itemCode", itemCode);
		map.put("itemAmount", itemAmount);	
			
        getSqlMapClientTemplate().update("order.optionOrder", map);
			
        HashMap<String,Object> resultMap = new HashMap<>();
    	resultMap.put("errorCode",map.get("ERROR_CODE"));
    	resultMap.put("errorMsg", map.get("ERROR_MSG")); 
        	
		return resultMap;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		return (ArrayList<OrderInfoTO>)getSqlMapClientTemplate().queryForList("order.getOrderInfoListOnDelivery");	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);	
		
		return (ArrayList<OrderInfoTO>)getSqlMapClientTemplate().queryForList("order.getOrderInfoList", map);			
	}
	@Override
	public HashMap<String,Object> updateOrderInfo(String orderNoList) {
		// TODO Auto-generated method stub
		HashMap<String,Object> resultMap = new HashMap<>();
		HashMap<String,String> params = new HashMap<>();
		params.put("orderNoList", orderNoList);
		getSqlMapClientTemplate().update("order.updateOrderInfo",params);
		resultMap.put("errorCode",params.get("ERROR_CODE"));
    	resultMap.put("errorMsg", params.get("ERROR_MSG")); 
		return resultMap;
	}
}
