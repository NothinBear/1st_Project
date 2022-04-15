package com.estimulo.system.authorityManager.applicationService;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.system.authorityManager.exception.IdNotFoundException;
import com.estimulo.system.authorityManager.exception.PwMissMatchException;
import com.estimulo.system.authorityManager.exception.PwNotFoundException;
import com.estimulo.system.common.exception.DataAccessException;

public interface LogInApplicationService {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException, DataAccessException;

}
