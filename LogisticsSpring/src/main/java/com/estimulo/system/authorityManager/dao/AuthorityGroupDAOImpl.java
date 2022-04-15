package com.estimulo.system.authorityManager.dao;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class AuthorityGroupDAOImpl extends IBatisSupportDAO implements AuthorityGroupDAO{

	@Override
	public ArrayList<AuthorityGroupTO>  selectUserAuthorityGroupList(String empCode) {	
	    return (ArrayList<AuthorityGroupTO>) getSqlMapClientTemplate().queryForList("authorityGroup.selectUserAuthorityGroupList", empCode);
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> selectAuthorityGroupList() {
	    return (ArrayList<AuthorityGroupTO>) getSqlMapClientTemplate().queryForList("authorityGroup.selectAuthorityGroupList");
	}

	@Override
	public void insertEmployeeAuthorityGroup(EmployeeAuthorityTO bean) {
		getSqlMapClientTemplate().insert("authorityGroup.insertEmployeeAuthorityGroup", bean);
	}

	@Override
	public void deleteEmployeeAuthorityGroup(String empCode) {
		getSqlMapClientTemplate().delete("authorityGroup.deleteEmployeeAuthorityGroup", empCode);
	}



}
