package com.estimulo.system.authorityManager.dao;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.to.MenuTO;

public interface MenuDAO {

	public ArrayList<MenuTO> selectAllMenuList();
	
}
