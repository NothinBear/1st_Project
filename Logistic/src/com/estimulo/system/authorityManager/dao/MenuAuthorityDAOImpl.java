package com.estimulo.system.authorityManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.authorityManager.to.AuthorityGroupMenuTO;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class MenuAuthorityDAOImpl implements MenuAuthorityDAO{
	
	// logger 
	private static Logger logger = LoggerFactory.getLogger(UserMenuDAOImpl.class);
	
	// 싱글톤
	private static MenuAuthorityDAO instance = new MenuAuthorityDAOImpl();
	private MenuAuthorityDAOImpl() {}
	   public static MenuAuthorityDAO getInstance() {      
		      if (logger.isDebugEnabled()) {
		         logger.debug("@ MenuAuthorityDAOImpl 객체접근");
		      }
		      return instance;
		   }
	
	// 참조변수 선언 
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	
	@Override
	public ArrayList<AuthorityGroupMenuTO> selectUserMenuAuthorityList(String empCode) {
		// TODO Auto-generated method stub

		      if (logger.isDebugEnabled()) {
		          logger.debug("MenuAuthorityDAOImpl : selectUserMenuAuthorityList 시작");
		       }
			
		      Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;

		      ArrayList<AuthorityGroupMenuTO> authorityGroupMenuList = new ArrayList<>();
		      
		      try {
		          conn = dataSourceTransactionManager.getConnection();
		          StringBuffer query = new StringBuffer();
		          query.append("SELECT m.menu_code, \r\n" + 
		          		"       m.MENU_NAME \r\n" + 
		          		"  FROM AUTHORITY_GROUP_MENU a, \r\n" + 
		          		"       MENU m \r\n" + 
		          		" WHERE a.MENU_CODE = m.MENU_CODE \r\n" + 
		          		"       AND AUTHORITY_GROUP_CODE IN \r\n" + 
		          		"       (SELECT AUTHORITY_GROUP_CODE \r\n" + 
		          		"         FROM EMPLOYEE_AUTHORITY \r\n" + 
		          		"        WHERE EMP_CODE = ?\r\n" + 
		          		"       )");

		          pstmt = conn.prepareStatement(query.toString());
		          pstmt.setString(1, empCode);
		          rs = pstmt.executeQuery();

		          AuthorityGroupMenuTO bean = null;
		          
		          while (rs.next()) {
		             bean = new AuthorityGroupMenuTO();					
		             bean.setMenuCode(rs.getString("MENU_CODE"));
		             bean.setMenuName(rs.getString("MENU_NAME"));
		             authorityGroupMenuList.add(bean);
		          }
		          
		       } catch (Exception sqle) {

		          throw new DataAccessException(sqle.getMessage());

		       } finally {

		          dataSourceTransactionManager.close(pstmt, rs);

		       }

		       return authorityGroupMenuList;
		}
	
	@Override
	public ArrayList<MenuAuthorityTO> selectMenuAuthorityList(String authorityGroupCode) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("MenuAuthorityDAOImpl : selectMenuAuthorityList 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      ArrayList<MenuAuthorityTO> menuAuthorityTOList = new ArrayList<>();
	      
	      try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("SELECT m.MENU_CODE, \r\n" + 
	          		"       m.MENU_NAME,\r\n" + 
	          		"       m.MENU_LEVEL, \r\n" + 
	          		"				a. AUTHORITY_GROUP_CODE,\r\n" + 
	          		"        CASE \r\n" + 
	          		"        WHEN  MENU_LEVEL = '0' THEN '-1'\r\n" + 
	          		"        WHEN MENU_LEVEL = '1' AND CHILD_MENU IS NOT NULL THEN '-2' \r\n" + 
	          		"        WHEN AUTHORITY_GROUP_CODE IS NOT NULL AND MENU_LEVEL <> '0' THEN '1'\r\n" + 
	          		"        ELSE '0' END authority \r\n" + 
	          		"  FROM MENU m, \r\n" + 
	          		"       (SELECT * \r\n" + 
	          		"         FROM AUTHORITY_GROUP_MENU \r\n" + 
	          		"        WHERE AUTHORITY_GROUP_CODE=? \r\n" + 
	          		"       ) a \r\n" + 
	          		" WHERE m.MENU_CODE = a.MENU_CODE(+) \r\n" + 
	          		"ORDER BY m.MENU_CODE");

	          pstmt = conn.prepareStatement(query.toString());
	          pstmt.setString(1, authorityGroupCode);
	          rs = pstmt.executeQuery();
     
	          MenuAuthorityTO bean = null;
	          
	          while (rs.next()) {

	             bean = new MenuAuthorityTO();					

	             bean.setAuthority(rs.getString("authority"));
	             bean.setMenuCode(rs.getString("menu_code"));
	             bean.setMenuName(rs.getString("menu_name"));
	             bean.setMenuLevel(rs.getString("menu_level"));
	                
	             menuAuthorityTOList.add(bean);
	          } 
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }

	       return menuAuthorityTOList;
	}
	
	@Override
	public void deleteMenuAuthority(String authorityGroupCode) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("MenuAuthorityDAOImpl : deleteMenuAuthority 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("DELETE AUTHORITY_GROUP_MENU WHERE AUTHORITY_GROUP_CODE=?");

	          pstmt = conn.prepareStatement(query.toString());
	          pstmt.setString(1, authorityGroupCode);
	          rs = pstmt.executeQuery();
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }
	}

	@Override
	public void insertMenuAuthority(MenuAuthorityTO bean) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("MenuAuthorityDAOImpl : insertMenuAuthority 시작");
	       }
		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
        try {
	          conn = dataSourceTransactionManager.getConnection();
	          StringBuffer query = new StringBuffer();
	          query.append("INSERT INTO AUTHORITY_GROUP_MENU VALUES(?,?)");

	          pstmt = conn.prepareStatement(query.toString());
	          pstmt.setString(1, bean.getAuthorityGroupCode());
	          pstmt.setString(2, bean.getMenuCode());
	          rs = pstmt.executeQuery();
	          
	       } catch (Exception sqle) {

	          throw new DataAccessException(sqle.getMessage());

	       } finally {

	          dataSourceTransactionManager.close(pstmt, rs);

	       }
	}
}
