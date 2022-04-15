package com.estimulo.system.authorityManager.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuAuthorityTO {
	String menuCode, menuName, authorityGroupCode, menuLevel, authority;
	
}
