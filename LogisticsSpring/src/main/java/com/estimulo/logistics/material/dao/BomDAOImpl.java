package com.estimulo.logistics.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class BomDAOImpl extends IBatisSupportDAO implements BomDAO {

	
	public ArrayList<BomDeployTO> selectBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition) {
		
		HashMap<String, String> map = new HashMap<>();
		map.put("deployCondition" , deployCondition);
		map.put("itemCode" , itemCode);
		map.put("itemClassificationCondition" , itemClassificationCondition);
		
		return (ArrayList<BomDeployTO>) getSqlMapClientTemplate().queryForList("bom.selectBomDeployList",map);
	}
	
	public ArrayList<BomInfoTO> selectBomInfoList(String itemCode){
		return (ArrayList<BomInfoTO>)getSqlMapClientTemplate().queryForList("bom.selectBomInfoList", itemCode);
	}
	
	public ArrayList<BomInfoTO> selectAllItemWithBomRegisterAvailable() {
		return (ArrayList<BomInfoTO>)getSqlMapClientTemplate().queryForList("bom.selectAllItemWithBomRegisterAvailable");
	}
	
	public void insertBom(BomTO bean) {
		getSqlMapClientTemplate().insert("bom.insertBom",bean);
	}
	
	public void updateBom(BomTO bean) {
		getSqlMapClientTemplate().update("bom.updateBom",bean);
	}
	
	public void deleteBom(BomTO bean) {
		getSqlMapClientTemplate().delete("bom.deleteBom",bean);		
	}
	
}
