package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;

import com.estimulo.system.basicInfo.to.CustomerTO;

public interface CustomerDAO {
	public ArrayList<CustomerTO> selectCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode);
	
	public ArrayList<CustomerTO> selectCustomerListByCompany();

	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode);
	
	public void insertCustomer(CustomerTO TO);

	public void updateCustomer(CustomerTO TO);

	public void deleteCustomer(CustomerTO TO);

}
