package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicInfo.to.CustomerTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class CustomerDAOImpl extends IBatisSupportDAO implements CustomerDAO {
	@Override
	public ArrayList<CustomerTO> selectCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode) {
		HashMap<String, String> param= new HashMap<>();
		param.put("searchCondition",searchCondition);
		param.put("companyCode",companyCode);
		param.put("workplaceCode",workplaceCode);
		param.put("itemGroupCode",itemGroupCode);
		
		return (ArrayList<CustomerTO>) getSqlMapClientTemplate().queryForList("customer.selectCustomerList",param);
	}
	
	@Override
	public ArrayList<CustomerTO> selectCustomerListByCompany() {
		
		return (ArrayList<CustomerTO>) getSqlMapClientTemplate().queryForList("customer.selectCustomerListByCompany");
	}

	@Override
	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode) {
		
		return (ArrayList<CustomerTO>) getSqlMapClientTemplate().queryForList("customer.selectCustomerListByWorkplace", workplaceCode);
	}

	@Override
	public void insertCustomer(CustomerTO bean) {
		
		getSqlMapClientTemplate().insert("customer.insertCustomer", bean);
	}

	@Override
	public void updateCustomer(CustomerTO bean) {
		
		getSqlMapClientTemplate().update("customer.updateCustomer", bean);
	}

	@Override
	public void deleteCustomer(CustomerTO bean) {
		
		getSqlMapClientTemplate().delete("customer.deleteCustomer", bean);	
	}


}
