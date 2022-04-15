package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.dao.StockDAO;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;

public class StockApplicationServiceImpl implements StockApplicationService{

		// DAO 참조변수 선언
		private StockDAO stockDAO;
		public void setStockDAO(StockDAO stockDAO) {
			this.stockDAO=stockDAO;
		}
		@Override
		public ArrayList<StockTO> getStockList() {

			ArrayList<StockTO> stockList = null;

				stockList = stockDAO.selectStockList();

			return stockList;
		}
	
		@Override
		public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate) {

			ArrayList<StockLogTO> StockLogList = null;

				StockLogList = stockDAO.selectStockLogList(startDate,endDate);
				
			return StockLogList;
		}

		@Override
		public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {
			
            HashMap<String,Object> resultMap = null;
				
				String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
				
				resultMap = stockDAO.warehousing(orderNoList);
				
        	return resultMap;
			
		}
		
		@Override
		public HashMap<String, Object> changeSafetyAllowanceAmount(String itemCode, String itemName,
				String safetyAllowanceAmount) {

			HashMap<String, Object> resultMap = null;

				resultMap = stockDAO.updatesafetyAllowance(itemCode,itemName,safetyAllowanceAmount);

			return resultMap;
		}
		
		@Override
		public ArrayList<StockChartTO> getStockChart() {

			ArrayList<StockChartTO> stockChart = null;

				stockChart = stockDAO.selectStockChart();

			return stockChart;
		}
		
}
