package com.estimulo.logistics.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;

public interface StockDAO {
	
	public ArrayList<StockTO> selectStockList();
	
	public ArrayList<StockLogTO> selectStockLogList(String startDate,String endDate);
	
	public HashMap<String,Object> warehousing(String orderNoList);
	
	public HashMap<String, Object> updatesafetyAllowance(String itemCode, String itemName,
			String safetyAllowanceAmount);
	
	public ArrayList<StockChartTO> selectStockChart();
}
