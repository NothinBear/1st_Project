package com.estimulo.system.authorityManager.applicationService;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.authorityManager.dao.AuthorityGroupDAO;
import com.estimulo.system.authorityManager.dao.AuthorityGroupDAOImpl;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.common.exception.DataAccessException;

public class AuthorityGroupApplicationServiceImpl implements AuthorityGroupApplicationService {
	
	private static Logger logger = LoggerFactory.getLogger(AuthorityGroupApplicationServiceImpl.class);
	
	// 싱글톤 
	private static AuthorityGroupApplicationService instance = new AuthorityGroupApplicationServiceImpl();
	
	private AuthorityGroupApplicationServiceImpl(){}
	
	public static AuthorityGroupApplicationService getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("@ AuthorityGroupApplicationServiceImpl 객체접근");
		}
		return instance;
	}
	
	// DAO 참조변수 선언 
	private static AuthorityGroupDAO authorityGroupDAO = AuthorityGroupDAOImpl.getInstance();
	
	
	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {   
		// TODO Auto-generated method stub
		
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupApplicationServiceImpl : getUserAuthorityGroup 시작");
	       }
	      
	      ArrayList<AuthorityGroupTO> authorityGroupTOList = null;
		
	      try {
	    	  authorityGroupTOList = authorityGroupDAO.selectUserAuthorityGroupList(empCode);

	       } catch (DataAccessException e) {
	          logger.error(e.getMessage());
	          throw e;
	       }

	       if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupApplicationServiceImpl : getUserAuthorityGroup 종료");
	       }
	       
	       return authorityGroupTOList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupApplicationServiceImpl : getAuthorityGroup 시작");
	       }
	      
	      ArrayList<AuthorityGroupTO> authorityGroupTOList = null;
		
	      try {
	    	  authorityGroupTOList = authorityGroupDAO.selectAuthorityGroupList();

	       } catch (DataAccessException e) {
	          logger.error(e.getMessage());
	          throw e;
	       }

	       if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupApplicationServiceImpl : getAuthorityGroup 종료");
	       }
	       
	       return authorityGroupTOList;
	}

	@Override
	public void insertEmployeeAuthorityGroup(
			String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {
		// TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupApplicationServiceImpl : insertEmployeeAuthorityGroup 시작");
	       }
	     
	      try {
	    	  // 해당 사원의 기존 그룹권한 정보 삭제
	    	  authorityGroupDAO.deleteEmployeeAuthorityGroup(empCode);
	    	  
	    	  // 새로운 그룹권한 정보 insert 
	    	  for(EmployeeAuthorityTO bean : employeeAuthorityTOList) {
	    		  
	    		  authorityGroupDAO.insertEmployeeAuthorityGroup(bean);
	    		  
	    	  }

	       } catch (DataAccessException e) {
	          logger.error(e.getMessage());
	          throw e;
	       }

	       if (logger.isDebugEnabled()) {
	          logger.debug("AuthorityGroupApplicationServiceImpl : insertEmployeeAuthorityGroup 종료");
	       }
	}
}
