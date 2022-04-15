package com.estimulo.logistics.sales.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.logistics.sales.applicationService.ContractApplicationService;
import com.estimulo.logistics.sales.applicationService.DeliveryApplicationService;
import com.estimulo.logistics.sales.applicationService.EstimateApplicationService;
import com.estimulo.logistics.sales.applicationService.SalesPlanApplicationService;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.DeliveryInfoTO;
import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.estimulo.logistics.sales.to.SalesPlanTO;

@Service
public class SalesServiceFacadeImpl implements SalesServiceFacade {
	@Autowired
	private EstimateApplicationService estimateApplicationService;
	@Autowired
	private ContractApplicationService contractApplicationService;
	@Autowired
	private SalesPlanApplicationService salesPlanApplicationService;
	@Autowired
	private DeliveryApplicationService deliveryApplicationService;


	@Override
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {

		ArrayList<EstimateTO> estimateTOList = null;

			estimateTOList = estimateApplicationService.getEstimateList(dateSearchCondition, startDate, endDate);

		return estimateTOList;
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {

		ArrayList<EstimateDetailTO> estimateDetailTOList = null;

			estimateDetailTOList = estimateApplicationService.getEstimateDetailList(estimateNo);

		return estimateDetailTOList;
	}

	@Override
	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {


		HashMap<String, Object> resultMap = null;


			resultMap = estimateApplicationService.addNewEstimate(estimateDate, newEstimateTO);
			// SalesSF�뿉�꽌resultMap媛믪� : {DELETE=[], 
									//	newEstimateNo=ES2020100702, 
									//	INSERT=[ES2020100702-01, ES2020100702-02], 
									//	UPDATE=[]}

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList,String estimateNo) {

		HashMap<String, Object> resultMap = null;

			resultMap = estimateApplicationService.batchEstimateDetailListProcess(estimateDetailTOList,estimateNo);

		return resultMap;
	}

	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode){

		ArrayList<ContractInfoTO> contractInfoTOList = null;

			contractInfoTOList = contractApplicationService.getContractList(searchCondition, startDate, endDate,customerCode);

		return contractInfoTOList;
	}

	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String,String> ableSearchConditionInfo) {
		
		ArrayList<ContractInfoTO> deliverableContractList = null;
			
			deliverableContractList = contractApplicationService.getDeliverableContractList(ableSearchConditionInfo);
		return deliverableContractList;
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo) {


		ArrayList<ContractDetailTO> contractDetailTOList = null;

			contractDetailTOList = contractApplicationService.getContractDetailList(estimateNo);
			
		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		ArrayList<EstimateTO> estimateListInContractAvailable = null;

			estimateListInContractAvailable = contractApplicationService.getEstimateListInContractAvailable(startDate, endDate);
			
		return estimateListInContractAvailable;
	}

	@Override
	public HashMap<String, Object> addNewContract(HashMap<String,String[]>  workingContractList) {

		HashMap<String, Object> resultMap = null;

			resultMap = contractApplicationService.addNewContract(workingContractList);
			
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		HashMap<String, Object> resultMap = null;

			resultMap = contractApplicationService.batchContractDetailListProcess(contractDetailTOList);
			
		return resultMap;
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
													 //estimateNo, "N"
			contractApplicationService.changeContractStatusInEstimate(estimateNo, contractStatus);

	}

	@Override
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {

		ArrayList<SalesPlanTO> salesPlanTOList = null;

			salesPlanTOList = salesPlanApplicationService.getSalesPlanList(dateSearchCondition, startDate, endDate);
			
		return salesPlanTOList;
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {

		HashMap<String, Object> resultMap = null;


			resultMap = salesPlanApplicationService.batchSalesPlanListProcess(salesPlanTOList);

		return resultMap;
	}

	@Override
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList() {

		ArrayList<DeliveryInfoTO> deliveryInfoList = null;

			deliveryInfoList = deliveryApplicationService.getDeliveryInfoList();

		return deliveryInfoList;
	}

	@Override
	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList) {
	
		HashMap<String, Object> resultMap = null;

			resultMap = deliveryApplicationService.batchDeliveryListProcess(deliveryTOList);
	
		return resultMap;
	}

	@Override
	public HashMap<String, Object> deliver(String contractDetailNo) {

        HashMap<String,Object> resultMap = null;


			resultMap = deliveryApplicationService.deliver(contractDetailNo);
		
		return resultMap;
	}
	
}
