package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;

public interface StockApplicationService {
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr);
	
	public HashMap<String, Object> changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount);
	public ArrayList<StockChartTO> getStockChart();
}
