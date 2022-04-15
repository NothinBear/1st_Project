package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.sales.dao.ContractDAO;
import com.estimulo.logistics.sales.dao.ContractDetailDAO;
import com.estimulo.logistics.sales.dao.EstimateDAO;
import com.estimulo.logistics.sales.dao.EstimateDetailDAO;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.EstimateTO;

public class ContractApplicationServiceImpl implements ContractApplicationService {

	// 李몄“蹂��닔 �꽑�뼵
	private ContractDAO contractDAO;
	private ContractDetailDAO contractDetailDAO;
	private EstimateDAO estimateDAO;
	private EstimateDetailDAO estimateDetailDAO;
	

	public void setContractDAO(ContractDAO contractDAO) {
		this.contractDAO = contractDAO;
	}

	public void setContractDetailDAO(ContractDetailDAO contractDetailDAO) {
		this.contractDetailDAO = contractDetailDAO;
	}

	public void setEstimateDAO(EstimateDAO estimateDAO) {
		this.estimateDAO = estimateDAO;
	}

	public void setEstimateDetailDAO(EstimateDetailDAO estimateDetailDAO) {
		this.estimateDetailDAO = estimateDetailDAO;
	}

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;
			
				contractInfoTOList = contractDAO.selectContractList(searchCondition, startDate, endDate ,customerCode);

			for (ContractInfoTO bean : contractInfoTOList) {

				bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));
			}
		return contractInfoTOList;

	}
	
	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String, String> ableSearchConditionInfo) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;

				contractInfoTOList = contractDAO.selectDeliverableContractList(ableSearchConditionInfo);


			for (ContractInfoTO bean : contractInfoTOList) { // �빐�떦 �닔二쇱쓽 �닔二쇱긽�꽭 由ъ뒪�듃 �꽭�똿 

				bean.setContractDetailTOList(contractDetailDAO.selectDeliverableContractDetailList(bean.getContractNo()));

			}

		return contractInfoTOList;

	}
	
	
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {

		ArrayList<ContractDetailTO> contractDetailTOList = null;


			contractDetailTOList = contractDetailDAO.selectContractDetailList(contractNo);

		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		ArrayList<EstimateTO> estimateListInContractAvailable = null;

			estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(startDate, endDate);
			//estimateListInContractAvailable = EstimateListInContractAvailable

			for (EstimateTO bean : estimateListInContractAvailable) {

				bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));
			}
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {

		StringBuffer newContractNo = null;

			int i = contractDAO.selectContractCount(contractDate);
			newContractNo = new StringBuffer();
			newContractNo.append("CO"); //CO
			newContractNo.append(contractDate.replace("-", "")); //CO2020-04-27 -> CO20200427 -> CO2020042701
			newContractNo.append(String.format("%02d", i));	//CO + contractDate + 01 

		return newContractNo.toString();
	}

	@Override
	public HashMap<String, Object> addNewContract(HashMap<String,String[]>  workingContractList) {

		HashMap<String, Object> resultMap = null;
		HashMap<String,String> setValue = null;
		StringBuffer str = null;
		
			setValue=new HashMap<String,String>();
			for(String key:workingContractList.keySet()) {
				str=new StringBuffer();
				
				for(String value:workingContractList.get(key)) {
					if(key.equals("contractDate")) {
						String newContractNo=getNewContractNo(value);	
						str.append(newContractNo+",");
					}
					else str.append(value+",");
				}
					
				str.substring(0, str.length()-1);
				if(key.equals("contractDate"))
					setValue.put("newContractNo", str.toString());
				else
				setValue.put(key, str.toString());
			}
			resultMap=contractDetailDAO.insertContractDetail(setValue);
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (ContractDetailTO bean : contractDetailTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					/*contractDetailDAO.insertContractDetail(bean);*/
					insertList.add(bean.getContractDetailNo());

					break;

				case "UPDATE":

					/*contractDetailDAO.updateContractDetail(bean);*/
					updateList.add(bean.getContractDetailNo());

					break;

				case "DELETE":

					contractDetailDAO.deleteContractDetail(bean);
					deleteList.add(bean.getContractDetailNo());

					break;

				}


			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		}
		return resultMap;
	}

	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
													   //estimateNO , "Y"
			estimateDAO.changeContractStatusOfEstimate(estimateNo, contractStatus);

	}

}
