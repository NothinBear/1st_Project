package com.estimulo.logistics.sales.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.ContractTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class ContractDAOImpl implements ContractDAO {
	private static Logger logger = LoggerFactory.getLogger(ContractDAOImpl.class);

	private static ContractDAO instance = new ContractDAOImpl();
	private ContractDAOImpl() { }
	public static ContractDAO getInstance() { return instance; }


	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();

	@Override
	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(String startDate, String endDate) {
		logger.debug("ContractDAOImpl : selectEstimateListInContractAvailable(String startDate, String endDate) ");
		logger.debug("startDate: " + startDate);
		logger.debug("endDate: " + endDate);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<EstimateTO> EstimateListInContractAvailable = new ArrayList<EstimateTO>();
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*EFFECTIVE_DATE = 유효일자, ESTIMATE_DATE = 견적일자
			 * SELECT * FROM ESTIMATE WHERE CONTRACT_STATUS IS NULL AND
			 * TO_DATE(EFFECTIVE_DATE,'YYYY-MM-DD') >= SYSDATE AND TO_DATE(ESTIMATE_DATE,
			 * 'YYYY-MM-DD') BETWEEN TO_DATE('20180701','YYYY-MM-DD') AND
			 * TO_DATE('20180731','YYYY-MM-DD')
			 */
			// 유효날짜 > 오늘날짜
			query.append("SELECT * FROM ESTIMATE a ,(SELECT CUSTOMER_CODE , CUSTOMER_NAME FROM CUSTOMER) b \r\n" 
					+ "WHERE CONTRACT_STATUS IS NULL \r\n"
					+ "AND b.CUSTOMER_CODE = a.CUSTOMER_CODE \r\n"
					+ "AND TO_DATE(EFFECTIVE_DATE,'YYYY-MM-DD') >= TO_DATE(SYSDATE,'YYYY-MM-DD') \r\n"
					+ "AND TO_DATE(ESTIMATE_DATE, 'YYYY-MM-DD') \r\n"
					+ "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);

			rs = pstmt.executeQuery();

			EstimateTO bean = null;

			while (rs.next()) {
				bean = new EstimateTO();

				bean.setContractStatus(rs.getString("CONTRACT_STATUS"));
				bean.setCustomerCode(rs.getString("CUSTOMER_CODE"));
				bean.setDescription(rs.getString("DESCRIPTION"));
				bean.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
				bean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
				bean.setEstimateNo(rs.getString("ESTIMATE_NO"));
				bean.setEstimateRequester(rs.getString("ESTIMATE_REQUESTER"));
				bean.setPersonCodeInCharge(rs.getString("PERSON_CODE_IN_CHARGE"));
				bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
				EstimateListInContractAvailable.add(bean);
			}
			return EstimateListInContractAvailable;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<ContractInfoTO> selectContractListByDate(String startDate, String endDate) {
		logger.debug(" ContractDAOImpl : selectContractListByDate(String startDate, String endDate) ");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ContractInfoTO> contractInfoTOList = new ArrayList<ContractInfoTO>();
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
				  WITH CODE_DETAIL_LIST AS
      		 ( SELECT DETAIL_CODE, DETAIL_CODE_NAME
              FROM CODE_DETAIL ) ,
			      CONTRACT_LIST AS
            ( SELECT * FROM CONTRACT
            WHERE TO_DATE(CONTRACT_DATE, 'YYYY-MM-DD')
            BETWEEN TO_DATE('2020-10-01', 'YYYY-MM-DD') AND TO_DATE('2020-10-31','YYYY-MM-DD') ),
					  ESTIMATE_LIST AS ( SELECT * FROM ESTIMATE )
			  	  SELECT T1.CONTRACT_NO,
                       T1.ESTIMATE_NO,
                       T1.CONTRACT_TYPE,
                       T2.DETAIL_CODE_NAME AS CONTRACT_TYPE_NAME,
                       T1.CUSTOMER_CODE,
                       T3.DETAIL_CODE_NAME AS CUSTOMER_NAME,
                       E.ESTIMATE_DATE,
                       T1.CONTRACT_DATE,
                       T1.CONTRACT_REQUESTER,
                       T1.PERSON_CODE_IN_CHARGE,
                       T4.DETAIL_CODE_NAME AS EMP_NAME_IN_CHARGE,
			                 T1.DESCRIPTION,
                       T1.DELIVERY_COMPLETION_STATUS
                       FROM CONTRACT_LIST T1 , CODE_DETAIL_LIST T2 , CODE_DETAIL_LIST T3, CODE_DETAIL_LIST T4, ESTIMATE_LIST E
                       WHERE T1.CONTRACT_TYPE = T2.DETAIL_CODE
                           AND T1.CUSTOMER_CODE = T3.DETAIL_CODE
                           AND T1.PERSON_CODE_IN_CHARGE = T4.DETAIL_CODE AND T1.ESTIMATE_NO = E.ESTIMATE_NO
			 */
			query.append("  WITH CODE_DETAIL_LIST AS\n" +
					"      \t\t ( SELECT DETAIL_CODE, DETAIL_CODE_NAME\n" +
					"              FROM CODE_DETAIL ) ,\n" +
					"\t\t\t      CONTRACT_LIST AS\n" +
					"            ( SELECT * FROM CONTRACT\n" +
					"            WHERE TO_DATE(CONTRACT_DATE, 'YYYY-MM-DD')\n" +
					"            BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ),\n" +
					"\t\t\t\t\t  ESTIMATE_LIST AS ( SELECT * FROM ESTIMATE )\n" +
					"\t\t\t  \t  SELECT T1.CONTRACT_NO,\n" +
					"                       T1.ESTIMATE_NO,\n" +
					"                       T1.CONTRACT_TYPE,\n" +
					"                       T2.DETAIL_CODE_NAME AS CONTRACT_TYPE_NAME,\n" +
					"                       T1.CUSTOMER_CODE,\n" +
					"                       T3.DETAIL_CODE_NAME AS CUSTOMER_NAME,\n" +
					"                       E.ESTIMATE_DATE,\n" +
					"                       T1.CONTRACT_DATE,\n" +
					"                       T1.CONTRACT_REQUESTER,\n" +
					"                       T1.PERSON_CODE_IN_CHARGE,\n" +
					"                       T4.DETAIL_CODE_NAME AS EMP_NAME_IN_CHARGE,\n" +
					"\t\t\t                 T1.DESCRIPTION,\n" +
					"                       T1.DELIVERY_COMPLETION_STATUS\n" +
					"                       FROM CONTRACT_LIST T1 , CODE_DETAIL_LIST T2 , CODE_DETAIL_LIST T3, CODE_DETAIL_LIST T4, ESTIMATE_LIST E\n" +
					"                       WHERE T1.CONTRACT_TYPE = T2.DETAIL_CODE\n" +
					"                           AND T1.CUSTOMER_CODE = T3.DETAIL_CODE\n" +
					"                           AND T1.PERSON_CODE_IN_CHARGE = T4.DETAIL_CODE AND T1.ESTIMATE_NO = E.ESTIMATE_NO");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);

			rs = pstmt.executeQuery();

			ContractInfoTO bean = null;

			while (rs.next()) {
				bean = new ContractInfoTO();

				bean.setContractNo(rs.getString("CONTRACT_NO"));
				bean.setEstimateNo(rs.getString("ESTIMATE_NO"));
				bean.setContractType(rs.getString("CONTRACT_TYPE"));
				bean.setContractTypeName(rs.getString("CONTRACT_TYPE_NAME"));
				bean.setCustomerCode(rs.getString("CUSTOMER_CODE"));
				bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
				bean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
				bean.setContractDate(rs.getString("CONTRACT_DATE"));
				bean.setContractRequester(rs.getString("CONTRACT_REQUESTER"));
				bean.setPersonCodeInCharge(rs.getString("PERSON_CODE_IN_CHARGE"));
				bean.setEmpNameInCharge(rs.getString("EMP_NAME_IN_CHARGE"));
				bean.setDescription(rs.getString("DESCRIPTION"));
				bean.setDeliveryCompletionStatus(rs.getString("DELIVERY_COMPLETION_STATUS"));
				contractInfoTOList.add(bean);
			}
			System.out.println("contractDAOImpl selectContractByDate End");
			return contractInfoTOList;

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<ContractInfoTO> selectContractListByCustomer(String customerCode) {
		logger.debug(" ContractDAOImpl : selectContractListByCustomer(String customerCode)");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ContractInfoTO> contractInfoTOList = new ArrayList<ContractInfoTO>();
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * WITH CODE_DETAIL_LIST AS ( SELECT DETAIL_CODE, DETAIL_CODE_NAME FROM
			 * CODE_DETAIL ) ,
			 *
			 * CONTRACT_LIST AS ( SELECT * FROM CONTRACT WHERE CUSTOMER_CODE = ? ) ,
			 *
			 * ESTIMATE_LIST AS ( SELECT * FROM ESTIMATE )
			 *
			 * SELECT T1.CONTRACT_NO, T1.ESTIMATE_NO, T1.CONTRACT_TYPE, T2.DETAIL_CODE_NAME
			 * AS CONTRACT_TYPE_NAME, T1.CUSTOMER_CODE, T3.DETAIL_CODE_NAME AS
			 * CUSTOMER_NAME, E.ESTIMATE_DATE, T1.CONTRACT_DATE, T1.CONTRACT_REQUESTER,
			 * T1.PERSON_CODE_IN_CHARGE, T4.DETAIL_CODE_NAME AS EMP_NAME_IN_CHARGE,
			 * T1.DESCRIPTION FROM CONTRACT_LIST T1 , CODE_DETAIL_LIST T2 , CODE_DETAIL_LIST
			 * T3, CODE_DETAIL_LIST T4, ESTIMATE_LIST E WHERE T1.CONTRACT_TYPE =
			 * T2.DETAIL_CODE AND T1.CUSTOMER_CODE = T3.DETAIL_CODE AND
			 * T1.PERSON_CODE_IN_CHARGE = T4.DETAIL_CODE AND T1.ESTIMATE_NO = E.ESTIMATE_NO
			 *
			 */
			query.append("WITH CODE_DETAIL_LIST AS\r\n"
					+ "( SELECT DETAIL_CODE, DETAIL_CODE_NAME FROM CODE_DETAIL ) ,\r\n" + "\r\n"
					+ "CONTRACT_LIST AS \r\n" + "( SELECT * FROM CONTRACT WHERE CUSTOMER_CODE = ? ) ,\r\n" + "\r\n"
					+ "ESTIMATE_LIST AS\r\n" + "( SELECT * FROM ESTIMATE )\r\n" + "\r\n"
					+ "SELECT T1.CONTRACT_NO, T1.ESTIMATE_NO, \r\n"
					+ "	T1.CONTRACT_TYPE, T2.DETAIL_CODE_NAME AS CONTRACT_TYPE_NAME,\r\n"
					+ "    T1.CUSTOMER_CODE, T3.DETAIL_CODE_NAME AS CUSTOMER_NAME,\r\n"
					+ "    E.ESTIMATE_DATE, T1.CONTRACT_DATE, T1.CONTRACT_REQUESTER, \r\n"
					+ "    T1.PERSON_CODE_IN_CHARGE, T4.DETAIL_CODE_NAME AS EMP_NAME_IN_CHARGE,\r\n"
					+ "    T1.DESCRIPTION\r\n" + "FROM CONTRACT_LIST T1 , \r\n"
					+ "	CODE_DETAIL_LIST T2 , CODE_DETAIL_LIST T3, CODE_DETAIL_LIST T4, \r\n" + "	ESTIMATE_LIST E\r\n"
					+ "WHERE T1.CONTRACT_TYPE = T2.DETAIL_CODE\r\n" + "	AND T1.CUSTOMER_CODE = T3.DETAIL_CODE\r\n"
					+ "	AND T1.PERSON_CODE_IN_CHARGE = T4.DETAIL_CODE\r\n" + "	AND T1.ESTIMATE_NO = E.ESTIMATE_NO");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, customerCode);

			rs = pstmt.executeQuery();

			ContractInfoTO bean = null;

			while (rs.next()) {

				bean = new ContractInfoTO();

				bean.setContractNo(rs.getString("CONTRACT_NO"));
				bean.setEstimateNo(rs.getString("ESTIMATE_NO"));
				bean.setContractType(rs.getString("CONTRACT_TYPE"));
				bean.setContractTypeName(rs.getString("CONTRACT_TYPE_NAME"));
				bean.setCustomerCode(rs.getString("CUSTOMER_CODE"));
				bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
				bean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
				bean.setContractDate(rs.getString("CONTRACT_DATE"));
				bean.setContractRequester(rs.getString("CONTRACT_REQUESTER"));
				bean.setPersonCodeInCharge(rs.getString("PERSON_CODE_IN_CHARGE"));
				bean.setEmpNameInCharge(rs.getString("EMP_NAME_IN_CHARGE"));
				bean.setDescription(rs.getString("DESCRIPTION"));

				contractInfoTOList.add(bean);

			}

			return contractInfoTOList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}
	

	
	@Override
	public ArrayList<ContractInfoTO> selectDeliverableContractList(HashMap<String, String> ableSearchConditionInfo) {
		logger.debug("ContractDAOImpl : selectDeliverableContractList(HashMap<String, String> ableSearchConditionInfo)");
		//납품가능리스트 날짜 or 회사코드
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	ArrayList<ContractInfoTO> contractInfoTOList = new ArrayList<ContractInfoTO>();
	try {
		conn = dataSourceTransactionManager.getConnection();
		StringBuffer query = new StringBuffer();
		

		query.append("\r\n" +
				"WITH CODE_DETAIL_LIST AS ( SELECT DETAIL_CODE, DETAIL_CODE_NAME FROM\r\n" +
				"CODE_DETAIL ) ,\r\n" +
				"\r\n" );
		
		
		if(ableSearchConditionInfo.get("searchCondition").equals("searchByCustomer"))
		{
			logger.debug("customerCode: " + ableSearchConditionInfo.get("searchCondition"));
			
			query.append(	
					"CONTRACT_LIST AS \r\n" +
					"( SELECT * FROM CONTRACT WHERE CUSTOMER_CODE = ? ) ,\r\n" );
						
		}else if(ableSearchConditionInfo.get("searchCondition").equals("searchByDate")) {
			
			logger.debug("startData: " + ableSearchConditionInfo.get("startDate"));
			logger.debug("endData: " + ableSearchConditionInfo.get("endDate"));
			
			query.append(	
					"CONTRACT_LIST AS ( SELECT * FROM CONTRACT WHERE TO_DATE(CONTRACT_DATE,'RRRR-MM-DD') BETWEEN\r\n" +
					"TO_DATE(?,'RRRR-MM-DD') AND TO_DATE(?,'RRRR-MM-DD') ),\r\n" );
		}
		
		query.append("\r\n" +
				"ESTIMATE_LIST AS ( SELECT * FROM ESTIMATE )\r\n" +
				"\r\n" +
				"SELECT \r\n" +
				"T1.CONTRACT_NO, \r\n" +
				"T1.ESTIMATE_NO, \r\n" +
				"T1.CONTRACT_TYPE, \r\n" +
				"T2.DETAIL_CODE_NAME AS CONTRACT_TYPE_NAME, \r\n" +
				"T1.CUSTOMER_CODE,\r\n" +
				"T3.DETAIL_CODE_NAME AS CUSTOMER_NAME, \r\n" +
				"E.ESTIMATE_DATE, \r\n" +
				"T1.CONTRACT_DATE, \r\n" +
				"T1.CONTRACT_REQUESTER,\r\n" +
				"T1.PERSON_CODE_IN_CHARGE, \r\n" +
				"T4.DETAIL_CODE_NAME AS EMP_NAME_IN_CHARGE,\r\n" +
				"T1.DESCRIPTION,\r\n" +
				"T1.DELIVERY_COMPLETION_STATUS\r\n" +
				"FROM CONTRACT_LIST T1 , CODE_DETAIL_LIST T2 , CODE_DETAIL_LIST\r\n" +
				"T3, CODE_DETAIL_LIST T4, ESTIMATE_LIST E \r\n" +
				"WHERE T1.CONTRACT_TYPE = T2.DETAIL_CODE \r\n" +
				"AND T1.CUSTOMER_CODE = T3.DETAIL_CODE \r\n" +
				"AND T1.PERSON_CODE_IN_CHARGE = T4.DETAIL_CODE \r\n" +
				"AND T1.ESTIMATE_NO = E.ESTIMATE_NO\r\n" +
				"AND T1.DELIVERY_COMPLETION_STATUS IS NULL");

		//납품가능리스트 날짜 or 회사코드
		/*
		WITH CODE_DETAIL_LIST AS ( SELECT DETAIL_CODE, DETAIL_CODE_NAME FROM
		CODE_DETAIL ) ,
		_______________________________________________________________________
		CONTRACT_LIST AS ( SELECT * FROM CONTRACT WHERE TO_DATE(CONTRACT_DATE,'RRRR-MM-DD') BETWEEN
		TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ),
		_______________________________________________________________________
		// 위는 날짜검색  아래는 거래처로 검색 둘중 하나 선택.
		
		CONTRACT_LIST AS
		( SELECT * FROM CONTRACT WHERE CUSTOMER_CODE = 'PTN-01' ) ,
		_______________________________________________________________________
		
		
		ESTIMATE_LIST AS
		( SELECT * FROM ESTIMATE )
		
		SELECT
		T1.CONTRACT_NO,
		T1.ESTIMATE_NO,
		T1.CONTRACT_TYPE,
		T2.DETAIL_CODE_NAME AS CONTRACT_TYPE_NAME,
		T1.CUSTOMER_CODE,
		T3.DETAIL_CODE_NAME AS CUSTOMER_NAME,
		E.ESTIMATE_DATE,
		T1.CONTRACT_DATE,
		T1.CONTRACT_REQUESTER,
		T1.PERSON_CODE_IN_CHARGE,
		T4.DETAIL_CODE_NAME AS EMP_NAME_IN_CHARGE,
		T1.DESCRIPTION,
		T1.DELIVERY_COMPLETION_STATUS
		FROM
		CONTRACT_LIST T1 ,
		CODE_DETAIL_LIST T2 ,
		CODE_DETAIL_LIST T3,
		CODE_DETAIL_LIST T4,
		ESTIMATE_LIST E
		WHERE
		T1.CONTRACT_TYPE = T2.DETAIL_CODE
		AND T1.CUSTOMER_CODE = T3.DETAIL_CODE
		AND T1.PERSON_CODE_IN_CHARGE = T4.DETAIL_CODE
		AND T1.ESTIMATE_NO = E.ESTIMATE_NO
		AND T1.DELIVERY_COMPLETION_STATUS IS NULL*/

		pstmt = conn.prepareStatement(query.toString());
		
		if(ableSearchConditionInfo.get("searchCondition").equals("searchByCustomer")) {
			pstmt.setString(1,ableSearchConditionInfo.get("customerCode"));
			
		}else {
			
			pstmt.setString(1,ableSearchConditionInfo.get("startDate"));
			pstmt.setString(2,ableSearchConditionInfo.get("endDate"));
		}
		
		rs = pstmt.executeQuery();

		ContractInfoTO bean = null;

		while (rs.next()) {

			bean = new ContractInfoTO();

			bean.setContractNo(rs.getString("CONTRACT_NO"));
			bean.setEstimateNo(rs.getString("ESTIMATE_NO"));
			bean.setContractType(rs.getString("CONTRACT_TYPE"));
			bean.setContractTypeName(rs.getString("CONTRACT_TYPE_NAME"));
			bean.setCustomerCode(rs.getString("CUSTOMER_CODE"));
			bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
			bean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
			bean.setContractDate(rs.getString("CONTRACT_DATE"));
			bean.setContractRequester(rs.getString("CONTRACT_REQUESTER"));
			bean.setPersonCodeInCharge(rs.getString("PERSON_CODE_IN_CHARGE"));
			bean.setEmpNameInCharge(rs.getString("EMP_NAME_IN_CHARGE"));
			bean.setDescription(rs.getString("DESCRIPTION"));
			bean.setDeliveryCompletionStatus(rs.getString("DELIVERY_COMPLETION_STATUS"));

			contractInfoTOList.add(bean);
			
			
		}
		return contractInfoTOList;
	} catch (Exception sqle) {
		throw new DataAccessException(sqle.getMessage());
	} finally {
		dataSourceTransactionManager.close(pstmt, rs);
	}
}

	

	@Override
	public int selectContractCount(String contractDate) {
		logger.debug("ContractDAOImpl : selectContractCount(String contractDate)");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * SELECT COUNT(*) FROM CONTRACT WHERE CONTRACT_DATE =
			 * TO_DATE('2018-07-10','YYYY-MM-DD')
			 */

			query.append("select CONTRACT_SEQ.NEXTVAL from dual");
			pstmt = conn.prepareStatement(query.toString());

			rs = pstmt.executeQuery();

			int i = 0;

			if (rs.next()) {
				i = rs.getInt(1);
			}
			return i;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void insertContract(ContractTO bean) {
	      logger.debug("ContractDAOImpl : insertContract 시작");

	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      try {
	         conn = dataSourceTransactionManager.getConnection();
	         StringBuffer query = new StringBuffer();
	         /*
	          * Insert into CONTRACT (CONTRACT_NO, ESTIMATE_NO, CONTRACT_TYPE, CUSTOMER_CODE,
	          * CONTRACT_DATE, CONTRACT_REQUESTER, PERSON_CODE_IN_CHARGE, DESCRIPTION) values
	          * ('CO2018070301','ES2018070301','견적','PTN-01','2018-07-03','김종한','EMP-02',
	          * null)
	          */
	         query.append("Insert into CONTRACT (CONTRACT_NO, ESTIMATE_NO, \r\n"
	               + "CONTRACT_TYPE, CUSTOMER_CODE, CONTRACT_DATE, \r\n"
	               + "CONTRACT_REQUESTER, PERSON_CODE_IN_CHARGE, DESCRIPTION) \r\n"
	               + "values (?, ?, ?, ?, ?, ?, ?, ?)");
	         pstmt = conn.prepareStatement(query.toString());
	         pstmt.setString(1, bean.getContractNo());
	         pstmt.setString(2, bean.getEstimateNo());
	         pstmt.setString(3, bean.getContractType());
	         pstmt.setString(4, bean.getCustomerCode());
	         pstmt.setString(5, bean.getContractDate());
	         pstmt.setString(6, bean.getContractRequester());
	         pstmt.setString(7, bean.getPersonCodeInCharge());
	         pstmt.setString(8, bean.getDescription());

	         pstmt.executeQuery();

	      } catch (Exception sqle) {

	         throw new DataAccessException(sqle.getMessage());

	      } finally {

	         dataSourceTransactionManager.close(pstmt);

	      }

	   }

	@Override
	public void updateContract(ContractTO bean) {
		logger.debug("ContractDAOImpl : updateContract 시작");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE CONTRACT SET ESTIMATE_NO = ? , CONTRACT_TYPE = ? , CUSTOMER_CODE = ? ,
			 * CONTRACT_DATE = ? , CONTRACT_REQUESTER = ? , PERSON_CODE_IN_CHARGE = ? ,
			 * DESCRIPTION = ? WHERE CONTRACT_NO = ?
			 */
			query.append("UPDATE CONTRACT SET \r\n" + "ESTIMATE_NO = ? , CONTRACT_TYPE = ? , CUSTOMER_CODE = ? ,\r\n"
					+ "CONTRACT_DATE = ? , CONTRACT_REQUESTER = ? , \r\n"
					+ "PERSON_CODE_IN_CHARGE = ? , DESCRIPTION = ? \r\n" + "WHERE CONTRACT_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getEstimateNo());
			pstmt.setString(2, bean.getContractType());
			pstmt.setString(3, bean.getCustomerCode());
			pstmt.setString(4, bean.getContractDate());
			pstmt.setString(5, bean.getContractRequester());
			pstmt.setString(6, bean.getPersonCodeInCharge());
			pstmt.setString(7, bean.getDescription());
			pstmt.setString(8, bean.getContractNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void deleteContract(ContractTO TO) {

	}
}
