package com.estimulo.system.authorityManager.applicationService;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.to.MenuAuthorityTO;

public interface MenuApplicationService {
	
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList);

	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode);
	
	public String[] getAllMenuList();
	
}
