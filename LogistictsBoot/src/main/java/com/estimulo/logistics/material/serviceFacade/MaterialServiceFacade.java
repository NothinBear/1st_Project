package com.estimulo.logistics.material.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;
import com.estimulo.logistics.material.to.OrderInfoTO;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;



public interface MaterialServiceFacade {

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition);
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable();
	
	public ModelMap batchBomListProcess(ArrayList<BomTO> batchBomList);
	
	public ModelMap getOrderList(String startDate, String endDate);
	
	public ModelMap getOrderDialogInfo(String mrpNoArr);
	
	public ModelMap order(ArrayList<String> mrpGaNoArr);
	
	public ModelMap optionOrder(String itemCode, String itemAmount);
	
	public ModelMap warehousing(ArrayList<String> orderNoArr);
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate,String endDate);

	public ModelMap changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount);

	public ModelMap checkOrderInfo(ArrayList<String> orderNoArr);
	
	public ArrayList<StockChartTO> getStockChart();
}
