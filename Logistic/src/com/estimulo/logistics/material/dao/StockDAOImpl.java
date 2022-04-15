package com.estimulo.logistics.material.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.material.to.StockChartTO;
import com.estimulo.logistics.material.to.StockLogTO;
import com.estimulo.logistics.material.to.StockTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

import oracle.jdbc.internal.OracleTypes;

public class StockDAOImpl implements StockDAO {

		// SLF4J logger
		private static Logger logger = LoggerFactory.getLogger(StockDAOImpl.class);	
			
		// 싱글톤
		private static StockDAO instance = new StockDAOImpl();

		private StockDAOImpl() {
		}

		public static StockDAO getInstance() {
				
			if (logger.isDebugEnabled()) {
				logger.debug("@ StockDAOImpl 객체접근");
			}
			
			return instance;
		}

		// 참조변수 선언
		private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();

		@Override
	      public ArrayList<StockTO> selectStockList() {
	         if (logger.isDebugEnabled()) {
	            logger.debug("StockDAOImpl : selectStockList 시작");
	         }

	         Connection conn = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;

	         ArrayList<StockTO> stockList = new ArrayList<StockTO>();

	         try {
	            conn = dataSourceTransactionManager.getConnection();

	            StringBuffer query = new StringBuffer();

	            query.append("SELECT * FROM STOCK order by item_code");
	            pstmt = conn.prepareStatement(query.toString());
	            rs = pstmt.executeQuery();

	            StockTO bean = null;
	            
	            while (rs.next()) {

	               bean = new StockTO();
	               bean.setWarehouseCode(rs.getString("WAREHOUSE_CODE"));
	               bean.setItemCode(rs.getString("ITEM_CODE"));
	               bean.setItemName(rs.getString("ITEM_NAME"));
	               bean.setUnitOfStock(rs.getString("UNIT_OF_STOCK"));
	               bean.setSafetyAllowanceAmount(rs.getString("SAFETY_ALLOWANCE_AMOUNT"));
	               bean.setStockAmount(rs.getString("STOCK_AMOUNT"));
	               bean.setOrderAmount(rs.getString("ORDER_AMOUNT"));
	               bean.setInputAmount(rs.getString("INPUT_AMOUNT"));
	               bean.setDeliveryAmount(rs.getString("DELIVERY_AMOUNT"));
	               bean.setTotalStockAmount(rs.getString("TOTAL_STOCK_AMOUNT"));
	               
	               stockList.add(bean);
	            }

	            return stockList;

	         } catch (Exception sqle) {

	            throw new DataAccessException(sqle.getMessage());

	         } finally {

	            dataSourceTransactionManager.close(pstmt, rs);

	         }
	      }

		@Override
		public ArrayList<StockLogTO> selectStockLogList(String startDate, String endDate) {
			if (logger.isDebugEnabled()) {
				logger.debug("StockDAOImpl : selectStockLogList 시작");
			}

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			ArrayList<StockLogTO> StockLogList = new ArrayList<StockLogTO>();

			try {
				conn = dataSourceTransactionManager.getConnection();

				StringBuffer query = new StringBuffer();
				System.out.println(startDate);
				System.out.println(endDate);
				query.append("select * from stock_log \r\n" + 
						"where to_date(log_date,'YYYY-MM-DD HH24:MI:SS') \r\n" + 
						"between to_date(?,'YYYY-MM-DD') \r\n" + 
						"AND to_date(?,'YYYY-MM-DD') \r\n" + 
						"order by log_date desc");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, startDate);
				pstmt.setString(2, endDate);
				rs = pstmt.executeQuery();

				StockLogTO bean = null;
				
				while (rs.next()) {

					bean = new StockLogTO();
					bean.setCause(rs.getString("CAUSE"));
					bean.setEffect(rs.getString("EFFECT"));
					bean.setLogDate(rs.getString("LOG_DATE"));
					bean.setItemCode(rs.getString("ITEM_CODE"));
					bean.setItemName(rs.getString("ITEM_NAME"));
					bean.setAmount(rs.getString("AMOUNT"));
					bean.setReason(rs.getString("REASON"));					
					
					StockLogList.add(bean);
				}

				return StockLogList;

			} catch (Exception sqle) {

				throw new DataAccessException(sqle.getMessage());

			} finally {

				dataSourceTransactionManager.close(pstmt, rs);

			}
		}

		@Override
		public HashMap<String,Object> warehousing(String orderNoList) {
			
			if (logger.isDebugEnabled()) {
		         logger.debug("StockDAOImpl : warehousing 시작");
		      }
		      
		      Connection conn = null;
		      CallableStatement cs = null;
		      ResultSet rs = null;
              HashMap<String,Object> resultMap = new HashMap<>();
              
		     try {
		         conn = dataSourceTransactionManager.getConnection();
		         
		         StringBuffer query = new StringBuffer();
		         System.out.println("orderNoList:"+orderNoList);
		         System.out.println("      @ 프로시저 호출시도"); 
		         query.append(" {call P_WAREHOUSING(?,?,?)} ");
		          
			         
		         
		         cs = conn.prepareCall(query.toString());
		         cs.setString(1,orderNoList);
		         cs.registerOutParameter(2,OracleTypes.NUMBER);
		         cs.registerOutParameter(3,OracleTypes.VARCHAR);
		         cs.executeUpdate();
		         System.out.println("      @ 프로시저 호출완료");
		         
	             int errorCode = cs.getInt(2);
	             String errorMsg = cs.getString(3);
		         
	         	 resultMap.put("errorCode",errorCode);
	        	 resultMap.put("errorMsg",errorMsg);

	             return resultMap; 
	             
		      } catch (Exception sqle) {

		         throw new DataAccessException(sqle.getMessage());

		      } finally {

		         dataSourceTransactionManager.close(cs, rs);

		      }
			
		};
		@Override
		public HashMap<String, Object> updatesafetyAllowance(String itemCode, String itemName,
				String safetyAllowanceAmount) {
			if (logger.isDebugEnabled()) {
				logger.debug("StockDAOImpl : updatesafetyAllowance 시작");
			}
			Connection conn = null;
			CallableStatement cs = null;
			ResultSet rs = null;
	        HashMap<String,Object> resultMap = new HashMap<>();
				
			try {
				conn = dataSourceTransactionManager.getConnection();

				StringBuffer query = new StringBuffer();

				System.out.println("      @ 프로시저 호출시도");
				query.append("{call P_UPDATE_STOCK_SAFETYALLOWANCE(?,?,?,?,?)}");

				cs = conn.prepareCall(query.toString());
				cs.setString(1, itemCode);
				cs.setString(2, itemName);
				cs.setInt(3, Integer.parseInt(safetyAllowanceAmount));
				cs.registerOutParameter(4, OracleTypes.NUMBER);
				cs.registerOutParameter(5, OracleTypes.VARCHAR);
				cs.executeUpdate();
				
				int errorCode = cs.getInt(4);
				String errorMsg = cs.getString(5);
				
				System.out.println("      @ 프로시저 호출완료");
				
				resultMap.put("errorCode",errorCode); //성공시 0
				resultMap.put("errorMsg",errorMsg); //## MPS등록완료 ##

				if (logger.isDebugEnabled()) {
					logger.debug("StockDAOImpl : updatesafetyAllowance 종료");
				}
				return resultMap;

			} catch (Exception sqle) {

				throw new DataAccessException(sqle.getMessage());

			} finally {

				dataSourceTransactionManager.close(cs, rs);

			}
		};
		
		@Override
		public ArrayList<StockChartTO> selectStockChart() {
			if (logger.isDebugEnabled()) {
				logger.debug("StockDAOImpl : selectStockList 시작");
			}

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			ArrayList<StockChartTO> stockChart = new ArrayList<StockChartTO>();

			try {
				conn = dataSourceTransactionManager.getConnection();

				StringBuffer query = new StringBuffer();

				query.append(
						"SELECT CONCAT(CONCAT(S.item_name,' ('),CONCAT(S.item_code,')')) AS ITEM_NAME_FOR_CODE\r\n"
						+ "		, S.STOCK_AMOUNT\r\n"
						+ "		, S.SAFETY_ALLOWANCE_AMOUNT\r\n"
						+ "		, S.STOCK_AMOUNT-SAFETY_ALLOWANCE_AMOUNT AS ALLOWANCE_AMOUNT\r\n"
						+ "		FROM STOCK S, BOM B\r\n"
						+ "WHERE B.ITEM_CODE(+)=S.ITEM_CODE\r\n"
						+ "	  	AND NOT S.ITEM_NAME IN( SELECT S.ITEM_NAME FROM STOCK S WHERE S.ITEM_NAME LIKE 'KC%')\r\n"
						+ "START WITH B.PARENT_ITEM_CODE='NULL'\r\n"
						+ "		CONNECT BY PRIOR B.ITEM_CODE=B.PARENT_ITEM_CODE\r\n"
						+ "ORDER BY\r\n"
						+ "	  LEVEL\r\n"
						+ "	  , DECODE(ITEM_NAME_FOR_CODE,'카메라 본체 NO.1 (DK-AP01)',0,2)\r\n"
						+ "	  , DECODE(ITEM_NAME_FOR_CODE,'카메라 본체 NO.2 (DK-AP02)',0,2)\r\n"
						+ "	  , ITEM_NAME_FOR_CODE"
						);
				pstmt = conn.prepareStatement(query.toString());
				rs = pstmt.executeQuery();

				StockChartTO bean = null;
				
				while (rs.next()) {
					bean = new StockChartTO();
					bean.setItemName(rs.getString("ITEM_NAME_FOR_CODE"));
					bean.setStockAmount(rs.getString("STOCK_AMOUNT"));
					bean.setSaftyAmount(rs.getString("SAFETY_ALLOWANCE_AMOUNT"));
					bean.setAllowanceAmount(rs.getString("ALLOWANCE_AMOUNT"));
					stockChart.add(bean);
				}

				return stockChart;

			} catch (Exception sqle) {

				throw new DataAccessException(sqle.getMessage());

			} finally {

				dataSourceTransactionManager.close(pstmt, rs);

			}
		}
}
