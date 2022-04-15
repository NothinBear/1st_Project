package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicInfo.to.DepartmentTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class DepartmentDAOImpl extends IBatisSupportDAO implements DepartmentDAO {
	
	@Override
	public ArrayList<DepartmentTO> selectDepartmentList(String searchCondition, String companyCode, String workplaceCode) {
		HashMap<String,String> param = new HashMap<>();
		param.put("searchCondition",searchCondition);
		param.put("companyCode",companyCode);
		param.put("workplaceCode",workplaceCode);

		return (ArrayList<DepartmentTO>) getSqlMapClientTemplate().queryForList("company.selectDepartmentList",param);
	}

	@Override
	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode) {
		return (ArrayList<DepartmentTO>) getSqlMapClientTemplate().queryForList("company.selectDepartmentListByCompany",companyCode);
	}

	@Override
	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode) {
		return (ArrayList<DepartmentTO>) getSqlMapClientTemplate().queryForList("company.selectDepartmentListByWorkplace",workplaceCode);
	}

	@Override
	public void insertDepartment(DepartmentTO bean) {
		getSqlMapClientTemplate().insert("company.insertDepartment",bean);
	}

	@Override
	public void updateDepartment(DepartmentTO bean) {
		getSqlMapClientTemplate().update("company.updateDepartment",bean);}

	@Override
	public void deleteDepartment(DepartmentTO bean) {
		getSqlMapClientTemplate().delete("company.deleteDepartment",bean);}

}

