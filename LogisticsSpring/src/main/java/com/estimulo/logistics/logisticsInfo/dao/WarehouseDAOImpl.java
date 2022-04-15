package com.estimulo.logistics.logisticsInfo.dao;

import java.util.ArrayList;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class WarehouseDAOImpl extends IBatisSupportDAO implements WarehouseDAO{
	@SuppressWarnings("unchecked")
	public ArrayList<WarehouseTO> selectWarehouseList() {
		return (ArrayList<WarehouseTO>) getSqlMapClientTemplate().queryForList("warehouse.selectWarehouseList");
	}
}
