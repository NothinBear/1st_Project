package com.estimulo.logistics.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class StockDAOImpl extends IBatisSupportDAO implements StockDAO {

		@SuppressWarnings("unchecked")
		@Override
	      public ArrayList<StockTO> selectStockList() {
			return (ArrayList<StockTO>) getSqlMapClientTemplate().queryForList("stock.selectStockList");
	      }

		@SuppressWarnings("unchecked")
		@Override
		public ArrayList<StockLogTO> selectStockLogList(String startDate, String endDate) {

			HashMap<String, String> map = new HashMap<>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			return (ArrayList<StockLogTO>) getSqlMapClientTemplate().queryForList("stock.selectStockLogList", map);
		}

		@Override
		public HashMap<String,Object> warehousing(String orderNoList) {
			HashMap<String, String> map = new HashMap<>();
			map.put("orderNoList", orderNoList);

			getSqlMapClientTemplate().update("stock.warehousing", map);

			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("errorCode", map.get("ERROR_CODE"));
			resultMap.put("errorMsg", map.get("ERROR_MSG"));

			return resultMap;
		}
		@Override
		public HashMap<String, Object> updatesafetyAllowance(String itemCode, String itemName, String safetyAllowanceAmount) {
			HashMap<String, String> map = new HashMap<>();
			map.put("itemCode",itemCode);
			map.put("itemName", itemName);
			map.put("safetyAllowanceAmount", safetyAllowanceAmount);

			getSqlMapClientTemplate().update("stock.updatesafetyAllowance", map);

			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("errorCode", map.get("ERROR_CODE"));
			resultMap.put("errorMsg", map.get("ERROR_MSG"));

			return resultMap;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public ArrayList<StockChartTO> selectStockChart() {
				return (ArrayList<StockChartTO>) getSqlMapClientTemplate().queryForList("stock.selectStockChart");
		}
}
