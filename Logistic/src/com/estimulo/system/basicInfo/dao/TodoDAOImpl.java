package com.estimulo.system.basicInfo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.basicInfo.to.TodoTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

import oracle.jdbc.internal.OracleTypes;

public class TodoDAOImpl implements TodoDAO{
	// SLF4J logger
		private static Logger logger = LoggerFactory.getLogger(TodoDAOImpl.class);

		// 싱글톤
		private static TodoDAO instance = new TodoDAOImpl();

		private TodoDAOImpl() {}

		public static TodoDAO getInstance() {

			if (logger.isDebugEnabled()) {
				logger.debug("@ TodoDAOImpl 객체접근");
			}

			return instance;
		}
		// 참조변수 선언
		private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
				.getInstance();
		
		@Override
		public ArrayList<TodoTO> selectTodoList() {
			if (logger.isDebugEnabled()) {
				logger.debug("TodoDAOImpl : selectTodoList 시작");
			}

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			ArrayList<TodoTO> TodoTOList = new ArrayList<TodoTO>();

			try {
				conn = dataSourceTransactionManager.getConnection();
				StringBuffer query = new StringBuffer();
				
				query.append("SELECT * FROM TODO");
				pstmt = conn.prepareStatement(query.toString());
				

				rs = pstmt.executeQuery();

				TodoTO bean = null;

				while (rs.next()) {

					bean = new TodoTO();
					
					bean.setEmpCode(rs.getString("EMP_CODE"));
					bean.setEmpName(rs.getString("EMP_NAME"));
					bean.setBe(rs.getString("BE"));
					bean.setIng(rs.getString("ING"));
					bean.setDone(rs.getString("DONE"));
					bean.setUpdateDate(rs.getString("UPDATE_DATE"));
					bean.setMag(rs.getString("MAG"));
					bean.setContentStatus(rs.getString("CONTENT_STATUS"));
					bean.setTodoNum(rs.getString("TODO_NUM"));
					TodoTOList.add(bean);
					
				}

				return TodoTOList;

			} catch (Exception sqle) {

				throw new DataAccessException(sqle.getMessage());

			} finally {

				dataSourceTransactionManager.close(pstmt, rs);

			}
		}

		@Override
		public HashMap<String,Object> addNewTodo(String empCode, String empName, String status, String mag, String content) {
			if (logger.isDebugEnabled()) {
				logger.debug("TodoDAOImpl : addNewTodo 시작");
			}

				Connection conn = null;
				CallableStatement cs = null;
				ResultSet rs = null;
				HashMap<String,Object> resultMap = new HashMap<>();

			try {
				conn = dataSourceTransactionManager.getConnection();
				StringBuffer query = new StringBuffer();
				query.append(" {call P_INSERT_NEW_TODO(?,?,?,?,?,?,?)} ");
				
				cs = conn.prepareCall(query.toString());
				cs.setString(1, empCode);
				cs.setString(2, empName);
				cs.setString(3, status);
				cs.setString(4, mag);
				cs.setString(5, content);
				cs.registerOutParameter(6, OracleTypes.NUMBER);
				cs.registerOutParameter(7, OracleTypes.VARCHAR);
				cs.executeUpdate();
				
				int errorCode = cs.getInt(3);
				String errorMsg = cs.getString(4);
				
				resultMap.put("errorCode",errorCode); //성공시 0
				resultMap.put("errorMsg",errorMsg); //## insert완료 ##
				
				return resultMap;
			} catch (Exception sqle) {

				throw new DataAccessException(sqle.getMessage());

			} finally {

				dataSourceTransactionManager.close(cs, rs);

			}
		}

		@Override
		public void deleteTodo(String todoNum) {
			if (logger.isDebugEnabled()) {
				logger.debug("TodoDAOImpl : selectTodoList 시작");
			}

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;


			try {
				conn = dataSourceTransactionManager.getConnection();
				StringBuffer query = new StringBuffer();
				
				query.append("DELETE FROM TODO WHERE TODO_NUM = ?");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, todoNum);

				rs = pstmt.executeQuery();


			} catch (Exception sqle) {

				throw new DataAccessException(sqle.getMessage());

			} finally {

				dataSourceTransactionManager.close(pstmt, rs);

			}
			
		}
}
