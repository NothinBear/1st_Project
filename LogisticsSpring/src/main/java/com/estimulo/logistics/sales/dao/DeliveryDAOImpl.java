package com.estimulo.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.sales.to.DeliveryInfoTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class DeliveryDAOImpl extends IBatisSupportDAO implements DeliveryDAO {

	@Override
	public ArrayList<DeliveryInfoTO> selectDeliveryInfoList() {
		return (ArrayList<DeliveryInfoTO>) getSqlMapClientTemplate().queryForList("delivery.selectDeliveryInfoList");
	}

	@Override
	public void insertDeliveryResult(DeliveryInfoTO bean) {
		getSqlMapClientTemplate().insert("delivery.insertDeliveryResult", bean);
	}

	@Override
	public HashMap<String,Object> deliver(String contractDetailNo) {
		HashMap<String, String> map = new HashMap<>();
		map.put("contractDetailNo", contractDetailNo);

		getSqlMapClientTemplate().update("delivery.deliver", map);

		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;
	}

	@Override
	public void updateDeliveryResult(DeliveryInfoTO bean) {
		getSqlMapClientTemplate().update("delivery.updateDeliveryResult", bean);
	}
	@Override
	public void deleteDeliveryResult(DeliveryInfoTO bean) {
		getSqlMapClientTemplate().delete("delivery.deleteDeliveryResult", bean);
	}

}
