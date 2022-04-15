package com.estimulo.system.basicInfo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.basicInfo.to.CustomerTO;

@Mapper
public interface CustomerDAO {
	public ArrayList<CustomerTO> selectCustomerList(HashMap<String, String> param);
	
	public ArrayList<CustomerTO> selectCustomerListByCompany();

	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode);
	
	public void insertCustomer(CustomerTO TO);

	public void updateCustomer(CustomerTO TO);

	public void deleteCustomer(CustomerTO TO);

}
