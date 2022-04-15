package com.estimulo.logistics.logisticsInfo.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.logisticsInfo.to.ItemInfoTO;
import com.estimulo.logistics.logisticsInfo.to.ItemTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class ItemDAOImpl extends IBatisSupportDAO implements ItemDAO {

	@SuppressWarnings("unchecked")
	public ArrayList<ItemInfoTO> selectAllItemList() {
		return (ArrayList<ItemInfoTO>) getSqlMapClientTemplate().queryForList("item.selectAllItemList");
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ItemInfoTO> selectItemList(String searchCondition, String paramArray[]) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		if (paramArray != null) {
			for (int i = 0; i < paramArray.length; i++) {
				switch (i + "") {
					case "0":
						if (searchCondition.equals("ITEM_CLASSIFICATION")) {
							map.put("itemClassification", paramArray[0]);
						} else if (searchCondition.equals("ITEM_GROUP_CODE")) {
							map.put("itemGroupCode", paramArray[0]);
						} else if (searchCondition.equals("STANDARD_UNIT_PRICE")) {
							map.put("minPrice", paramArray[0]);
						}
						break;
					case "1":
						map.put("maxPrice", paramArray[1]);
						break;
				}
			}
		}
		return (ArrayList<ItemInfoTO>) getSqlMapClientTemplate().queryForList("item.selectItemList",map);
	}
	public void insertItem(ItemTO TO) {
		getSqlMapClientTemplate().insert("item.insertItem", TO);
	}
	public void updateItem(ItemTO TO) {
		getSqlMapClientTemplate().update("item.updateItem", TO);
	}
	public void deleteItem(ItemTO TO) {
		getSqlMapClientTemplate().delete("item.deleteItem", TO);
	}
	@Override
	public int getStandardUnitPrice(String itemCode) {
		return (int)getSqlMapClientTemplate().queryForObject("item.getStandardUnitPrice", itemCode);
	}
	@Override
	public int getStandardUnitPriceBox(String itemCode) {
		return (int)getSqlMapClientTemplate().queryForObject("item.getStandardUnitPriceBox", itemCode);
	}
}