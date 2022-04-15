package com.estimulo.system.authorityManager.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;

@Mapper
public interface AuthorityGroupDAO {
	
	public ArrayList<AuthorityGroupTO> selectUserAuthorityGroupList(String empCode);

	public ArrayList<AuthorityGroupTO> selectAuthorityGroupList();
	
	public void insertEmployeeAuthorityGroup(EmployeeAuthorityTO bean);
	
	public void deleteEmployeeAuthorityGroup(String empCode);

}
