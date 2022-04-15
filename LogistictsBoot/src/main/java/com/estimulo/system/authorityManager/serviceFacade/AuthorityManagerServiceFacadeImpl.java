package com.estimulo.system.authorityManager.serviceFacade;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	// AS 참조변수 선언
	@Autowired
	private LogInApplicationService logInAS;
	@Autowired
	private MenuApplicationService menuAS;
	@Autowired
	private AuthorityGroupApplicationService authorityGroupAS;

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {
			
		return logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);
	}

	@Override
	public String[] getAllMenuList() {
		
		System.out.print("getAllMenuList&&&&&&&&&&&&&&&&&&&&&");
			
		return menuAS.getAllMenuList();
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
			
		return authorityGroupAS.getAuthorityGroup();
	}

	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {
		// TODO Auto-generated method stub
			
		return authorityGroupAS.getUserAuthorityGroup(empCode);
		
	}

	@Override
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {
		// TODO Auto-generated method stub
		
			authorityGroupAS.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);
	}

	@Override
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {
		
		return menuAS.getMenuAuthority(authorityGroupCode);
	}

	@Override
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
			menuAS.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
	}

}
