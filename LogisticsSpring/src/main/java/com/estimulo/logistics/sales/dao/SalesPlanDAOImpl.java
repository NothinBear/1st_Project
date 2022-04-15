package com.estimulo.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.estimulo.logistics.sales.to.SalesPlanTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class SalesPlanDAOImpl extends IBatisSupportDAO implements SalesPlanDAO {

	@Override
	public ArrayList<SalesPlanTO> selectSalesPlanList(String dateSearchCondition, String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("dateSearchCondition", dateSearchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<SalesPlanTO>) getSqlMapClientTemplate().queryForList("salesPlan.selectSalesPlanList", map);
	}
	
	@Override
	public int selectSalesPlanCount(String salesPlanDate) {
		return (int) getSqlMapClientTemplate().queryForObject("salesPlan.selectSalesPlanCount", salesPlanDate) + 1;
	}
	
	@Override
	public ArrayList<SalesPlanInMpsAvailableTO> selectSalesPlanListInMpsAvailable(String searchCondition, String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<SalesPlanInMpsAvailableTO>) getSqlMapClientTemplate().queryForList("salesPlan.selectSalesPlanListInMpsAvailable", map);
	}
	
	@Override
	public void insertSalesPlan(SalesPlanTO bean) {
		getSqlMapClientTemplate().insert("salesPlan.insertSalesPlan", bean);
	}

	@Override
	public void updateSalesPlan(SalesPlanTO bean) {
		getSqlMapClientTemplate().update("salesPlan.updateSalesPlan", bean);
	}

	@Override
	public void changeMpsStatusOfSalesPlan(String salesPlanNo, String mpsStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("salesPlanNo", salesPlanNo);
		map.put("mpsStatus", mpsStatus);

		getSqlMapClientTemplate().update("salesPlan.changeMpsStatusOfSalesPlan", map);
	}

	@Override
	public void deleteSalesPlan(SalesPlanTO TO) {
		getSqlMapClientTemplate().delete("salesPlan.deleteSalesPlan", TO);
	}

}
