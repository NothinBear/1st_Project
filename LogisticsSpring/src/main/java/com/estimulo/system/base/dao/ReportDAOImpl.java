package com.estimulo.system.base.dao;

import java.util.ArrayList;

import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class ReportDAOImpl extends IBatisSupportDAO implements ReportDAO {
	
	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo) {
			return (ArrayList<EstimateReportTO>)getSqlMapClientTemplate().queryForList("report.selectEstimateReport",estimateNo);
	}

	public ArrayList<ContractReportTO> selectContractReport(String contractNo) {
			return (ArrayList<ContractReportTO>)getSqlMapClientTemplate().queryForList("report.selectContractReport",contractNo);
	}

}
