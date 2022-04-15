package com.estimulo.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.estimulo.logistics.production.to.MpsTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;


public class MpsDAOImpl extends IBatisSupportDAO implements MpsDAO {

	@SuppressWarnings("unchecked")
	public ArrayList<MpsTO> selectMpsList(String startDate, String endDate, String includeMrpApply) {
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("includeMrpApply",includeMrpApply);
				
		return (ArrayList<MpsTO>)getSqlMapClientTemplate().queryForList("mps.selectMpsList", map);
	}
	

	public int selectMpsCount(String mpsPlanDate) {

		@SuppressWarnings("unchecked")
		List<MpsTO> mpsTOlist = getSqlMapClientTemplate().queryForList("mps.selectMpsCount", mpsPlanDate);

		TreeSet<Integer> intSet = new TreeSet<>();
		for (MpsTO bean : mpsTOlist) {
			String mpsNo = bean.getMpsNo();
			// MPS �씪�젴踰덊샇�뿉�꽌 留덉�留� 2�옄由щ쭔 媛��졇�삤湲�
			int no = Integer.parseInt(mpsNo.substring(mpsNo.length() - 2, mpsNo.length()));
			intSet.add(no);
		}
		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1; // 媛��옣 �넂�� 踰덊샇 + 1
		}
	}

	
	
	public void insertMps(MpsTO bean) {
		getSqlMapClientTemplate().insert("mps.insertMps", bean);
	}

	public void updateMps(MpsTO bean) {
		getSqlMapClientTemplate().update("mps.updateMps", bean);
	}
	

	public void  changeMrpApplyStatus(String mpsNo, String mrpStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mpsNo", mpsNo);
		map.put("mrpStatus",mrpStatus);		
		
		getSqlMapClientTemplate().update("mps.changeMrpApplyStatus", map);
	}

	
	public void deleteMps(MpsTO bean) {
		getSqlMapClientTemplate().delete("mps.deleteMps", bean);		
	}
}
