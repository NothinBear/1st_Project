package com.estimulo.system.authorityManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class AuthorityGroupDAOImpl implements AuthorityGroupDAO{
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(UserMenuDAOImpl.class);
	
	// 싱글톤 
	private static AuthorityGroupDAO instance = new AuthorityGroupDAOImpl();
	private AuthorityGroupDAOImpl() {}
	public static AuthorityGroupDAO getInstance() {    
		      if (logger.isDebugEnabled()) {
		         logger.debug("@ AuthorityGroupDAOImpl 객체접근");
		      }
		      return instance;
		   }
	
	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	
	@Override
	public ArrayList<AuthorityGroupTO>  selectUserAuthorityGroupList(String empCode) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupDAOImpl : selectUserAuthorityGroupList 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      ArrayList<AuthorityGroupTO> authorityGroupTOList = new ArrayList<>();
	      
	      try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("SELECT ag.*, DECODE(emp_code, NULL, 0, 1) AS authority \r\n" + 
	          		"FROM AUTHORITY_GROUP ag, (SELECT * \r\n" + 
	          		"  FROM EMPLOYEE_AUTHORITY \r\n" + 
	          		" WHERE emp_code=?) a \r\n" + 
	          		" WHERE ag.AUTHORITY_GROUP_CODE = a.AUTHORITY_GROUP_CODE(+)\r\n" + 
	          		" ORDER BY ag.AUTHORITY_GROUP_CODE");

	          pstmt = conn.prepareStatement(query.toString());
	          pstmt.setString(1, empCode);
	          rs = pstmt.executeQuery();

	          AuthorityGroupTO bean = null;
	          
	          while (rs.next()) {

	             bean = new AuthorityGroupTO();					

	             bean.setAuthorityGroupCode(rs.getString("authority_group_code"));
	             bean.setAuthorityGroupName(rs.getString("authority_group_name"));
	             bean.setAuthority(rs.getString("authority"));  
	             
	             authorityGroupTOList.add(bean);
	          } 
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }

	       return authorityGroupTOList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> selectAuthorityGroupList() {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupDAOImpl : selectAuthorityGroupList 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      ArrayList<AuthorityGroupTO> authorityGroupTOList = new ArrayList<>();
	      
	      try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("SELECT * FROM AUTHORITY_GROUP");

	          pstmt = conn.prepareStatement(query.toString());
	          rs = pstmt.executeQuery();

	          AuthorityGroupTO bean = null;
	          
	          while (rs.next()) {

	             bean = new AuthorityGroupTO();					

	             bean.setAuthorityGroupCode(rs.getString("authority_group_code"));
	             bean.setAuthorityGroupName(rs.getString("authority_group_name"));
	             
	             authorityGroupTOList.add(bean);
	          } 
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }

	       return authorityGroupTOList;
	}

	@Override
	public void insertEmployeeAuthorityGroup(EmployeeAuthorityTO bean) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupDAOImpl : insertEmployeeAuthorityGroup 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
        try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("INSERT INTO EMPLOYEE_AUTHORITY VALUES(?,?)");

	          pstmt = conn.prepareStatement(query.toString());
	          pstmt.setString(1, bean.getEmpCode());
	          pstmt.setString(2, bean.getUserAuthorityGroupCode());
	          rs = pstmt.executeQuery();
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }
	}

	@Override
	public void deleteEmployeeAuthorityGroup(String empCode) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupDAOImpl : deleteEmployeeAuthorityGroup 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("DELETE EMPLOYEE_AUTHORITY WHERE EMP_CODE=?");

	          pstmt = conn.prepareStatement(query.toString());
	          pstmt.setString(1, empCode);
	          rs = pstmt.executeQuery();
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }
		}

}
