package com.estimulo.system.authorityManager.serviceFacade;

import java.util.ArrayList;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.system.authorityManager.applicationService.AuthorityGroupApplicationService;
import com.estimulo.system.authorityManager.applicationService.LogInApplicationService;
import com.estimulo.system.authorityManager.applicationService.MenuApplicationService;
import com.estimulo.system.authorityManager.exception.IdNotFoundException;
import com.estimulo.system.authorityManager.exception.PwMissMatchException;
import com.estimulo.system.authorityManager.exception.PwNotFoundException;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;

public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	// AS 참조변수 선언
	private LogInApplicationService logInAS;
	private MenuApplicationService menuAS;
	private AuthorityGroupApplicationService authorityGroupAS;
	public void setLogInAS(LogInApplicationService logInAS) {
		this.logInAS = logInAS;
	}
	public void setMenuAS(MenuApplicationService menuAS) {
		this.menuAS = menuAS;
	}
	public void setAuthorityGroupAS(AuthorityGroupApplicationService authorityGroupAS) {
		this.authorityGroupAS = authorityGroupAS;
	}
	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

		EmpInfoTO TO = null;

			TO = logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);		//문제가 없을 경우 로그인 한 사람의 정보가 담겨짐
			
		return TO;
	}

	@Override
	public String[] getAllMenuList() {
		

		String[] allMenuList = null;
		
			allMenuList = menuAS.getAllMenuList();
			
		return allMenuList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
		// TODO Auto-generated method stub

		ArrayList<AuthorityGroupTO> authorityGroupTOList =  null;

			authorityGroupTOList = authorityGroupAS.getAuthorityGroup();
			
		return authorityGroupTOList;
	}

	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {
		// TODO Auto-generated method stub
		

		ArrayList<AuthorityGroupTO> authorityGroupTOList =  null;

			authorityGroupTOList = authorityGroupAS.getUserAuthorityGroup(empCode);
			
		return authorityGroupTOList;
		
	}

	@Override
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {
		// TODO Auto-generated method stub
		
		
			authorityGroupAS.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);
	}

	@Override
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {
		
		ArrayList<MenuAuthorityTO> menuAuthorityTOList =  null;

		
			menuAuthorityTOList = menuAS.getMenuAuthority(authorityGroupCode);
			
		return menuAuthorityTOList;
	}

	@Override
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
		// TODO Auto-generated method stub
			menuAS.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
	}

}
