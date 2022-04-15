package com.estimulo.logistics.sales.dao;

import com.estimulo.logistics.sales.to.ContractInfoTO;

import com.estimulo.logistics.sales.to.ContractTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unchecked")
public class ContractDAOImpl extends IBatisSupportDAO implements ContractDAO {

	@Override
	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return (ArrayList<EstimateTO>)getSqlMapClientTemplate().queryForList("contract.selectEstimateListInContractAvailable" , map);
	}
	@Override
	public ArrayList<ContractInfoTO> selectContractList(String searchCondition,String startDate, String endDate ,String customerCode) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("searchCondition",searchCondition);
		param.put("startDate",startDate);
		param.put("endDate",endDate);
		param.put("customerCode",customerCode);
		
			return (ArrayList<ContractInfoTO>)getSqlMapClientTemplate().queryForList("contract.selectContractList",param);
	}
	

	
	@Override
	public ArrayList<ContractInfoTO> selectDeliverableContractList(HashMap<String, String> ableSearchConditionInfo) {
		
		return (ArrayList<ContractInfoTO>)getSqlMapClientTemplate().queryForList("contract.selectDeliverableContractList" , ableSearchConditionInfo);
}

	

	@Override
	public int selectContractCount(String contractDate) {
		return (int) getSqlMapClientTemplate().queryForObject("contract.selectContractCount");
	}

	@Override
	public void insertContract(ContractTO bean) {
		getSqlMapClientTemplate().insert("contract.insertContract" , bean);		
	}

	@Override
	public void updateContract(ContractTO bean) {
		getSqlMapClientTemplate().update("contract.updateContract" , bean);
	}

	@Override
	public void deleteContract(ContractTO TO) {

	}
}
