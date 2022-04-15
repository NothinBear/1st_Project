package com.estimulo.system.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.base.to.AddressTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings({"unchecked" })
public class AddressDAOImpl extends IBatisSupportDAO implements AddressDAO {

	@Override
	public String selectSidoCode(String sidoName) {
		AddressTO to = (AddressTO) getSqlMapClientTemplate().queryForObject("address.selectSidoCode", sidoName);
		return to.getSidoCode();
	}

	
	@Override
	public ArrayList<AddressTO> selectRoadNameAddressList(String sidoCode, String searchValue, String buildingMainNumber) {
		HashMap<String, String> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("searchValue", searchValue);
		map.put("buildingMainNumber", buildingMainNumber);

		return (ArrayList<AddressTO>) getSqlMapClientTemplate().queryForList("address.selectRoadNameAddressList", map);
	}

	
	@Override
	public ArrayList<AddressTO> selectJibunAddressList(String sidoCode, String searchValue, String jibunMainAddress) {
		HashMap<String, String> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("searchValue", searchValue);
		map.put("jibunMainAddress", jibunMainAddress);

		return (ArrayList<AddressTO>) getSqlMapClientTemplate().queryForList("address.selectJibunAddressList", map);
	}
}
