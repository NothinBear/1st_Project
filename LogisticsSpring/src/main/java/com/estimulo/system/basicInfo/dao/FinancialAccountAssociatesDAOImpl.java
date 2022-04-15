package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;

import com.estimulo.system.basicInfo.to.FinancialAccountAssociatesTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;


	@SuppressWarnings("unchecked")
	public class FinancialAccountAssociatesDAOImpl extends IBatisSupportDAO implements FinancialAccountAssociatesDAO {

		@Override
		public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByCompany() {
			return (ArrayList<FinancialAccountAssociatesTO>) getSqlMapClientTemplate().queryForList("company.selectFinancialAccountAssociatesListByCompany");
		}

		@Override
		public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByWorkplace(
				String workplaceCode) {
			return (ArrayList<FinancialAccountAssociatesTO>) getSqlMapClientTemplate().queryForList("company.selectFinancialAccountAssociatesListByWorkplace", workplaceCode);
			}

		@Override
		public void insertFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {
			getSqlMapClientTemplate().insert("company.insertFinancialAccountAssociates", bean);
			}

		@Override
		public void updateFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {
			getSqlMapClientTemplate().update("company.updateFinancialAccountAssociates", bean);
			}

		@Override
		public void deleteFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {
			getSqlMapClientTemplate().delete("company.deleteFinancialAccountAssociates", bean);
			}

}
