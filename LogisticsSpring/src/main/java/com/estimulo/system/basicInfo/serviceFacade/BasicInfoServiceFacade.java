package com.estimulo.system.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicInfo.to.CompanyTO;
import com.estimulo.system.basicInfo.to.CustomerTO;
import com.estimulo.system.basicInfo.to.DepartmentTO;
import com.estimulo.system.basicInfo.to.FinancialAccountAssociatesTO;
import com.estimulo.system.basicInfo.to.WorkplaceTO;

public interface BasicInfoServiceFacade {

    public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode);

    public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList);

    public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
                                                                                     String workplaceCode);

    public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
            ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList);

    public ArrayList<CompanyTO> getCompanyList();

    public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode);

    public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
                                                     String workplaceCode);

    public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList);

    public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList);

    public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList);
}
