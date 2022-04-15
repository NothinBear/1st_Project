package com.estimulo.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class EstimateDetailDAOImpl extends IBatisSupportDAO implements EstimateDetailDAO {

	@Override
	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo) {
		return (ArrayList<EstimateDetailTO>)getSqlMapClientTemplate().queryForList("estimateDetail.selectEstimateDetailList", estimateNo);
	}

	@Override
	public int selectEstimateDetailCount(String estimateNo) {
		ArrayList<EstimateDetailTO> list = (ArrayList<EstimateDetailTO>)getSqlMapClientTemplate().queryForList("estimateDetail.selectEstimateDetailCount" , estimateNo);
			
		TreeSet<Integer> intSet = new TreeSet<>();

		for(EstimateDetailTO bean : list) {
			String estimateDetailNo = bean.getEstimateDetailNo();
			int no = Integer.parseInt(estimateDetailNo.split("-")[1]);
			intSet.add(no);
		}

		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1;
		}
	}
	public int selectEstimateDetailSeq(String estimateDate) {
		return (int) getSqlMapClientTemplate().queryForObject("estimateDetail.selectEstimateDetailSeq");
	}

	@Override
	public void insertEstimateDetail(EstimateDetailTO bean) {
		getSqlMapClientTemplate().insert("estimateDetail.insertEstimateDetail", bean);
	}

	@Override
	public void updateEstimateDetail(EstimateDetailTO bean) {
		getSqlMapClientTemplate().update("estimateDetail.updateEstimateDetail", bean);
	}
	public void deleteEstimateDetail(EstimateDetailTO bean) {
		String estimateDetailNo = bean.getEstimateDetailNo();
		getSqlMapClientTemplate().delete("estimateDetail.deleteEstimateDetail",estimateDetailNo);
	}

	@Override
	public void initDetailSeq() {
		getSqlMapClientTemplate().update("estimateDetail.initDetailSeq","EST_DETAIL_SEQ");
	}

	@Override
	public void reArrangeEstimateDetail(EstimateDetailTO bean, String newSeq) {
		HashMap<String,String> params= new HashMap<>();
		params.put("newSeq", newSeq);
		params.put("estimateDetailNo", bean.getEstimateDetailNo());
		getSqlMapClientTemplate().update("estimateDetail.reArrangeEstimateDetail",params);
	}
}
