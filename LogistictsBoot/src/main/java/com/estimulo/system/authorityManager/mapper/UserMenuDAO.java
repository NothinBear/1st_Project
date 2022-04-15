package com.estimulo.system.authorityManager.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.authorityManager.to.UserMenuTO;

@Mapper
public interface UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode, String positionCode);

}
