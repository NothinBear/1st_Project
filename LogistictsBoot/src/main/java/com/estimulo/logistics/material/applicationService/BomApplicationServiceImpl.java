package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.logistics.material.mapper.BomDAO;
import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;

@Component
public class BomApplicationServiceImpl implements BomApplicationService {


	@Autowired
	private BomDAO bomDAO;

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition) {

		HashMap<String, String> param = new HashMap<>();
		param.put("deployCondition", deployCondition);
		param.put("itemCode", itemCode);
		param.put("itemClassificationCondition", itemClassificationCondition);

		return bomDAO.selectBomDeployList(param);
	}
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		ArrayList<BomInfoTO> bomInfoList = null;

			bomInfoList = bomDAO.selectBomInfoList(parentItemCode);
			
		return bomInfoList;
	}

	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = null;

			allItemWithBomRegisterAvailable = bomDAO.selectAllItemWithBomRegisterAvailable();

		return allItemWithBomRegisterAvailable;

	}

	public ModelMap batchBomListProcess(ArrayList<BomTO> batchBomList) {

			int insertCount = 0;
			int updateCount = 0;
			int deleteCount = 0;

			for (BomTO TO : batchBomList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					bomDAO.insertBom(TO);

					insertCount++;

					break;

				case "UPDATE":

					bomDAO.updateBom(TO);

					updateCount++;

					break;

				case "DELETE":

					bomDAO.deleteBom(TO);

					deleteCount++;

					break;

				}

			}

			ModelMap modelMap = new ModelMap();
			modelMap.put("INSERT", insertCount);
			modelMap.put("UPDATE", updateCount);
			modelMap.put("DELETE", deleteCount);
			
		return modelMap;
	}

}
