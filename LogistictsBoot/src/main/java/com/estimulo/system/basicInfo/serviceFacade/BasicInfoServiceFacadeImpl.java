package com.estimulo.system.basicInfo.serviceFacade;

import java.util.ArrayList;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.system.basicInfo.applicationService.*;
import com.estimulo.system.basicInfo.to.CompanyTO;
import com.estimulo.system.basicInfo.to.CustomerTO;
import com.estimulo.system.basicInfo.to.DepartmentTO;
import com.estimulo.system.basicInfo.to.FinancialAccountAssociatesTO;
import com.estimulo.system.basicInfo.to.WorkplaceTO;


@Service
public class BasicInfoServiceFacadeImpl implements BasicInfoServiceFacade {
	// 참조변수 선언
	@Autowired
	private CustomerApplicationService customerApplicationService;
	@Autowired
	private FinancialAccountAssociatesApplicationService financialAccountAssociatesApplicationService;
	@Autowired
	private CompanyApplicationService companyApplicationService;
	@Autowired
	private WorkplaceApplicationService workplaceApplicationService;
	@Autowired
	private DepartmentApplicationService departmentApplicationService;


	@Override
	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode,
			String itemGroupCode) {

		ArrayList<CustomerTO> customerList = null;

		customerList = customerApplicationService.getCustomerList(searchCondition, companyCode, workplaceCode,
				itemGroupCode);

		return customerList;
	}

	@Override
	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {

		HashMap<String, Object> resultMap = null;

		resultMap = customerApplicationService.batchCustomerListProcess(customerList);

		return resultMap;

	}

	@Override
	public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
			String workplaceCode) {

		ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;

		financialAccountAssociatesList = financialAccountAssociatesApplicationService
				.getFinancialAccountAssociatesList(searchCondition, workplaceCode);

		return financialAccountAssociatesList;

	}

	@Override
	public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList) {

		HashMap<String, Object> resultMap = null;

		resultMap = financialAccountAssociatesApplicationService
				.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);

		return resultMap;
	}

	@Override
	public ArrayList<CompanyTO> getCompanyList() {

		ArrayList<CompanyTO> companyList = null;

		companyList = companyApplicationService.getCompanyList();

		return companyList;
	}

	@Override
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {
		ArrayList<WorkplaceTO> workplaceList = null;

		workplaceList = workplaceApplicationService.getWorkplaceList(companyCode);

		return workplaceList;
	}

	@Override
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode, String workplaceCode) {

		ArrayList<DepartmentTO> departmentList = null;

		departmentList = departmentApplicationService.getDepartmentList(searchCondition, companyCode, workplaceCode);

		return departmentList;
	}

	@Override
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {

		HashMap<String, Object> resultMap = null;

		resultMap = companyApplicationService.batchCompanyListProcess(companyList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {

		HashMap<String, Object> resultMap = null;

		resultMap = workplaceApplicationService.batchWorkplaceListProcess(workplaceList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {

		HashMap<String, Object> resultMap = null;

		resultMap = departmentApplicationService.batchDepartmentListProcess(departmentList);

		return resultMap;
	}

}
