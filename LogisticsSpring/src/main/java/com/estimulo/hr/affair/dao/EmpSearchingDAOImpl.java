package com.estimulo.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class EmpSearchingDAOImpl extends IBatisSupportDAO implements EmpSearchingDAO {
	
	@SuppressWarnings("unchecked")
	public ArrayList<EmpInfoTO> selectAllEmpList(String searchCondition, String[] paramArray) {
		
		HashMap<String, String> map = new HashMap<>();
		int size = paramArray.length;
		map.put("searchCondition", searchCondition);
		for(int a=0;a<size;a++) {
				switch (a + "") {
				case "0":
					map.put("companyCode", paramArray[0]);
					break;
				case "1":
					map.put("workplaceCode", paramArray[1]);
					break;
				case "2":
					map.put("deptCode", paramArray[2]);
					break;
				}
		}
		return (ArrayList<EmpInfoTO>) getSqlMapClientTemplate().queryForList("empInfo.selectAllEmpList", map);	
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EmpInfoTO> getTotalEmpInfo(String companyCode, String workplaceCode, String userId) {

		HashMap<String, String> map = new HashMap<>();
		map.put("companyCode", companyCode);
		map.put("workplaceCode", workplaceCode);
		map.put("userId", userId);
		
		return (ArrayList<EmpInfoTO>)getSqlMapClientTemplate().queryForList("empInfo.getTotalEmpInfo",map);
	}
}
