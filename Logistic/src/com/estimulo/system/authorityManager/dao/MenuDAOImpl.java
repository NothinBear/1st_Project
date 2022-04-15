package com.estimulo.system.authorityManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.authorityManager.to.MenuTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class MenuDAOImpl implements MenuDAO{
	
	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(UserMenuDAOImpl.class);

	// 싱글톤
	private static MenuDAO instance = new MenuDAOImpl();
	private MenuDAOImpl() {}
	public static MenuDAO getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("@ MenuDAOImpl 객체접근");
		}
		return instance;
	}

	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();
	

	@Override
	public ArrayList<MenuTO> selectAllMenuList() {
		// TODO Auto-generated method stub
		
		if (logger.isDebugEnabled()) {
			logger.debug("MenuDAOImpl : selectAllMenuList 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<MenuTO> allMenuTOList = new ArrayList<>();
		
		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from menu order by menu_code");
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			
			MenuTO bean = null;
			
			while (rs.next()) {
				bean = new MenuTO();
				
				bean.setMenuCode(rs.getString("MENU_CODE"));
				bean.setParentMenuCode(rs.getString("PARENT_MENU_CODE"));
				bean.setMenuName(rs.getString("MENU_NAME"));
				bean.setMenuLevel(rs.getString("MENU_LEVEL"));
				bean.setMenuURL(rs.getString("MENU_URL"));
				bean.setMenuStatus(rs.getString("MENU_STATUS"));
				bean.setChildMenu(rs.getString("CHILD_MENU"));
				bean.setNavMenu(rs.getString("NAV_MENU"));
				bean.setNavMenuName(rs.getString("NAV_MENU_NAME"));    
				
				allMenuTOList.add(bean);
			}

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);
		}
		return allMenuTOList;		
	}

}
