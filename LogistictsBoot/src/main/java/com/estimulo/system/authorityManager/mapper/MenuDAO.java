package com.estimulo.system.authorityManager.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.authorityManager.to.MenuTO;

@Mapper
public interface MenuDAO {

	public ArrayList<MenuTO> selectAllMenuList();
	
}
