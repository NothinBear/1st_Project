package com.estimulo.system.authorityManager.dao;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.to.AuthorityGroupMenuTO;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;


@SuppressWarnings("unchecked")
public class MenuAuthorityDAOImpl extends IBatisSupportDAO implements MenuAuthorityDAO{

	
	public ArrayList<AuthorityGroupMenuTO> selectUserMenuAuthorityList(String empCode) {
		return (ArrayList<AuthorityGroupMenuTO>) getSqlMapClientTemplate().queryForList("menuAuthority.selectUserMenuAuthorityList", empCode);
	}
	

	public ArrayList<MenuAuthorityTO> selectMenuAuthorityList(String authorityGroupCode) {
		return (ArrayList<MenuAuthorityTO>) getSqlMapClientTemplate().queryForList("menuAuthority.selectMenuAuthorityList", authorityGroupCode); 
	}
	

	public void deleteMenuAuthority(String authorityGroupCode) {
		getSqlMapClientTemplate().delete("menuAuthority.deleteMenuAuthority", authorityGroupCode); 
	}


	public void insertMenuAuthority(MenuAuthorityTO bean) {
		getSqlMapClientTemplate().insert("menuAuthority.insertMenuAuthority", bean); 
	}
}
