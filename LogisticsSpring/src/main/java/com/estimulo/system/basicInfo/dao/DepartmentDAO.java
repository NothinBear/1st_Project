package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;

import com.estimulo.system.basicInfo.to.DepartmentTO;

public interface DepartmentDAO {
	public ArrayList<DepartmentTO> selectDepartmentList(String searchCondition, String companyCode,String workplaceCode);
	
	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode);

	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode);

	public void insertDepartment(DepartmentTO TO);

	public void updateDepartment(DepartmentTO TO);

	public void deleteDepartment(DepartmentTO TO);
}
