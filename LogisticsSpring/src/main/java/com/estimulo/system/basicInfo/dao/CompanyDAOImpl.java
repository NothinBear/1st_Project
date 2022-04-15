package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;

import com.estimulo.system.basicInfo.to.CompanyTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

	@SuppressWarnings("unchecked")
	public class CompanyDAOImpl extends IBatisSupportDAO implements CompanyDAO {
		
		@Override
		public ArrayList<CompanyTO> selectCompanyList() {
			return (ArrayList<CompanyTO>) getSqlMapClientTemplate().queryForList("company.selectCompanyList");
		}

		@Override
		public void insertCompany(CompanyTO bean) {
			getSqlMapClientTemplate().insert("company.insertCompany",bean);
		}

		@Override
		public void updateCompany(CompanyTO bean) {
			getSqlMapClientTemplate().update("company.updateCompany",bean);
		}

		@Override
		public void deleteCompany(CompanyTO bean) {
			getSqlMapClientTemplate().delete("company.deleteCompany",bean);
		}

}
