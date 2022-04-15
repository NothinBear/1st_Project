package com.estimulo.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class EmployeeBasicDAOImpl extends IBatisSupportDAO implements EmployeeBasicDAO {
	@SuppressWarnings("unchecked")
	public ArrayList<EmployeeBasicTO> selectEmployeeBasicList(String companyCode) {
		return (ArrayList<EmployeeBasicTO>)getSqlMapClientTemplate().queryForList("employeeBasic.selectEmployeeBasicList",companyCode);
	}

	public EmployeeBasicTO selectEmployeeBasicTO(String companyCode, String empCode) {
		HashMap<String, String> params = new HashMap<>();
		params.put("companyCode",companyCode);
		params.put("empCode",empCode);
		return (EmployeeBasicTO) getSqlMapClientTemplate().queryForObject("employeeBasic.selectEmployeeBasicTO", params);
	}

	public void insertEmployeeBasic(EmployeeBasicTO TO) {
		getSqlMapClientTemplate().insert("employeeBasic.insertEmployeeBasic", TO);
	}

	@Override
	public void changeUserAccountStatus(String companyCode, String empCode, String userStatus) {
		HashMap<String, String> params = new HashMap<>();
		params.put("companyCode",companyCode);
		params.put("empCode",empCode);
		params.put("userStatus",userStatus);
		getSqlMapClientTemplate().update("emloyeeBasic.changeUserAccountStatus",params);
	}


}
