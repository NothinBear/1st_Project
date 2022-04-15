package com.estimulo.logistics.material.dao;

import oracle.jdbc.internal.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.material.to.OrderDialogTempTO;
import com.estimulo.logistics.material.to.OrderInfoTO;
import com.estimulo.logistics.material.to.OrderTempTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDAOImpl implements OrderDAO {
	private static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

	private static OrderDAO instance = new OrderDAOImpl();
	private OrderDAOImpl() { }
	public static OrderDAO getInstance() { return instance; }

	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();
	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
		logger.debug("OrderDAOImpl : getOrderList(String startDate, String endDate)");

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		ArrayList<OrderTempTO> orderList = new ArrayList<OrderTempTO>();
		HashMap<String,Object> resultMap = new HashMap<>();
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			    SELECT
			    M.MRP_GATHERING_NO,
			    M.MRP_NO,
			    M.item_code,
			    TRIM(M.item_name) ITEM_NAME,
			    M.unit_of_mrp,
			    M.required_amount,
			    (S.STOCK_AMOUNT-S.SAFETY_ALLOWANCE_AMOUNT) STOCK_AMOUNT,
			    M.order_date,
			    M.required_date
			    FROM MRP M, STOCK S
			    WHERE
			    S.ITEM_CODE = M.ITEM_CODE
			    AND S.ITEM_NAME = TRIM(M.ITEM_NAME)
			    AND M.REQUEST_STATUS IS NULL --발주 및 작업요청여부
			    AND M.MRP_GATHERING_NO IS NOT NULL --소요량취합번호
			    AND M.ITEM_CLASSIFICATION = '원재료'
			    AND TO_DATE(M.ORDER_DATE,'YYYY-MM-DD') BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')
			    ORDER BY MRP_GATHERING_NO,MRP_NO;
			*/

			query.append(" {call P_ORDERLIST_OPEN(?,?,?,?,?)} ");

			logger.debug("		@ 프로시저 호출 : P_ORDERLIST_OPEN");
			logger.debug("		@ startDate : "+startDate);
			logger.debug("		@ endDate : "+endDate);
			cs = conn.prepareCall(query.toString());
			cs.setString(1, startDate);
			cs.setString(2, endDate);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.executeUpdate();

			int errorCode = cs.getInt(3);
			String errorMsg = cs.getString(4);
			rs = (ResultSet) cs.getObject(5);

			OrderTempTO bean = null;
			while (rs.next()) {

				bean = new OrderTempTO();

				bean.setMrpGatheringNo(rs.getString("MRP_GATHERING_NO"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setUnitOfMrp(rs.getString("UNIT_OF_MRP"));
				bean.setRequiredAmount(rs.getInt("REQUIRED_AMOUNT"));
				bean.setStockAmount(rs.getInt("STOCK_AMOUNT"));
				bean.setOrderDate(rs.getString("ORDER_DATE"));
				bean.setRequiredDate(rs.getString("REQUIRED_DATE"));
				orderList.add(bean);
			}

			resultMap.put("gridRowJson",orderList);
			resultMap.put("errorCode",errorCode);
			resultMap.put("errorMsg",errorMsg);
			return resultMap;

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cs, rs);
		}
	}
	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpNoList) {
		logger.debug("OrderDAOImpl : getOrderDialogInfo(String mrpNoList)");

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		HashMap<String,Object> resultMap = new HashMap<>();

		ArrayList<OrderDialogTempTO> orderDialogInfoList = new ArrayList<OrderDialogTempTO>();
		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append(" {call P_ORDER_DIALOG_OPEN(?,?,?,?)} ");

			logger.debug("      @ P_ORDER_DIALOG_OPEN 프로시저 호출 ");

			cs = conn.prepareCall(query.toString());
			cs.setString(1, mrpNoList);
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.executeUpdate();

			int errorCode = cs.getInt(2);
			String errorMsg = cs.getString(3);
			rs = (ResultSet) cs.getObject(4);

			OrderDialogTempTO bean = null;

			while (rs.next()) {
				bean = new OrderDialogTempTO();
				//bean.setMrpGatheringNo(rs.getString("mrp_gathering_no"));
				bean.setItemCode(rs.getString("item_code"));
				bean.setItemName(rs.getString("item_name"));
				bean.setUnitOfMrp(rs.getString("unit_of_mrp"));
				bean.setRequiredAmount(rs.getString("required_amount"));
				bean.setStockAmount(rs.getString("stock_amount"));
				bean.setCalculatedRequiredAmount(rs.getString("calculated_required_amount"));
				bean.setStandardUnitPrice(rs.getString("standard_unit_price"));
				bean.setSumPrice(rs.getString("sum_price"));

				orderDialogInfoList.add(bean);

			}
			resultMap.put("gridRowJson", orderDialogInfoList);
			resultMap.put("errorCode",errorCode);
			resultMap.put("errorMsg",errorMsg);

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cs, rs);
		}

		return resultMap;
	}
	@Override
	public HashMap<String,Object> order(String mpsNoList) {
		logger.debug("OrderDAOImpl : order(String mpsNoList)");

		logger.debug("		@ List : "+ mpsNoList);
		//소요량취합번호 리스트.  @ List : MG20210128-005, MG20210128-002, MG20210128-003...
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		HashMap<String,Object> resultMap = new HashMap<>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			System.out.println("      @ 프로시저 호출시도");
			query.append(" {call P_ORDER(?,?,?)} ");

			cs = conn.prepareCall(query.toString());
			cs.setString(1, mpsNoList);
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.executeUpdate();

			logger.debug("      @ 프로시저 호출완료");

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
	}
	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		logger.debug("OrderDAOImpl : optionOrder(String itemCode, String itemAmount)");

		logger.debug("itemCode: " + itemCode);
		logger.debug("itemAmount: " + itemAmount);
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		HashMap<String,Object> resultMap = new HashMap<>();
		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			logger.debug("@ 프로시저 호출시도 P_OPTION_ORDER");
			query.append(" {call P_OPTION_ORDER(?,?,?,?)} ");

			cs = conn.prepareCall(query.toString());
			cs.setString(1, itemCode);
			cs.setString(2, itemAmount);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.executeUpdate();

			logger.debug("@ 프로시저 호출완료 P_OPTION_ORDER");

			int errorCode = cs.getInt(3);
			String errorMsg = cs.getString(4);

			resultMap.put("errorCode",errorCode);
			resultMap.put("errorMsg",errorMsg);

			return resultMap;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(cs, rs);

		}
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		logger.debug("OrderDAOImpl : getOrderInfoListOnDelivery() ");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<OrderInfoTO> OrderInfoListOnDelivery = new ArrayList<OrderInfoTO>();
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			query.append("select * from order_info where ORDER_INFO_STATUS = '운송중' OR ORDER_INFO_STATUS = '검사완료'");

			pstmt = conn.prepareStatement(query.toString());

			rs = pstmt.executeQuery();

			OrderInfoTO bean = null;

			while (rs.next()) {

				bean = new OrderInfoTO();

				bean.setOrderNo(rs.getString("ORDER_NO"));
				bean.setOrderDate(rs.getString("ORDER_DATE"));
				bean.setOrderInfoStatus(rs.getString("ORDER_INFO_STATUS"));
				bean.setOrderSort(rs.getString("ORDER_SORT"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setOrderAmount(rs.getString("ORDER_AMOUNT"));
				bean.setInspectionStatus(rs.getString("INSPECTION_STATUS"));
				OrderInfoListOnDelivery.add(bean);
			}

			return OrderInfoListOnDelivery;

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}

	}
	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		logger.debug("OrderDAOImpl : getOrderInfoList(String startDate, String endDate)");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<OrderInfoTO> orderInfoList = new ArrayList<OrderInfoTO>();
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			query.append("select * from order_info\r\n" +
					"where \r\n" +
					"to_date(,'YY-MM-DD') \r\n" +
					"between to_date(?,'YY-MM-DD') AND\r\n" +
					"to_date(?,'YY-MM-DD')");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			rs = pstmt.executeQuery();

			OrderInfoTO bean = null;

			while (rs.next()) {

				bean = new OrderInfoTO();

				bean.setOrderNo(rs.getString("ORDER_NO"));
				bean.setOrderDate(rs.getString(""));
				bean.setOrderInfoStatus(rs.getString("ORDER_INFO_STATUS"));
				bean.setOrderSort(rs.getString("ORDER_SORT"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setOrderAmount(rs.getString("ORDER_AMOUNT"));

				orderInfoList.add(bean);

			}

			return orderInfoList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public HashMap<String,Object> updateOrderInfo(String orderNoList) {
		// TODO Auto-generated method stub
		logger.debug("OrderDAOImpl : updateOrderInfo");

		Connection conn = null;
		CallableStatement cs = null;
		HashMap<String,Object> resultMap = new HashMap<>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			logger.debug("@ 프로시저 호출시도 P_INSPECTION");
			query.append(" {call P_INSPECTION(?,?,?)} ");
			cs = conn.prepareCall(query.toString());
	        cs.setString(1,orderNoList);
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.executeUpdate();

			logger.debug("@ 프로시저 호출완료 P_INSPECTION");

			int errorCode = cs.getInt(2);
			String errorMsg = cs.getString(3);

			resultMap.put("errorCode",errorCode);
			resultMap.put("errorMsg",errorMsg);
			
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cs);
		}
		return resultMap;
	}
}
