package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.EstimateTO;

public interface ContractApplicationService {

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode);

	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo);
	
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate);

	// ApplicationService �븞�뿉�꽌留� �샇異�
	public String getNewContractNo(String contractDate);

	public HashMap<String, Object> addNewContract(HashMap<String,String[]>  workingContractList);

	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList);

	public void changeContractStatusInEstimate(String estimateNo , String contractStatus);
	
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String,String> ableSearchConditionInfo);

}
