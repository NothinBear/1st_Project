package com.estimulo.system.authorityManager.dao;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.to.MenuTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class MenuDAOImpl extends IBatisSupportDAO implements MenuDAO{

	
	public ArrayList<MenuTO> selectAllMenuList() {
		return (ArrayList<MenuTO>) getSqlMapClientTemplate().queryForList("menu.selectAllMenuList");		
	}
}
