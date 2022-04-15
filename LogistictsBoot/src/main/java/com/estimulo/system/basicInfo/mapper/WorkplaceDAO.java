package com.estimulo.system.basicInfo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.basicInfo.to.WorkplaceTO;

@Mapper
public interface WorkplaceDAO {

	public ArrayList<WorkplaceTO> selectWorkplaceList(String companyCode);

	public void insertWorkplace(WorkplaceTO TO);

	public void updateWorkplace(WorkplaceTO TO);

	public void deleteWorkplace(WorkplaceTO TO);
}
