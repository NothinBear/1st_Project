package com.estimulo.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class MrpGatheringDAOImpl extends IBatisSupportDAO implements MrpGatheringDAO {

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MrpGatheringTO> selectMrpGatheringList(String searchDateCondition, String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchDateCondition", searchDateCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<MrpGatheringTO>) getSqlMapClientTemplate().queryForList("mrpGathering.selectMrpGatheringList", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MrpGatheringTO> getMrpGathering(String mrpNoList) {
		return (ArrayList<MrpGatheringTO>) getSqlMapClientTemplate().queryForList("mrpGathering.getMrpGathering", mrpNoList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int selectMrpGatheringCount(String mrpGatheringRegisterDate) {
		List<MrpGatheringTO> list= getSqlMapClientTemplate().queryForList("mrpGathering.selectMrpGatheringCount", mrpGatheringRegisterDate);

		TreeSet<Integer> intSet = new TreeSet<>();
		for(MrpGatheringTO bean : list) {
			String mrpGatheringNo = bean.getMrpGatheringNo();
			int no = Integer.parseInt(mrpGatheringNo.substring(mrpGatheringNo.length() - 2, mrpGatheringNo.length()));
			intSet.add(no);
		}
		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1;
		}
	}

	@Override
	public void insertMrpGathering(MrpGatheringTO bean) {
		getSqlMapClientTemplate().insert("mrpGathering.insertMrpGathering", bean);
	}

	@Override
	public void updateMrpGathering(MrpGatheringTO bean) {
		getSqlMapClientTemplate().update("mrpGathering.updateMrpGathering", bean);
	}

	@Override
	public void deleteMrpGathering(MrpGatheringTO bean) {
		getSqlMapClientTemplate().delete("mrpGathering.deleteMrpGathering", bean);
	}

	@Override 
	public void updateMrpGatheringContract(HashMap<String, String> parameter) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringNo",parameter.get("mrpGatheringNoList"));
		
		getSqlMapClientTemplate().update("mrpGathering.updateMrpGatheringContract", map);
	}

	@Override
	public int getMGSeqNo() {
		return (int) getSqlMapClientTemplate().queryForObject("mrpGathering.getMGSeqNo");
	}
}