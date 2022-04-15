package com.estimulo.system.authorityManager.serviceFacade;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.system.authorityManager.applicationService.AuthorityGroupApplicationService;
import com.estimulo.system.authorityManager.applicationService.AuthorityGroupApplicationServiceImpl;
import com.estimulo.system.authorityManager.applicationService.LogInApplicationService;
import com.estimulo.system.authorityManager.applicationService.LogInApplicationServiceImpl;
import com.estimulo.system.authorityManager.applicationService.MenuApplicationService;
import com.estimulo.system.authorityManager.applicationService.MenuApplicationServiceImpl;
import com.estimulo.system.authorityManager.exception.IdNotFoundException;
import com.estimulo.system.authorityManager.exception.PwMissMatchException;
import com.estimulo.system.authorityManager.exception.PwNotFoundException;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(AuthorityManagerServiceFacadeImpl.class);

	// 싱글톤
	private static AuthorityManagerServiceFacade instance = new AuthorityManagerServiceFacadeImpl();
	private AuthorityManagerServiceFacadeImpl() {}
	public static AuthorityManagerServiceFacade getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("@ AuthorityManagerServiceFacadeImpl");
		}
		return instance;
	}

	// AS 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static LogInApplicationService logInAS = LogInApplicationServiceImpl.getInstance(); 
	private static MenuApplicationService menuAS = MenuApplicationServiceImpl.getInstance();
	private static AuthorityGroupApplicationService authorityGroupAS = AuthorityGroupApplicationServiceImpl.getInstance();

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : accessToAuthority 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);	//Auto Commit 막아주는 아이   
		EmpInfoTO TO = null;

		try {

			TO = logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);		//문제가 없을 경우 로그인 한 사람의 정보가 담겨짐
			dataSourceTransactionManager.commitTransaction();			// Commit 해주고 Close 해주는 메서드 

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : accessToAuthority 종료");
		}
		return TO;
	}

	@Override
	public String[] getAllMenuList() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getAllMenuList 시작");
		}
		
		dataSourceTransactionManager.beginTransaction(false);	
		System.out.println("		@ DB 연동 : getAllMenuList");

		String[] allMenuList = null;

		try {
		
			allMenuList = menuAS.getAllMenuList();
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getAllMenuList 종료");
		}
		return allMenuList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getAuthorityGroup 시작");
		}
		
		dataSourceTransactionManager.beginTransaction(false);	//DB 연동 메서드 인거 같다
		System.out.println("		@ DB 연동 : getAuthorityGroup");

		ArrayList<AuthorityGroupTO> authorityGroupTOList =  null;

		try {
		
			authorityGroupTOList = authorityGroupAS.getAuthorityGroup();
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getAuthorityGroup 종료");
		}
		return authorityGroupTOList;
	}

	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {
		// TODO Auto-generated method stub
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getUserAuthorityGroup 시작");
		}
		
		dataSourceTransactionManager.beginTransaction(false);	//DB 연동 메서드 인거 같다
		System.out.println("		@ DB 연동 : getUserAuthorityGroup");

		ArrayList<AuthorityGroupTO> authorityGroupTOList =  null;

		try {
		
			authorityGroupTOList = authorityGroupAS.getUserAuthorityGroup(empCode);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getUserAuthorityGroup 종료");
		}
		return authorityGroupTOList;
		
	}

	@Override
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {
		// TODO Auto-generated method stub
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : insertEmployeeAuthorityGroup 시작");
		}
		
		dataSourceTransactionManager.beginTransaction(false);	//DB 연동 메서드 인거 같다
		System.out.println("		@ DB 연동 : insertEmployeeAuthorityGroup");

		try {
		
			authorityGroupAS.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : insertEmployeeAuthorityGroup 종료");
		}
	}

	@Override
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getMenuAuthority 시작");
		}
		
		dataSourceTransactionManager.beginTransaction(false);	//DB 연동 메서드 인거 같다
		System.out.println("		@ DB 연동 : getMenuAuthority");

		ArrayList<MenuAuthorityTO> menuAuthorityTOList =  null;

		try {
		
			menuAuthorityTOList = menuAS.getMenuAuthority(authorityGroupCode);
			dataSourceTransactionManager.commitTransaction();
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : getMenuAuthority 종료");
		}
		return menuAuthorityTOList;
	}

	@Override
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : insertMenuAuthority 시작");
		}
		
		dataSourceTransactionManager.beginTransaction(false);	//DB 연동 메서드 인거 같다
		System.out.println("		@ DB 연동 : insertMenuAuthority");

		try {
		
			menuAS.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityManagerServiceFacadeImpl : insertMenuAuthority 종료");
		}
	}

}
