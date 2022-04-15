package com.estimulo.hr.affair.serviceFacade;

import java.util.ArrayList;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.hr.affair.applicationService.EmpApplicationService;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;

@Service
public class AffairServiceFacadeImpl implements AffairServiceFacade {
	@Autowired
	private EmpApplicationService empApplicationService;

	@Override
	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray) {
		
		ArrayList<EmpInfoTO> empList = null;


			empList = empApplicationService.getAllEmpList(searchCondition, paramArray);

		return empList;
	}

	@Override
	public EmpInfoTO getEmpInfo(String companyCode, String empCode) {

		EmpInfoTO TO = null;

			TO = empApplicationService.getEmpInfo(companyCode, empCode);

		return TO;
	}

	@Override
	public String getNewEmpCode(String companyCode) {

		String newEmpCode = null;

			newEmpCode = empApplicationService.getNewEmpCode(companyCode);

		return newEmpCode;
	}

	@Override
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList) {

		HashMap<String, Object> resultMap = null;

			resultMap = empApplicationService.batchEmpBasicListProcess(empBasicList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList) {
		
		HashMap<String, Object> resultMap = null;

			resultMap = empApplicationService.batchEmpDetailListProcess(empDetailList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList) {
		
		HashMap<String, Object> resultMap = null;

			resultMap = empApplicationService.batchEmpSecretListProcess(empSecretList);

		return resultMap;
	}

	@Override
	public Boolean checkUserIdDuplication(String companyCode, String newUserId) {
		
		Boolean duplicated = false;

			duplicated = empApplicationService.checkUserIdDuplication(companyCode, newUserId);

		return duplicated;
	}

	@Override
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode) {

		Boolean duplicated = false;

			duplicated = empApplicationService.checkEmpCodeDuplication(companyCode, newEmpCode);

		return duplicated;
	}
	@Override
	public void updateEmpImg(EmployeeDetailTO employeeDetailTO) {
		
		empApplicationService.updateEmpImg(employeeDetailTO);
		
	}
}
