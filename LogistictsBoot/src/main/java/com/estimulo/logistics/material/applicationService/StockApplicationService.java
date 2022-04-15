package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;

import org.springframework.ui.ModelMap;

import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;

public interface StockApplicationService {
	
	public ArrayList<StockTO> getStockList();
	
	public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate);
	
	public ModelMap warehousing(ArrayList<String> orderNoArr);
	
	public ModelMap changeSafetyAllowanceAmount(String itemCode, String itemName,
			String safetyAllowanceAmount);
	public ArrayList<StockChartTO> getStockChart();
}
