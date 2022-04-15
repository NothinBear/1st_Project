package com.estimulo.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.estimulo.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class ContractDetailDAOImpl extends IBatisSupportDAO implements ContractDetailDAO {

	@Override
	public HashMap<String,Object> insertContractDetail(HashMap<String,String>  workingContractList) {
		
        HashMap<String,Object> resultMap = new HashMap<>();
			
        ArrayList<ContractDetailTO> list = (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.insertContractDetail",workingContractList);
			ArrayList<String> contractDetailNoArr = new ArrayList<String>();

			for(ContractDetailTO CDT : list) {
				contractDetailNoArr.add(CDT.getContractDetailNo());
			}
			
		    resultMap.put("gridRowJson", contractDetailNoArr);
			resultMap.put("errorCode",workingContractList.get("ERROR_CODE")); //�꽦怨듭떆 0
			resultMap.put("errorMsg",workingContractList.get("ERROR_MSG")); //## MPS�벑濡앹셿猷� ##
			
			return resultMap;

}
	
	@Override
	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo) {
		return (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectContractDetailList", contractNo);
	}

	@Override
	public ArrayList<ContractDetailTO> selectDeliverableContractDetailList(String contractNo) {
		return (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectDeliverableContractDetailList", contractNo);
	}
	
	@Override
	public int selectContractDetailCount(String contractNo) {
		ArrayList<ContractDetailTO> list = (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectContractDetailCount", contractNo);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (ContractDetailTO bean : list) {
			String contractDetailNo = bean.getContractDetailNo();
			int no = Integer.parseInt(contractDetailNo.split("-")[1]);
			intSet.add(no);
		}
		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1;
		}
	}

	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(String searchCondition,	String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return (ArrayList<ContractDetailInMpsAvailableTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectContractDetailListInMpsAvailable", map);
	}

	public void changeMpsStatusOfContractDetail(String contractDetailNo, String mpsStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("contractDetailNo", contractDetailNo);
		map.put("mpsStatus", mpsStatus);
		getSqlMapClientTemplate().update("contractDetail.changeMpsStatusOfContractDetail", map);
	}

	@Override
	public void deleteContractDetail(ContractDetailTO bean) {
		getSqlMapClientTemplate().delete("contractDetail.deleteContractDetail", bean);
	}

}
