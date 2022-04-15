package com.estimulo.logistics.sales.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class EstimateDetailDAOImpl implements EstimateDetailDAO {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(EstimateDetailDAOImpl.class);

	// 싱글톤
	private static EstimateDetailDAO instance = new EstimateDetailDAOImpl();

	private EstimateDetailDAOImpl() {
	}

	public static EstimateDetailDAO getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ EstimateDetailDAOImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();

	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo) {

		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : selectEstimateDetailList 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<EstimateDetailTO> estimateDetailTOList = new ArrayList<EstimateDetailTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ESTIMATE_DETAIL WHERE ESTIMATE_NO =?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, estimateNo);

			rs = pstmt.executeQuery();

			EstimateDetailTO bean = null;

			while (rs.next()) {

				bean = new EstimateDetailTO();

				bean.setDescription(rs.getString("DESCRIPTION"));//비고
				bean.setDueDateOfEstimate(rs.getString("DUE_DATE_OF_ESTIMATE"));//납기일
				bean.setEstimateAmount(rs.getInt("ESTIMATE_AMOUNT"));//견적수량
				bean.setEstimateDetailNo(rs.getString("ESTIMATE_DETAIL_NO"));//견적상세번호
				bean.setEstimateNo(estimateNo);//견적번호
				bean.setItemCode(rs.getString("ITEM_CODE"));//품목코드
				bean.setItemName(rs.getString("ITEM_NAME"));//품목이름
				bean.setSumPriceOfEstimate(rs.getInt("SUM_PRICE_OF_ESTIMATE"));//품목 합계액
				bean.setUnitOfEstimate(rs.getString("UNIT_OF_ESTIMATE"));//품목 금액
				bean.setUnitPriceOfEstimate(rs.getInt("UNIT_PRICE_OF_ESTIMATE"));//수량 단위

				estimateDetailTOList.add(bean);

			}

			return estimateDetailTOList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public int selectEstimateDetailCount(String estimateNo) {

		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : selectEstimateDetailCount 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * SELECT ESTIMATE_DETAIL_NO FROM ESTIMATE_DETAIL WHERE ESTIMATE_NO = ?
			 */
			query.append("SELECT ESTIMATE_DETAIL_NO FROM ESTIMATE_DETAIL WHERE ESTIMATE_NO = ?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, estimateNo);

			rs = pstmt.executeQuery();

			TreeSet<Integer> intSet = new TreeSet<>();

			while (rs.next()) {
				String estimateDetailNo = rs.getString("ESTIMATE_DETAIL_NO");
				int no = Integer.parseInt(estimateDetailNo.split("-")[1]);

				intSet.add(no);
			}

			if (intSet.isEmpty()) {
				return 1;
			} else {
				return intSet.pollLast() + 1;
			}

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}
	public int selectEstimateDetailSeq(String estimateDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDAOImpl : selectEstimateDetailCount 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * SELECT COUNT(*) FROM ESTIMATE WHERE ESTIMATE_DATE =
			 * TO_DATE('2018-07-10','YYYY-MM-DD')
			 */
			query.append("SELECT EST_DETAIL_SEQ.NEXTVAL FROM DUAL"); //0
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

	public void insertEstimateDetail(EstimateDetailTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : insertEstimateDetail 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * Insert into ESTIMATE_DETAIL (ESTIMATE_DETAIL_NO, ESTIMATE_NO, ITEM_CODE,
			 * ITEM_NAME, UNIT_OF_ESTIMATE, DUE_DATE_OF_ESTIMATE, ESTIMATE_AMOUNT,
			 * UNIT_PRICE_OF_ESTIMATE, SUM_PRICE_OF_ESTIMATE, DESCRIPTION) values
			 * ('ES2018070101-01','ES2018070101', 'DK-01','디지털카메라 NO.1',
			 * 'EA','2018-07-10',200,210000,42000000,null)
			 */
			int estimateAmount = 0;
			switch(bean.getUnitOfEstimate()) {
			case "BOX" :
				estimateAmount=bean.getEstimateAmount()*8;
			case "SET" :
				estimateAmount=bean.getEstimateAmount()*2;
			case "EA" :
				estimateAmount=bean.getEstimateAmount();
			}
			query.append(
					"Insert into ESTIMATE_DETAIL\r\n" + "(ESTIMATE_DETAIL_NO, ESTIMATE_NO, ITEM_CODE, ITEM_NAME, \r\n"
							+ "UNIT_OF_ESTIMATE, DUE_DATE_OF_ESTIMATE, ESTIMATE_AMOUNT,\r\n"
							+ "UNIT_PRICE_OF_ESTIMATE, SUM_PRICE_OF_ESTIMATE, DESCRIPTION)\r\n"
							+ "values (?,?,?,?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, bean.getEstimateDetailNo());
			pstmt.setString(2, bean.getEstimateNo());
			pstmt.setString(3, bean.getItemCode());
			pstmt.setString(4, bean.getItemName());
			pstmt.setString(5, bean.getUnitOfEstimate());
			pstmt.setString(6, bean.getDueDateOfEstimate());
			pstmt.setInt(7, estimateAmount);
			pstmt.setInt(8, bean.getUnitPriceOfEstimate());
			pstmt.setInt(9, bean.getSumPriceOfEstimate());
			pstmt.setString(10, bean.getDescription());
			pstmt.executeUpdate();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt);

		}
	}

	public void updateEstimateDetail(EstimateDetailTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : updateEstimateDetail 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE ESTIMATE_DETAIL SET ITEM_CODE = ? , ITEM_NAME = ? , UNIT_OF_ESTIMATE =
			 * ? , DUE_DATE_OF_ESTIMATE = ? , ESTIMATE_AMOUNT = ? , UNIT_PRICE_OF_ESTIMATE =
			 * ? , SUM_PRICE_OF_ESTIMATE = ? , DESCRIPTION = ? WHERE ESTIMATE_DETAIL_NO = ?
			 */
			query.append("UPDATE ESTIMATE_DETAIL SET ITEM_CODE = ? , ITEM_NAME = ? , \r\n"
					+ "UNIT_OF_ESTIMATE = ? , DUE_DATE_OF_ESTIMATE = ? , \r\n"
					+ "ESTIMATE_AMOUNT = ? , UNIT_PRICE_OF_ESTIMATE = ? , \r\n"
					+ "SUM_PRICE_OF_ESTIMATE = ? , DESCRIPTION = ? \r\n" + "WHERE ESTIMATE_DETAIL_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getItemCode());
			pstmt.setString(2, bean.getItemName());
			pstmt.setString(3, bean.getUnitOfEstimate());
			pstmt.setString(4, bean.getDueDateOfEstimate());
			pstmt.setInt(5, bean.getEstimateAmount());
			pstmt.setInt(6, bean.getUnitPriceOfEstimate());
			pstmt.setInt(7, bean.getSumPriceOfEstimate());
			pstmt.setString(8, bean.getDescription());
			pstmt.setString(9, bean.getEstimateDetailNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public void deleteEstimateDetail(EstimateDetailTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : deleteEstimateDetail 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * DELETE FROM ESTIMATE_DETAIL WHERE ESTIMATE_DETAIL_NO = ?
			 */
			query.append("DELETE FROM ESTIMATE_DETAIL WHERE ESTIMATE_DETAIL_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getEstimateDetailNo());
			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void initDetailSeq() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : initDetailSeq 시작");
		}

		Connection conn = null;
		CallableStatement cs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			System.out.println("      @ 프로시저 호출시도");
			query.append(" {call P_SEQUENCE_INIT(?)} ");
			cs = conn.prepareCall(query.toString());
			cs.setString(1, "EST_DETAIL_SEQ");
			System.out.println("      @ 프로시저 호출완료");
			cs.executeUpdate();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(cs);

		}
	}

	@Override
	public void reArrangeEstimateDetail(EstimateDetailTO bean, String newSeq) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("EstimateDetailDAOImpl : reArrangeEstimateDetail 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE ESTIMATE_DETAIL\r\n"
					+ "SET	ESTIMATE_DETAIL_NO=replace(ESTIMATE_DETAIL_NO,substr(ESTIMATE_DETAIL_NO,instr(ESTIMATE_DETAIL_NO,'-')+1),?)\r\n"
					+ "WHERE ESTIMATE_DETAIL_NO=?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, newSeq);
			pstmt.setString(2, bean.getEstimateDetailNo());

			pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt);

		}
	}
}
