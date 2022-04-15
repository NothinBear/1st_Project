package com.estimulo.logistics.material.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;

@Mapper
public interface BomDAO {
	
	public ArrayList<BomDeployTO> selectBomDeployList(HashMap<String, String> param);
	
	public ArrayList<BomInfoTO> selectBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> selectAllItemWithBomRegisterAvailable();
	
	public void insertBom(BomTO TO);
	
	public void updateBom(BomTO TO);
	
	public void deleteBom(BomTO TO);
	
}
