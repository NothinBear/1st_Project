package com.estimulo.logistics.logisticsInfo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;

@Mapper
public interface WarehouseDAO {
	public ArrayList<WarehouseTO> selectWarehouseList();
}
