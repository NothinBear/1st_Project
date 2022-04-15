package com.estimulo.system.authorityManager.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.authorityManager.to.AuthorityGroupMenuTO;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;

@Mapper
public interface MenuAuthorityDAO {
	
	public void deleteMenuAuthority(String authorityGroupCode);
	
	public void insertMenuAuthority(MenuAuthorityTO bean);
	
	public ArrayList<MenuAuthorityTO> selectMenuAuthorityList(String authorityGroupCode);
	
	public ArrayList<AuthorityGroupMenuTO> selectUserMenuAuthorityList(String empCode);
		
}
