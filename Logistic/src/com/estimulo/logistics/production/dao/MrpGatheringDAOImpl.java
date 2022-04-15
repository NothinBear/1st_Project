package com.estimulo.logistics.production.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

import oracle.jdbc.internal.OracleTypes;

public class MrpGatheringDAOImpl implements MrpGatheringDAO {

   // SLF4J logger
   private static Logger logger = LoggerFactory.getLogger(MrpGatheringDAOImpl.class);

   // 싱글톤
   private static MrpGatheringDAO instance = new MrpGatheringDAOImpl();

   private MrpGatheringDAOImpl() {
   }

   public static MrpGatheringDAO getInstance() {

      if (logger.isDebugEnabled()) {
         logger.debug("@ MrpGatheringDAOImpl 객체접근");
      }

      return instance;
   }

   // 참조변수 선언
   private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
         .getInstance();

   public ArrayList<MrpGatheringTO> selectMrpGatheringList(String searchDateCondition, String startDate,
         String endDate) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : selectMrpGatheringList 시작");
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      ArrayList<MrpGatheringTO> mrpGatheringList = new ArrayList<MrpGatheringTO>();

      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();
         /*
          * SELECT * FROM MRP_GATHERING WHERE ( CASE ? WHEN 'claimDate' THEN
          * TO_DATE(CLAIM_DATE, 'YYYY-MM-DD') WHEN 'orderDate' THEN TO_DATE(ORDER_DATE,
          * 'YYYY-MM-DD') END ) BETWEEN TO_DATE(?,'YYYY-MM-DD') AND
          * TO_DATE(?,'YYYY-MM-DD')
          */
         query.append("SELECT * FROM MRP_GATHERING WHERE ( CASE ? WHEN 'claimDate' THEN\r\n"
               + "TO_DATE(CLAIM_DATE, 'YYYY-MM-DD') WHEN 'dueDate' THEN\r\n"
               + "TO_DATE(DUE_DATE, 'YYYY-MM-DD') END ) \r\n"
               + "BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')\r\n" + "");

         pstmt = conn.prepareStatement(query.toString());

         pstmt.setString(1, searchDateCondition);
         pstmt.setString(2, startDate);
         pstmt.setString(3, endDate);

         rs = pstmt.executeQuery();

         MrpGatheringTO bean = null;

         while (rs.next()) {

            bean = new MrpGatheringTO();

            bean.setMrpGatheringNo(rs.getString("MRP_GATHERING_NO"));
            bean.setOrderOrProductionStatus(rs.getString("ORDER_OR_PRODUCTION_STATUS"));
            bean.setItemCode(rs.getString("ITEM_CODE"));
            bean.setItemName(rs.getString("ITEM_NAME"));
            bean.setUnitOfMrpGathering(rs.getString("UNIT_OF_MRP_GATHERING"));
            bean.setClaimDate(rs.getString("CLAIM_DATE"));
            bean.setDueDate(rs.getString("DUE_DATE"));
            bean.setNecessaryAmount(rs.getInt("NECESSARY_AMOUNT"));

            mrpGatheringList.add(bean);

         }

         return mrpGatheringList;

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(pstmt, rs);

      }

   }

   public ArrayList<MrpGatheringTO> getMrpGathering(String mrpNoList) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : getMrpGathering 시작");
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      ArrayList<MrpGatheringTO> mrpGatheringList = new ArrayList<MrpGatheringTO>();

      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();

         /*
          * WITH MRP_NO_STR AS ( SELECT ? FROM DUAL ) ,
          * 
          * MRP_NO_LIST AS ( SELECT TRIM( REGEXP_SUBSTR( (SELECT * FROM MRP_NO_STR )
          * ,'[^,]+', 1, LEVEL ) ) AS MRP_NO FROM DUAL CONNECT BY REGEXP_SUBSTR( (SELECT
          * FROM MRP_NO_STR ) , '[^,]+', 1, LEVEL ) IS NOT NULL )
          * 
          * SELECT ( CASE TO_CHAR(ITEM_CLASSIFICATION) WHEN '원재료' THEN '구매' WHEN '반제품'
          * THEN '생산' WHEN '완제품' THEN '생산' ELSE TO_CHAR(ITEM_CLASSIFICATION) END ) AS
          * ORDER_OR_PRODUCTION_STATUS, ITEM_CODE,TRIM(ITEM_NAME) AS ITEM_NAME,
          * UNIT_OF_MRP AS UNIT_OF_MRP_GATHERING, MIN(ORDER_DATE) AS CLAIM_DATE,
          * MIN(REQUIRED_DATE) AS DUE_DATE, SUM(REQUIRED_AMOUNT) AS NECESSARY_AMOUNT FROM
          * ( SELECT * FROM MRP WHERE MRP_GATHERING_STATUS IS NULL AND MRP_NO IN ( SELECT
          * MRP_NO FROM MRP_NO_LIST ) ) GROUP BY ITEM_CLASSIFICATION, ITEM_CODE,
          * TRIM(ITEM_NAME), UNIT_OF_MRP ORDER BY CLAIM_DATE, ORDER_OR_PRODUCTION_STATUS;
          */

			/*
			 * SELECT DECODE(item_classification,'원재료', '구매', '생산') AS
			 * ORDER_OR_PRODUCTION_STATUS , ITEM_CODE , TRIM(ITEM_NAME) AS ITEM_NAME ,
			 * UNIT_OF_MRP , MIN(ORDER_DATE) AS CLAIM_DATE , MIN(REQUIRED_DATE) AS DUE_DATE
			 * , SUM(REQUIRED_AMOUNT) AS NECESSARY_AMOUNT FROM mrp WHERE mrp_gathering_no IS
			 * NULL AND MRP_NO IN ( WITH MRP_NO_STR AS (SELECT ? AS STR FROM DUAL ) SELECT
			 * TRIM(REGEXP_SUBSTR(MRP_NO_STR.STR,'[^,]+',1,LEVEL)) AS MRP_NO FROM MRP_NO_STR
			 * CONNECT BY LEVEL <= REGEXP_COUNT(STR,'[^,]+',1) ) GROUP BY
			 * ITEM_CLASSIFICATION , ITEM_CODE , ITEM_NAME , UNIT_OF_MRP ORDER BY CLAIM_DATE
			 * , ORDER_OR_PRODUCTION_STATUS;
			 */
         
			/*
			 * query.append("WITH MRP_NO_STR AS ( SELECT ? FROM DUAL ) ,\r\n" + "\r\n" +
			 * "MRP_NO_LIST AS (\r\n" +
			 * "SELECT TRIM( REGEXP_SUBSTR( (SELECT * FROM MRP_NO_STR ) ,'[^,]+', 1, LEVEL ) ) AS MRP_NO FROM DUAL \r\n"
			 * +
			 * "CONNECT BY REGEXP_SUBSTR( (SELECT * FROM MRP_NO_STR ) , '[^,]+', 1, LEVEL ) IS NOT NULL\r\n"
			 * + ") \r\n" + "\r\n" + "SELECT \r\n" +
			 * "    ( CASE TO_CHAR(ITEM_CLASSIFICATION) WHEN '원재료' THEN '구매' \r\n" +
			 * "            WHEN '반제품' THEN '생산' WHEN '완제품' THEN '생산' \r\n" +
			 * "            ELSE TO_CHAR(ITEM_CLASSIFICATION) END ) AS ORDER_OR_PRODUCTION_STATUS, \r\n"
			 * +
			 * "    ITEM_CODE, TRIM(ITEM_NAME) AS ITEM_NAME, UNIT_OF_MRP AS UNIT_OF_MRP_GATHERING, \r\n"
			 * + "    MIN(ORDER_DATE) AS CLAIM_DATE, MIN(REQUIRED_DATE) AS DUE_DATE, \r\n" +
			 * "    SUM(REQUIRED_AMOUNT) AS NECESSARY_AMOUNT\r\n" + "FROM ( \r\n" +
			 * "    SELECT * FROM MRP \r\n" + "    WHERE MRP_GATHERING_STATUS IS NULL  \r\n"
			 * + "    AND MRP_NO IN ( SELECT MRP_NO FROM MRP_NO_LIST )\r\n" + "    )\r\n" +
			 * "GROUP BY ITEM_CLASSIFICATION, ITEM_CODE, TRIM(ITEM_NAME), UNIT_OF_MRP \r\n"
			 * + "ORDER BY CLAIM_DATE, ORDER_OR_PRODUCTION_STATUS");
			 */
			
			/*
			 * query.append("SELECT DECODE(item_classification,'원재료',\r\n" +
			 * "              '구매',\r\n" +
			 * "              '생산') AS ORDER_OR_PRODUCTION_STATUS ,\r\n" +
			 * "       ITEM_CODE ,\r\n" + "       TRIM(ITEM_NAME) AS ITEM_NAME ,\r\n" +
			 * "       UNIT_OF_MRP AS UNIT_OF_MRP_GATHERING,\r\n" +
			 * "       MIN(ORDER_DATE)      AS CLAIM_DATE ,\r\n" +
			 * "       MIN(REQUIRED_DATE)   AS DUE_DATE ,\r\n" +
			 * "       SUM(REQUIRED_AMOUNT) AS NECESSARY_AMOUNT\r\n" + "FROM   mrp\r\n" +
			 * "WHERE  mrp_gathering_no IS NULL\r\n" +
			 * "AND    MRP_NO IN ( WITH MRP_NO_STR AS\r\n" +
			 * "                  (SELECT ? AS STR\r\n" +
			 * "                  FROM    DUAL\r\n" + "                  )\r\n" +
			 * "          SELECT   TRIM(REGEXP_SUBSTR(MRP_NO_STR.STR,'[^,]+',1,LEVEL)) AS MRP_NO\r\n"
			 * + "          FROM     MRP_NO_STR\r\n" +
			 * "                   CONNECT BY LEVEL <= REGEXP_COUNT(STR,'[^,]+',1) )\r\n" +
			 * "GROUP BY ITEM_CLASSIFICATION ,\r\n" + "         ITEM_CODE ,\r\n" +
			 * "         ITEM_NAME ,\r\n" + "         UNIT_OF_MRP \r\n" +
			 * "ORDER BY CLAIM_DATE ,\r\n" + "         ORDER_OR_PRODUCTION_STATUS");
			 */
			
			  query.append("SELECT ORDER_OR_PRODUCTION_STATUS\r\n" +
			  "			,ITEM_CODE\r\n" + "			,ITEM_NAME\r\n" +
			  "			,UNIT_OF_MRP_GATHERING\r\n" + "			,CLAIM_DATE\r\n" +
			  "			,DUE_DATE\r\n" +
			  "			,SUM(NECESSARY_AMOUNT) AS NECESSARY_AMOUNT\r\n" + "FROM(\r\n" +
			  "SELECT DECODE(item_classification,'원재료',\r\n" + "              '구매',\r\n" +
			  "              '생산') AS ORDER_OR_PRODUCTION_STATUS ,\r\n" +
			  "       ITEM_CODE ,\r\n" + "       TRIM(ITEM_NAME) AS ITEM_NAME ,\r\n" +
			  "       UNIT_OF_MRP AS UNIT_OF_MRP_GATHERING,\r\n" +
			  "       MIN(ORDER_DATE)      AS CLAIM_DATE ,\r\n" +
			  "       MIN(REQUIRED_DATE)   AS DUE_DATE ,\r\n" +
			  "       SUM(REQUIRED_AMOUNT) AS NECESSARY_AMOUNT\r\n" + "FROM   mrp\r\n" +
			  "WHERE  mrp_gathering_no IS NULL\r\n" +
			  "AND    MRP_NO IN ( WITH MRP_NO_STR AS\r\n" +
			  "                  (SELECT ? AS STR\r\n" +
			  "                  FROM    DUAL\r\n" + "                  )\r\n" +
			  "          SELECT   TRIM(REGEXP_SUBSTR(MRP_NO_STR.STR,'[^,]+',1,LEVEL)) AS MRP_NO\r\n"
			  + "          FROM     MRP_NO_STR\r\n" +
			  "                   CONNECT BY LEVEL <= REGEXP_COUNT(STR,'[^,]+',1) )\r\n" +
			  "GROUP BY ITEM_CLASSIFICATION ,\r\n" + "         ITEM_CODE ,\r\n" +
			  "         ITEM_NAME ,\r\n" + "         UNIT_OF_MRP \r\n" +
			  "ORDER BY CLAIM_DATE ,\r\n" + "         ORDER_OR_PRODUCTION_STATUS)\r\n" +
			  "GROUP BY ITEM_CODE,ITEM_NAME,ORDER_OR_PRODUCTION_STATUS,UNIT_OF_MRP_GATHERING,CLAIM_DATE,DUE_DATE\r\n"
			  + "ORDER BY CLAIM_DATE");
			 
         System.out.println(query.toString());
         pstmt = conn.prepareStatement(query.toString());
         pstmt.setString(1, mrpNoList);

         rs = pstmt.executeQuery();

         MrpGatheringTO bean = null;

         while (rs.next()) {

            bean = new MrpGatheringTO();

            bean.setOrderOrProductionStatus(rs.getString("ORDER_OR_PRODUCTION_STATUS"));
            bean.setItemCode(rs.getString("ITEM_CODE"));
            bean.setItemName(rs.getString("ITEM_NAME"));
            bean.setUnitOfMrpGathering(rs.getString("UNIT_OF_MRP_GATHERING"));
            bean.setClaimDate(rs.getString("CLAIM_DATE"));
            bean.setDueDate(rs.getString("DUE_DATE"));
            bean.setNecessaryAmount(rs.getInt("NECESSARY_AMOUNT"));

            mrpGatheringList.add(bean);
         }
         return mrpGatheringList;

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(pstmt, rs);

      }

   }

   public int selectMrpGatheringCount(String mrpGatheringRegisterDate) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : selectMrpGatheringCount 시작");
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         conn = dataSourceTransactionManager.getConnection();

         StringBuffer query = new StringBuffer();
         /*
          * SELECT * FROM MRP_GATHERING WHERE INSTR(MRP_GATHERING_NO, REPLACE( ? , '-' ,
          * '' ) ) > 0
          */
         query.append("SELECT * FROM MRP_GATHERING WHERE INSTR(MRP_GATHERING_NO, REPLACE( ? , '-' , '' ) ) > 0");
         pstmt = conn.prepareStatement(query.toString());
         pstmt.setString(1, mrpGatheringRegisterDate);

         rs = pstmt.executeQuery();

         TreeSet<Integer> intSet = new TreeSet<>();

         while (rs.next()) {
            String mrpGatheringNo = rs.getString("MRP_GATHERING_NO");

            // mrpGathering 일련번호에서 마지막 2자리만 가져오기
            int no = Integer
                  .parseInt(mrpGatheringNo.substring(mrpGatheringNo.length() - 2, mrpGatheringNo.length()));

            intSet.add(no);
         }

         if (intSet.isEmpty()) {
            return 1;
         } else {
            return intSet.pollLast() + 1; // 가장 높은 번호 + 1
         }

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(pstmt, rs);

      }
   }

   public void insertMrpGathering(MrpGatheringTO bean) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : insertMrpGathering 시작");
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();
         /*
          * Insert into MRP_GATHERING (MRP_GATHERING_NO, ORDER_OR_PRODUCTION_STATUS,
          * ITEM_CODE, ITEM_NAME, UNIT_OF_MRP_GATHERING, CLAIM_DATE, DUE_DATE,
          * NECESSARY_AMOUNT ) values ( ?, ?, ?, ?, ?, ?, ?, ?,
          * ? )
          */
         query.append("Insert into MRP_GATHERING \r\n"
               + "(MRP_GATHERING_NO, ORDER_OR_PRODUCTION_STATUS, ITEM_CODE, ITEM_NAME,\r\n"
               + "UNIT_OF_MRP_GATHERING, CLAIM_DATE, DUE_DATE, NECESSARY_AMOUNT, MRP_GATHERING_SEQ \r\n"
               + ") values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");

         pstmt = conn.prepareStatement(query.toString());

         pstmt.setString(1, bean.getMrpGatheringNo());
         pstmt.setString(2, bean.getOrderOrProductionStatus());
         pstmt.setString(3, bean.getItemCode());
         pstmt.setString(4, bean.getItemName());
         pstmt.setString(5, bean.getUnitOfMrpGathering());
         pstmt.setString(6, bean.getClaimDate());
         pstmt.setString(7, bean.getDueDate());
         pstmt.setInt(8, bean.getNecessaryAmount());
         pstmt.setInt(9, bean.getMrpGatheringSeq());
         
         rs = pstmt.executeQuery();

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(pstmt, rs);

      }

   }

   public void updateMrpGathering(MrpGatheringTO bean) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : updateMrpGathering 시작");
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();
         /*
          * UPDATE MRP_GATHERING SET ITEM_CODE = ? , ITEM_NAME = ? ,
          * UNIT_OF_MRP_GATHERING =? , NECESSARY_AMOUNT = ? , DUE_DATE = ? , CLAIM_DATE
          * =?, , ORDER_OR_PRODUCTION_STATUS = ? WHERE
          * MRP_GATHERING_NO = ?
          */
         query.append(
               "UPDATE MRP_GATHERING SET\r\n" + "ITEM_CODE = ? , ITEM_NAME = ? , UNIT_OF_MRP_GATHERING =? , \r\n"
                     + "NECESSARY_AMOUNT = ? , DUE_DATE = ? , CLAIM_DATE = ?, \r\n"
                     + "ORDER_OR_PRODUCTION_STATUS = ? \r\n"
                     + "WHERE MRP_GATHERING_NO = ?");

         pstmt = conn.prepareStatement(query.toString());

         pstmt.setString(1, bean.getOrderOrProductionStatus());
         pstmt.setString(2, bean.getItemCode());
         pstmt.setString(3, bean.getItemName());
         pstmt.setString(4, bean.getUnitOfMrpGathering());
         pstmt.setInt(5, bean.getNecessaryAmount());
         pstmt.setString(6, bean.getDueDate());
         pstmt.setString(7, bean.getClaimDate());
         pstmt.setString(8, bean.getMrpGatheringNo());

         rs = pstmt.executeQuery();

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(pstmt, rs);

      }

   }

   public void deleteMrpGathering(MrpGatheringTO bean) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : deleteMrpGathering 시작");
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();

         query.append("DELETE FROM MRP_GATHERING WHERE MRP_GATHERING_NO = ?");

         pstmt = conn.prepareStatement(query.toString());

         pstmt.setString(1, bean.getMrpGatheringNo());

         rs = pstmt.executeQuery();

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(pstmt, rs);

      }

   }

@Override
public void updateMrpGatheringContract(HashMap<String, String> parameter) {
	// TODO Auto-generated method stub
	  if (logger.isDebugEnabled()) {
	         logger.debug("MrpGatheringDAOImpl : updateMrpGatheringContract 시작");
	      }

	      Connection conn = null;
	  	  CallableStatement cs = null;
	      ResultSet rs = null;
	      String mrpGatheringNo = parameter.get("mrpGatheringNoList");
	      
	      try {
	         conn = dataSourceTransactionManager.getConnection();
	         StringBuffer query = new StringBuffer();
	       
	         query.append("{call P_MRP_GATHERING_CONTRACT(?,?,?)}");
	         System.out.println("      @ 프로시저 호출");
	         
	         cs = conn.prepareCall(query.toString());

	         	cs.setString(1, mrpGatheringNo);
				cs.registerOutParameter(2, OracleTypes.NUMBER);
				cs.registerOutParameter(3, OracleTypes.VARCHAR);
				cs.executeUpdate();
	         

	      } catch (Exception sqle) {

	         throw new DataAccessException(sqle.getMessage());

	      } finally {

	         dataSourceTransactionManager.close(cs, rs);

	      }
}

@Override
public int getMGSeqNo() {
	// TODO Auto-generated method stub
	 if (logger.isDebugEnabled()) {
         logger.debug("MrpGatheringDAOImpl : updateMrpGatheringContract 시작");
      }

      Connection conn = null;
  	  PreparedStatement cs = null;
      ResultSet rs = null;
      int seq=0;
      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();
       
         query.append("select MRP_GATHERING_SEQ.nextVAL from dual");
         cs = conn.prepareStatement(query.toString());

		rs=cs.executeQuery();
		if(rs.next()) {
            seq=rs.getInt("NEXTVAL");
         }

      } catch (Exception sqle) {

         throw new DataAccessException(sqle.getMessage());

      } finally {

         dataSourceTransactionManager.close(cs, rs);

      }
	return seq;
}
}