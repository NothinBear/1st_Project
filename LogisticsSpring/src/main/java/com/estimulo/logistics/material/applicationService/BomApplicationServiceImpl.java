package com.estimulo.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.material.dao.BomDAO;
import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;

public class BomApplicationServiceImpl implements BomApplicationService {


	// DAO 참조변수 선언
	private BomDAO bomDAO;
	public void setBomDAO(BomDAO bomDAO) {
		this.bomDAO=bomDAO;
	}

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode, String itemClassificationCondition) {

		ArrayList<BomDeployTO> bomDeployList = null;

			bomDeployList = bomDAO.selectBomDeployList(deployCondition, itemCode, itemClassificationCondition);

		return bomDeployList;
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

	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		HashMap<String, Object> resultMap = new HashMap<>();

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

			resultMap.put("INSERT", insertCount);
			resultMap.put("UPDATE", updateCount);
			resultMap.put("DELETE", deleteCount);
			
		return resultMap;
	}

}
