package com.estimulo.system.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.basicInfo.applicationService.*;
import com.estimulo.system.basicInfo.to.CompanyTO;
import com.estimulo.system.basicInfo.to.CustomerTO;
import com.estimulo.system.basicInfo.to.DepartmentTO;
import com.estimulo.system.basicInfo.to.FinancialAccountAssociatesTO;
import com.estimulo.system.basicInfo.to.WorkplaceTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class BasicInfoServiceFacadeImpl implements BasicInfoServiceFacade{
    // SLF4J logger
    private static Logger logger = LoggerFactory.getLogger(BasicInfoServiceFacadeImpl.class);

    // 싱글톤
    private static BasicInfoServiceFacade instance = new BasicInfoServiceFacadeImpl();

    private BasicInfoServiceFacadeImpl() {
    }

    public static BasicInfoServiceFacade getInstance() {

        if (logger.isDebugEnabled()) {
            logger.debug("@ BasicInfoServiceFacadeImpl 객체접근");
        }

        return instance;
    }

    // 참조변수 선언
    private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

    private static CustomerApplicationService customerAS = CustomerApplicationServiceImpl.getInstance();
    private static FinancialAccountAssociatesApplicationService associatsAS = FinancialAccountAssociatesApplicationServiceImpl.getInstance();
    private static CompanyApplicationService companyAS = CompanyApplicationServiceImpl.getInstance();
    private static WorkplaceApplicationService workplaceAS = WorkplaceApplicationServiceImpl.getInstance();
    private static DepartmentApplicationService deptAS = DepartmentApplicationServiceImpl.getInstance();
    

    @Override
    public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode) {

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : getCustomerList 시작");
        }

        dataSourceTransactionManager.beginTransaction(false);
        ArrayList<CustomerTO> customerList = null;

        try {

            customerList = customerAS.getCustomerList(searchCondition, companyCode, workplaceCode,itemGroupCode);
            dataSourceTransactionManager.commitTransaction();

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            dataSourceTransactionManager.rollbackTransaction();
            throw e;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : getCustomerList 종료");
        }

        return customerList;
    }

    @Override
    public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : batchCustomerListProcess 시작");
        }

        dataSourceTransactionManager.beginTransaction(false);
        HashMap<String, Object> resultMap = null;

        try {

            resultMap = customerAS.batchCustomerListProcess(customerList);
            dataSourceTransactionManager.commitTransaction();

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            dataSourceTransactionManager.rollbackTransaction();
            throw e;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : batchCustomerListProcess 종료");
        }
        return resultMap;

    }

    @Override
    public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
                                                                                     String workplaceCode) {

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : getFinancialAccountAssociatesList 시작");
        }

        dataSourceTransactionManager.beginTransaction(false);
        ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList = null;

        try {

            financialAccountAssociatesList = associatsAS.getFinancialAccountAssociatesList(searchCondition,
                    workplaceCode);

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            dataSourceTransactionManager.rollbackTransaction();
            throw e;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : getFinancialAccountAssociatesList 종료");
        }
        return financialAccountAssociatesList;

    }

    @Override
    public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
            ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList) {
        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : batchFinancialAccountAssociatesListProcess 시작");
        }

        dataSourceTransactionManager.beginTransaction(false);
        HashMap<String, Object> resultMap = null;

        try {

            resultMap = associatsAS.batchFinancialAccountAssociatesListProcess(financialAccountAssociatesList);
            dataSourceTransactionManager.commitTransaction();

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            dataSourceTransactionManager.rollbackTransaction();
            throw e;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("BasicInfoServiceFacadeImpl : batchFinancialAccountAssociatesListProcess 종료");
        }
        return resultMap;
    }
    @Override
	public ArrayList<CompanyTO> getCompanyList() {

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : getCompanyList 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);
		ArrayList<CompanyTO> companyList = null;

		try {

			companyList = companyAS.getCompanyList();
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : getCompanyList 종료");
		}

		return companyList;
	}

	@Override
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : getWorkplaceList 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);
		ArrayList<WorkplaceTO> workplaceList = null;

		try {

			workplaceList = workplaceAS.getWorkplaceList(companyCode);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : getWorkplaceList 종료");
		}

		return workplaceList;
	}

	@Override
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode) {

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : getDepartmentList 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);
		ArrayList<DepartmentTO> departmentList = null;

		try {

			departmentList = deptAS.getDepartmentList(searchCondition, companyCode, workplaceCode);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : getDepartmentList 종료");
		}

		return departmentList;
	}

	@Override
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : batchCompanyListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);
		HashMap<String, Object> resultMap = null;

		try {

			resultMap = companyAS.batchCompanyListProcess(companyList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : batchCompanyListProcess 종료");
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : batchWorkplaceListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);
		HashMap<String, Object> resultMap = null;

		try {

			resultMap = workplaceAS.batchWorkplaceListProcess(workplaceList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : batchWorkplaceListProcess 종료");
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : batchDepartmentListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction(false);
		HashMap<String, Object> resultMap = null;

		try {

			resultMap = deptAS.batchDepartmentListProcess(departmentList);
			dataSourceTransactionManager.commitTransaction(); 

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BasicInfoServiceFacadeImpl : batchDepartmentListProcess 종료");
		}
		return resultMap;
	}

}
