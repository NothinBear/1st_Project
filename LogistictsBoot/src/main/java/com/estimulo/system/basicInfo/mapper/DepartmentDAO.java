package com.estimulo.system.basicInfo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.basicInfo.to.DepartmentTO;

@Mapper
public interface DepartmentDAO {
	public ArrayList<DepartmentTO> selectDepartmentList(HashMap<String, String> param);
	
	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode);

	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode);

	public void insertDepartment(DepartmentTO TO);

	public void updateDepartment(DepartmentTO TO);

	public void deleteDepartment(DepartmentTO TO);
}
