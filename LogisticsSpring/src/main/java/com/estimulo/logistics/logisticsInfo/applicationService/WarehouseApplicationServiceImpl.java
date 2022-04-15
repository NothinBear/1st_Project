package com.estimulo.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;

import com.estimulo.logistics.logisticsInfo.dao.WarehouseDAO;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;

public class WarehouseApplicationServiceImpl implements WarehouseApplicationService{
	
	private WarehouseDAO warehouseDAO;
	
	public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
		this.warehouseDAO=warehouseDAO;
	}
	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList(){


		ArrayList<WarehouseTO> warehouseList = null;

			warehouseList = warehouseDAO.selectWarehouseList();
			
		return warehouseList;
	}
	
	@Override
	public void modifyWarehouseInfo(WarehouseTO warehouseTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findLastWarehouseCode() {
		// TODO Auto-generated method stub
		return null;
	}
}
