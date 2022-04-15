package com.estimulo.system.authorityManager.serviceFacade;

import java.util.ArrayList;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.system.authorityManager.exception.IdNotFoundException;
import com.estimulo.system.authorityManager.exception.PwMissMatchException;
import com.estimulo.system.authorityManager.exception.PwNotFoundException;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;

public interface AuthorityManagerServiceFacade {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

	public String[] getAllMenuList();

	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode);
	
	public ArrayList<AuthorityGroupTO> getAuthorityGroup();

	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode);
	
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList);
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList);
	
}
