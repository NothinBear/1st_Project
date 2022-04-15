package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;

import com.estimulo.system.basicInfo.to.WorkplaceTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class WorkplaceDAOImpl extends IBatisSupportDAO implements WorkplaceDAO {


	public ArrayList<WorkplaceTO> selectWorkplaceList(String companyCode) {
		
		return (ArrayList<WorkplaceTO>) getSqlMapClientTemplate().queryForList("workplace.selectWorkplaceList", companyCode);
	}

	public void insertWorkplace(WorkplaceTO bean)  {
		
		getSqlMapClientTemplate().insert("workplace.insertWorkplace", bean);	
	}

	public void updateWorkplace(WorkplaceTO bean) {
		
		getSqlMapClientTemplate().update("workplace.updateWorkplace", bean);
	}

	public void deleteWorkplace(WorkplaceTO bean) {
		
		getSqlMapClientTemplate().delete("workplace.deleteWorkplace", bean);
	}

}