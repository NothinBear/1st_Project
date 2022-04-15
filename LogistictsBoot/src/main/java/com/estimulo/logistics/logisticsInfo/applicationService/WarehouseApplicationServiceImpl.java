package com.estimulo.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.logisticsInfo.mapper.WarehouseDAO;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;

@Component
public class WarehouseApplicationServiceImpl implements WarehouseApplicationService{
	@Autowired
	private WarehouseDAO warehouseDAO;
	
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
