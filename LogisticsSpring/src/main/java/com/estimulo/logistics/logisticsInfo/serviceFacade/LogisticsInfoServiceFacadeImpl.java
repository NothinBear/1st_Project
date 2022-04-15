package com.estimulo.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.logisticsInfo.applicationService.ItemApplicationService;
import com.estimulo.logistics.logisticsInfo.applicationService.WarehouseApplicationService;
import com.estimulo.logistics.logisticsInfo.to.ItemInfoTO;
import com.estimulo.logistics.logisticsInfo.to.ItemTO;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;

public class LogisticsInfoServiceFacadeImpl implements LogisticsInfoServiceFacade {

	private ItemApplicationService itemApplicationService;
	private WarehouseApplicationService warehouseApplicationService;

	public void setItemApplicationService(ItemApplicationService itemApplicationService) {
		
		this.itemApplicationService=itemApplicationService;
	}
	public void setWarehouseApplicationService(WarehouseApplicationService warehouseApplicationService) {
		
		this.warehouseApplicationService=warehouseApplicationService;
	}
	
	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {

		ArrayList<ItemInfoTO> itemInfoList = null;

			itemInfoList = itemApplicationService.getItemInfoList(searchCondition, paramArray);

		return itemInfoList;
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {

		HashMap<String, Object> resultMap = null;

			resultMap = itemApplicationService.batchItemListProcess(itemTOList);

		return resultMap;
	}

	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList() {

		ArrayList<WarehouseTO> warehouseInfoList = null;

			warehouseInfoList = warehouseApplicationService.getWarehouseInfoList();

		return warehouseInfoList;
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

	@Override
	public int getStandardUnitPrice(String itemCode) {

		int price = 0;

			price = itemApplicationService.getStandardUnitPrice(itemCode);

		return price;

	}

	@Override
	public int getStandardUnitPriceBox(String itemCode) {

		int price = 0;

			price = itemApplicationService.getStandardUnitPriceBox(itemCode);

		return price;

	}

}
