package com.estimulo.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class EmployeeDetailDAOImpl extends IBatisSupportDAO implements EmployeeDetailDAO {
	@SuppressWarnings("unchecked")
	public ArrayList<EmployeeDetailTO> selectEmployeeDetailList(String companyCode, String empCode) {
		HashMap<String, String> params = new HashMap<>();
		params.put("companyCode", companyCode);
		params.put("empCode", empCode);
		return (ArrayList<EmployeeDetailTO>) getSqlMapClientTemplate().queryForList("employeeDetail.selectEmployeeDetailList",params);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<EmployeeDetailTO> selectUserIdList(String companyCode) {
		return (ArrayList<EmployeeDetailTO>) getSqlMapClientTemplate().queryForList("employeeDetail.selectUserIdList", companyCode);
	}

	public void insertEmployeeDetail(EmployeeDetailTO TO) {
		getSqlMapClientTemplate().insert("employeeDetail.insertEmployeeDetail", TO);
	}
}
