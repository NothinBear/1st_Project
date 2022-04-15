package com.estimulo.logistics.material.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.applicationService.BomApplicationService;
import com.estimulo.logistics.material.applicationService.OrderApplicationService;
import com.estimulo.logistics.material.applicationService.StockApplicationService;
import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;
import com.estimulo.logistics.material.to.OrderInfoTO;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;


public class MaterialServiceFacadeImpl implements MaterialServiceFacade {
	
	private BomApplicationService bomAS;
	
	public void setBomApplicationService (BomApplicationService bomAS) {
		this.bomAS=bomAS;
	}
	
	private OrderApplicationService orderAS;
	public void setOrderApplicationService (OrderApplicationService orderAS) {
		this.orderAS=orderAS;
	}
	private StockApplicationService stockAS;
	public void setStockApplicationService (StockApplicationService stockAS) {
		this.stockAS=stockAS;
	}
	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode,
			String itemClassificationCondition) {

		ArrayList<BomDeployTO> bomDeployList = null;

			bomDeployList = bomAS.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

		return bomDeployList;
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		ArrayList<BomInfoTO> bomInfoList = null;

			bomInfoList = bomAS.getBomInfoList(parentItemCode);

		return bomInfoList;
	}

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

        HashMap<String,Object> resultMap = null;

		resultMap = orderAS.getOrderList(startDate, endDate);

		return resultMap;
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = null;

			allItemWithBomRegisterAvailable = bomAS.getAllItemWithBomRegisterAvailable();
			
		return allItemWithBomRegisterAvailable;
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		HashMap<String, Object> resultMap = null;


			resultMap = bomAS.batchBomListProcess(batchBomList);
			 
		return resultMap;

	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpNoArr) {

        HashMap<String,Object> resultMap = null;
        
			resultMap = orderAS.getOrderDialogInfo(mrpNoArr);
					
		return resultMap;

	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {

		
        HashMap<String,Object> resultMap = null;
		
			resultMap = orderAS.order(mrpGaNoArr);
		
    	return resultMap;
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		// TODO Auto-generated method stub
		
        HashMap<String,Object> resultMap = null;

			resultMap = orderAS.optionOrder(itemCode, itemAmount);
		
    	return resultMap;
		
	}

	@Override
	public ArrayList<StockTO> getStockList() {


		ArrayList<StockTO> stockList = null;

			stockList = stockAS.getStockList();

		return stockList;
	}

	@Override
	public ArrayList<StockLogTO> getStockLogList(String startDate, String endDate) {

		ArrayList<StockLogTO> stockLogList = null;

			stockLogList = stockAS.getStockLogList(startDate, endDate);

		return stockLogList;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {

		ArrayList<OrderInfoTO> orderInfoListOnDelivery = null;

			orderInfoListOnDelivery = orderAS.getOrderInfoListOnDelivery();

		return orderInfoListOnDelivery;

	}

	@Override
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {

        HashMap<String,Object> resultMap = null;
		
			resultMap = stockAS.warehousing(orderNoArr);

    	return resultMap;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {

		ArrayList<OrderInfoTO> orderInfoList  = null;

		return orderInfoList;

	}

	@Override
	public HashMap<String, Object> changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount) {
		
		HashMap<String, Object> resultMap = null;

			resultMap = stockAS.changeSafetyAllowanceAmount(itemCode, itemName, safetyAllowanceAmount);
			
		return resultMap;
	}

	@Override
	public HashMap<String,Object> checkOrderInfo(ArrayList<String> orderNoArr) {
		// TODO Auto-generated method stub
	
		HashMap<String, Object> resultMap = null;
		
			resultMap = orderAS.checkOrderInfo(orderNoArr);
			
		return resultMap;
	}
	
	@Override
	public ArrayList<StockChartTO> getStockChart() {

		ArrayList<StockChartTO> stockChart  = null;

			stockChart = stockAS.getStockChart();
			
		return stockChart;

	}
}
