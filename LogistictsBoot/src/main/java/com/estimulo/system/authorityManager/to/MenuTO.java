package com.estimulo.system.authorityManager.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuTO {
	String menuCode, parentMenuCode, menuName, menuLevel, menuURL, menuStatus, childMenu, navMenu, navMenuName;
	
}
