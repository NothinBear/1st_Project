package com.estimulo.system.authorityManager.applicationService;

import java.util.ArrayList;

import com.estimulo.system.authorityManager.dao.AuthorityGroupDAO;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;

public class AuthorityGroupApplicationServiceImpl implements AuthorityGroupApplicationService {
	
	// DAO 참조변수 선언 
	private AuthorityGroupDAO authorityGroupDAO;
	public void setAuthorityGroupDAO(AuthorityGroupDAO authorityGroupDAO) {
		this.authorityGroupDAO = authorityGroupDAO;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getUserAuthorityGroup(String empCode) {   
		// TODO Auto-generated method stub
	      
	      ArrayList<AuthorityGroupTO> authorityGroupTOList = null;
		
    	  authorityGroupTOList = authorityGroupDAO.selectUserAuthorityGroupList(empCode);
	       
	       return authorityGroupTOList;
	}
	
	@Override
	public ArrayList<AuthorityGroupTO> getAuthorityGroup() {
		// TODO Auto-generated method stub
	      ArrayList<AuthorityGroupTO> authorityGroupTOList = null;
		
	    	  authorityGroupTOList = authorityGroupDAO.selectAuthorityGroupList();

	       return authorityGroupTOList;
	}

	@Override
	public void insertEmployeeAuthorityGroup(
			String empCode, ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList) {

	    	  // 해당 사원의 기존 그룹권한 정보 삭제
	    	  authorityGroupDAO.deleteEmployeeAuthorityGroup(empCode);
	    	  
	    	  // 새로운 그룹권한 정보 insert 
	    	  for(EmployeeAuthorityTO bean : employeeAuthorityTOList) {
	    		  
	    		  authorityGroupDAO.insertEmployeeAuthorityGroup(bean);
	    		  
	    	  }

	       
	}
}
