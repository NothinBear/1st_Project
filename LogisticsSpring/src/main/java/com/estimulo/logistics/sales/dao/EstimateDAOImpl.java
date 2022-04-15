package com.estimulo.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.sales.to.EstimateTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;
@SuppressWarnings("unchecked")
public class EstimateDAOImpl extends IBatisSupportDAO implements EstimateDAO {

	@Override
	public ArrayList<EstimateTO> selectEstimateList(String dateSearchCondition, String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("dateSearchCondition", dateSearchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
			
		return (ArrayList<EstimateTO>)getSqlMapClientTemplate().queryForList("estimate.selectEstimateList", map);
	}

	@Override
	public EstimateTO selectEstimate(String estimateNo) {
		return (EstimateTO)getSqlMapClientTemplate().queryForObject("estimate.selectEstimate", estimateNo);
	}

	public int selectEstimateCount(String estimateDate) {
		return (int) getSqlMapClientTemplate().queryForObject("estimate.selectEstimateCount");
	}
	
	@Override
	public void insertEstimate(EstimateTO bean) {
		getSqlMapClientTemplate().insert("estimate.insertEstimate", bean);
	}

	@Override
	public void updateEstimate(EstimateTO bean) {
		getSqlMapClientTemplate().update("estimate.updateEstimate", bean);
	}

	@Override
	public void changeContractStatusOfEstimate(String estimateNo, String contractStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("estimateNo", estimateNo);
		map.put("contractStatus", contractStatus);
	
		getSqlMapClientTemplate().update("estimate.changeContractStatusOfEstimate", map);
	}

}