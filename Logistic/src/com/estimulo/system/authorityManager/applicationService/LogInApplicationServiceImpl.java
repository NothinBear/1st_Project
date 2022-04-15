package com.estimulo.system.authorityManager.applicationService;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.hr.affair.dao.EmpSearchingDAO;
import com.estimulo.hr.affair.dao.EmpSearchingDAOImpl;
import com.estimulo.hr.affair.dao.EmployeeSecretDAO;
import com.estimulo.hr.affair.dao.EmployeeSecretDAOImpl;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;
import com.estimulo.system.authorityManager.dao.AuthorityGroupDAO;
import com.estimulo.system.authorityManager.dao.AuthorityGroupDAOImpl;
import com.estimulo.system.authorityManager.dao.MenuAuthorityDAO;
import com.estimulo.system.authorityManager.dao.MenuAuthorityDAOImpl;
import com.estimulo.system.authorityManager.exception.IdNotFoundException;
import com.estimulo.system.authorityManager.exception.PwMissMatchException;
import com.estimulo.system.authorityManager.exception.PwNotFoundException;
import com.estimulo.system.authorityManager.to.AuthorityGroupMenuTO;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.common.exception.DataAccessException;

public class LogInApplicationServiceImpl implements LogInApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(LogInApplicationServiceImpl.class);

	// 싱글톤
	private static LogInApplicationService instance = new LogInApplicationServiceImpl();

	private LogInApplicationServiceImpl() {}

	public static LogInApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ LogInApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// DAO 참조변수 선언
	private static EmpSearchingDAO empSearchDAO = EmpSearchingDAOImpl.getInstance();
	private static EmployeeSecretDAO empSecretDAO = EmployeeSecretDAOImpl.getInstance();
	private static MenuAuthorityDAO menuAuthorityDAO = MenuAuthorityDAOImpl.getInstance();
	private static AuthorityGroupDAO authorityGroupDAO = AuthorityGroupDAOImpl.getInstance();

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException, DataAccessException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : accessToAuthority 시작");
		}

		EmpInfoTO bean = null;

		try {

			bean = checkEmpInfo(companyCode, workplaceCode, inputId);	// 데이터 베이스 에서 우리가 로그인 화면에서 입력한 값을 보내줘서 비교한후 있으면 들고와서 그사람의 정보를 bean 에 담는다
			checkPassWord(companyCode, bean.getEmpCode(), inputPassWord); // 비밀번호를 확인 해주는 메서드 					
			
			String[] userAuthorityGroupList = getUserAuthorityGroup(bean.getEmpCode()); // 사용자의 권한그룹 리스트를 가져오는 메서드
			bean.setAuthorityGroupList(userAuthorityGroupList); 
			 
			String[] menuList = getUserAuthorityGroupMenu(bean.getEmpCode()); // 궘한그룹별 메뉴 리스트를 가져오는 메서드 
			bean.setAuthorityGroupMenuList(menuList); 
 
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : accessToAuthority 종료");
		}
		return bean;
	}

	private EmpInfoTO checkEmpInfo(String companyCode, String workplaceCode, String inputId)
			throws IdNotFoundException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkEmpInfo 시작");
		}

		EmpInfoTO bean = null;
		ArrayList<EmpInfoTO> empInfoTOList = null;

		try {

			empInfoTOList = empSearchDAO.getTotalEmpInfo(companyCode, workplaceCode, inputId);

			if (empInfoTOList.size() == 1) {

				for (EmpInfoTO e : empInfoTOList) {
					bean = e;
				}

			} else if (empInfoTOList.size() == 0) {
				throw new IdNotFoundException("입력된 정보에 해당하는 사원은 없습니다.");
			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkEmpInfo 종료");
		}
		return bean;
	}

	private void checkPassWord(String companyCode, String empCode, String inputPassWord)
			throws PwMissMatchException, PwNotFoundException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkPassWord 시작");
		}

		try {

			EmployeeSecretTO bean = empSecretDAO.selectUserPassWord(companyCode, empCode);

			StringBuffer userPassWord = new StringBuffer();
			if (bean != null) {
				userPassWord.append(bean.getUserPassword());

				// 회원ID 는 있으나 passWord Data 가 없는 경우
			} else if (bean == null || bean.getUserPassword().equals("") || bean.getUserPassword() == null) {
				throw new PwNotFoundException("비밀번호 정보를 찾을 수 없습니다.");
			}

			if (!inputPassWord.equals(userPassWord.toString())) {
				throw new PwMissMatchException("비밀번호가 가입된 정보와 같지 않습니다.");
			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkPassWord 종료");
		}
	}
		
		private String[] getUserAuthorityGroupMenu(String empCode) {

			if (logger.isDebugEnabled()) {
				logger.debug("LogInApplicationServiceImpl : getUserAuthorityGroupMenu 시작");
			}

			String[] authorityGroupMenuList = null;

			try {

				ArrayList<AuthorityGroupMenuTO> authorityGroupMenuTOList = menuAuthorityDAO.selectUserMenuAuthorityList(empCode);

				// ArrayList 요소를 배열에 담음 
				int size = authorityGroupMenuTOList.size();
				authorityGroupMenuList = new String[size];
				  for(int i=0; i<size; i++ ) { 
					  authorityGroupMenuList[i] = authorityGroupMenuTOList.get(i).getMenuCode(); 
				  }

			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}
   
			if (logger.isDebugEnabled()) {
				logger.debug("LogInApplicationServiceImpl : getUserAuthorityGroupMenu 종료");
			}
			return authorityGroupMenuList;
		}
		
		private String[] getUserAuthorityGroup(String empCode) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("LogInApplicationServiceImpl : getUserAuthorityGroup 시작");
			}
			
			String[] userAuthorityGroupList = null;
			
			try {
				
				ArrayList<AuthorityGroupTO> userAuthorityGroupTOList = authorityGroupDAO.selectUserAuthorityGroupList(empCode);
				
				// 해당 사용자에게 부여되지 않은 권한그룹 목록 삭제 
				Iterator<AuthorityGroupTO> iter=userAuthorityGroupTOList.iterator();
				while(iter.hasNext()){	
					if(iter.next().getAuthority().equals("0")) {
						iter.remove();
					}
				}
				
				// ArrayList 요소를 배열에 담음 
				int size = userAuthorityGroupTOList.size();
				userAuthorityGroupList = new String[size];
				  for(int i=0; i<size; i++ ) { 
					  userAuthorityGroupList[i] = userAuthorityGroupTOList.get(i).getUserAuthorityGroupCode(); 
				  }
				
					
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				throw e;
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("LogInApplicationServiceImpl : getUserAuthorityGroup 종료");
			}
			return userAuthorityGroupList;
		}

	}