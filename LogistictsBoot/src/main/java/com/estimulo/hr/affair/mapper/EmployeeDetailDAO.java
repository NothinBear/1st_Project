package com.estimulo.hr.affair.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.hr.affair.to.EmployeeDetailTO;

@Mapper
public interface EmployeeDetailDAO {

	public ArrayList<EmployeeDetailTO> selectEmployeeDetailList(String companyCode, String empCode);
	
	public ArrayList<EmployeeDetailTO> selectUserIdList(String companyCode);
	
	public void insertEmployeeDetail(EmployeeDetailTO TO);
	
	public void updateEmployeeImg(EmployeeDetailTO employeeDetailTO);
}
