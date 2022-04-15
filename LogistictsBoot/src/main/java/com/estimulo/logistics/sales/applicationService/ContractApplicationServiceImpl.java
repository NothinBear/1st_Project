package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.logistics.sales.mapper.ContractDAO;
import com.estimulo.logistics.sales.mapper.ContractDetailDAO;
import com.estimulo.logistics.sales.mapper.EstimateDAO;
import com.estimulo.logistics.sales.mapper.EstimateDetailDAO;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.EstimateTO;

@Component
public class ContractApplicationServiceImpl implements ContractApplicationService {
	@Autowired
	private ContractDAO contractDAO;
	@Autowired
	private ContractDetailDAO contractDetailDAO;
	@Autowired
	private EstimateDAO estimateDAO;
	@Autowired
	private EstimateDetailDAO estimateDetailDAO;
	

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String startDate, String endDate, String customerCode) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;
			HashMap<String, String> map = new HashMap<>();
			map.put("searchCondition", searchCondition);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("customerCode", customerCode);
				contractInfoTOList = contractDAO.selectContractList(map);

			for (ContractInfoTO bean : contractInfoTOList) {

				bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));
			}
		return contractInfoTOList;

	}
	//
	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String, String> ableSearchConditionInfo) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;
				System.out.println(ableSearchConditionInfo.get("searchCondition"));
				contractInfoTOList = contractDAO.selectDeliverableContractList(ableSearchConditionInfo);


			for (ContractInfoTO bean : contractInfoTOList) {

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
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
			estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(map);
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

	      ModelMap resultMap = new ModelMap();
	      HashMap<String,String> setValue =new HashMap<String,String>();
	      StringBuffer str = null;
	         
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
	         contractDetailDAO.insertContractDetail(setValue);
	         
	         resultMap.put("gridRowJson", setValue.get("RESULT"));
	         resultMap.put("errorCode",setValue.get("ERROR_CODE"));
	         resultMap.put("errorMSG",setValue.get("ERROR_MSG"));
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
		HashMap<String, String> map = new HashMap<>();
		map.put("estimateNo", estimateNo);
		map.put("contractStatus", contractStatus);
		estimateDAO.changeContractStatusOfEstimate(map);

	}

}
