package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.material.dao.StockDAO;
import com.estimulo.logistics.material.dao.StockDAOImpl;
import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;
import com.estimulo.system.common.exception.DataAccessException;

public class StockApplicationServiceImpl implements StockApplicationService{

		// SLF4J logger
		private static Logger logger = LoggerFactory.getLogger(StockApplicationServiceImpl.class);

		// 싱글톤
		private static StockApplicationService instance = new StockApplicationServiceImpl();

		private StockApplicationServiceImpl() {
		}

		public static StockApplicationService getInstance() {
		
			if (logger.isDebugEnabled()) {
				logger.debug("@ StockApplicationService 객체접근");
				}

				return instance;
			}

		// DAO 참조변수 선언
		private static StockDAO stockDAO = StockDAOImpl.getInstance();

		@Override
		public ArrayList<StockTO> getStockList() {

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationService : getStockList 시작");
			}

			ArrayList<StockTO> stockList = null;

			try {

				stockList = stockDAO.selectStockList();

			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationService : getStockList 끝");
			}
			return stockList;
		}
	
		@Override
		public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate) {

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationService : getStockLogList 시작");
			}

			ArrayList<StockLogTO> StockLogList = null;

			try {
				
				StockLogList = stockDAO.selectStockLogList(startDate,endDate);

			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationService : getStockLogList 끝");
			}
			return StockLogList;
		}

		@Override
		public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationServiceImpl : warehousing 시작");
			}
			
            HashMap<String,Object> resultMap = null;
            
			try {
				
				String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
				
				resultMap = stockDAO.warehousing(orderNoList);
				
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationServiceImpl : warehousing 종료");
			}
			
        	return resultMap;
			
		}
		
		@Override
		public HashMap<String, Object> changeSafetyAllowanceAmount(String itemCode, String itemName,
				String safetyAllowanceAmount) {


			if (logger.isDebugEnabled()) {
				logger.debug("OrderApplicationServiceImpl : ChangesafetyAllowance 시작");
			}

			HashMap<String, Object> resultMap = null;

			try {

				resultMap = stockDAO.updatesafetyAllowance(itemCode,itemName,safetyAllowanceAmount);

			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("OrderApplicationServiceImpl : ChangesafetyAllowance 종료");
			}
			return resultMap;
		}
		
		@Override
		public ArrayList<StockChartTO> getStockChart() {

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationService : stockChart 시작");
			}

			ArrayList<StockChartTO> stockChart = null;

			try {

				stockChart = stockDAO.selectStockChart();

			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("StockApplicationService : stockChart 끝");
			}
			return stockChart;
		}
		
}
