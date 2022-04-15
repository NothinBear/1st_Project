package com.estimulo.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.hr.affair.to.EmployeeSecretTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class EmployeeSecretDAOImpl extends IBatisSupportDAO implements EmployeeSecretDAO {

	@SuppressWarnings("unchecked")
	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(String companyCode, String empCode) {
		HashMap<String, String> params = new HashMap<>();
		params.put("companyCode", companyCode);
		params.put("empCode", empCode);
		
		return (ArrayList<EmployeeSecretTO>) getSqlMapClientTemplate().queryForList("employeeSecret.selectEmployeeSecretList", params);
	
	}

	public EmployeeSecretTO selectUserPassWord(String companyCode, String empCode) {
		HashMap<String, String> params = new HashMap<>();
		params.put("companyCode", companyCode);
		params.put("empCode", empCode);
		
		return (EmployeeSecretTO) getSqlMapClientTemplate().queryForObject("employeeSecret.selectUserPassWord", params);
	
	}

	public void insertEmployeeSecret(EmployeeSecretTO TO) {
		getSqlMapClientTemplate().insert("employeeSecret.selectUserPassWord", TO);
	}

	@Override
	public int selectUserPassWordCount(String companyCode, String empCode) {
		HashMap<String, String> params = new HashMap<>();
		params.put("companyCode", companyCode);
		params.put("empCode", empCode);
		
		return (int) getSqlMapClientTemplate().queryForObject("employeeSecret.selectUserPassWord", params);
	}

}
