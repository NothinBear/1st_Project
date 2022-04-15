package com.estimulo.system.authorityManager.applicationService;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;

public interface AuthorityGroupApplicationService {
	
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode);
	
	public ArrayList<AuthorityGroupTO> getAuthorityGroup();
	
	public void insertEmployeeAuthorityGroup(String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList);

}
