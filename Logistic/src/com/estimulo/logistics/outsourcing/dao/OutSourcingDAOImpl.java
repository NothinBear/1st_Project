package com.estimulo.logistics.outsourcing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class OutSourcingDAOImpl implements OutSourcingDAO{
   // SLF4J logger
   private static Logger logger = LoggerFactory.getLogger(OutSourcingDAOImpl.class);   
   
   // 싱글톤
   private static OutSourcingDAO instance;

   private OutSourcingDAOImpl() {
   }

   public static OutSourcingDAO getInstance() {
      
      if (logger.isDebugEnabled()) {
         logger.debug("@ BomDAOImpl 객체접근");
      }
      if(instance==null) {
          instance = new OutSourcingDAOImpl();
      }
      return instance;
   }

   // 참조변수 선언
   private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
         .getInstance();

   @Override
   public ArrayList<OutSourcingTO> selectOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
      // TODO Auto-generated method stub
      if (logger.isDebugEnabled()) {
         logger.debug("OutSourcingDAOImpl : selectOutSourcingList 시작");
      }
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      ArrayList<OutSourcingTO> outSourcingList = new ArrayList<OutSourcingTO>();
      try {
         conn = dataSourceTransactionManager.getConnection();
         StringBuffer query = new StringBuffer();
         query.append("SELECT OS.*\r\n"
               + "         ,I.ITEM_NAME\r\n"
               + "         ,C.CUSTOMER_NAME\r\n"
               + "         FROM OUTSOURCING OS,ITEM I,CUSTOMER C\r\n"
               + "         WHERE OS.ITEM_CODE=I.ITEM_CODE\r\n"
               + "         AND    OS.CUSTOMER_CODE=C.CUSTOMER_CODE\r\n"
               + "         AND      to_char(OUTSOURCING_DATE,'yyyy-mm-dd') BETWEEN ? AND ? \r\n");
         if(customerCode!="") {
            query.append("         AND OS.CUSTOMER_CODE=?\r\n");
         }
         if(itemCode!="") {
            query.append("         AND I.ITEM_CODE=?\r\n");
         }
         if(materialStatus!="") {
            query.append("         AND OS.MATERIAL_STATUS=?\r\n");
         }
         int onlyMaterial=3;
         pstmt = conn.prepareStatement(query.toString());
         pstmt.setString(1, fromDate);
         pstmt.setString(2, toDate);
         if(customerCode!="") {
            pstmt.setString(3,customerCode);
            onlyMaterial=5;
         }
         if(itemCode!="") {
            pstmt.setString(4, itemCode);
         }
         if(materialStatus!="") {
            pstmt.setString(onlyMaterial,materialStatus);
         }
         System.out.println(query.toString());
         rs = pstmt.executeQuery();

         OutSourcingTO bean = null; 
         
         while (rs.next()) {
            bean = new OutSourcingTO();
            
              bean.setCheckStatus(rs.getString("CHECK_STATUS"));
              bean.setCompleteStatus(rs.getString("COMPLETE_STATUS"));
              bean.setCustomerCode(rs.getString("CUSTOMER_CODE"));
              bean.setInstructAmount(rs.getString("INSTRUCTION_AMOUNT"));
              bean.setInstructDate(rs.getString("INSTRUCT_DATE").substring(0,10));
              bean.setItemCode(rs.getString("ITEM_CODE"));
              bean.setItemName(rs.getString("ITEM_NAME"));
              bean.setMaterialStatus(rs.getString("MATERIAL_STATUS"));
              bean.setOutsourcingDate(rs.getString("OUTSOURCING_DATE").substring(0,10));
              bean.setOutsourcingNo(rs.getString("OUTSOURCING_NO"));
              bean.setTotalPrice(rs.getString("TOTAL_PRICE"));
              bean.setUnitPrice(rs.getString("UNIT_PRICE"));
              bean.setCompleteDate(rs.getString("COMPLETE_DATE").substring(0,10));
              bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
             
            
            outSourcingList.add(bean);
         }
         return outSourcingList;
      } catch (Exception sqle) {
         throw new DataAccessException(sqle.getMessage());
      } finally {
         dataSourceTransactionManager.close(pstmt, rs);
         if (logger.isDebugEnabled()) {
            logger.debug("OutSourcingDAOImpl : selectOutSourcingList 종료");
         }
      }
   }
}