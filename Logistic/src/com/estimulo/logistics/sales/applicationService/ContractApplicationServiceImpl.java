package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.sales.dao.ContractDAO;
import com.estimulo.logistics.sales.dao.ContractDAOImpl;
import com.estimulo.logistics.sales.dao.ContractDetailDAO;
import com.estimulo.logistics.sales.dao.ContractDetailDAOImpl;
import com.estimulo.logistics.sales.dao.EstimateDAO;
import com.estimulo.logistics.sales.dao.EstimateDAOImpl;
import com.estimulo.logistics.sales.dao.EstimateDetailDAO;
import com.estimulo.logistics.sales.dao.EstimateDetailDAOImpl;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.ContractTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.estimulo.system.common.exception.DataAccessException;

public class ContractApplicationServiceImpl implements ContractApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ContractApplicationServiceImpl.class);

	// 싱글톤
	private static ContractApplicationService instance = new ContractApplicationServiceImpl();

	private ContractApplicationServiceImpl() {
	}

	public static ContractApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ ContractApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static ContractDAO contractDAO = ContractDAOImpl.getInstance();
	private static ContractDetailDAO contractDetailDAO = ContractDetailDAOImpl.getInstance();
	private static EstimateDAO estimateDAO = EstimateDAOImpl.getInstance();
	private static EstimateDetailDAO estimateDetailDAO = EstimateDetailDAOImpl.getInstance();
	

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractList 시작");
		}

		ArrayList<ContractInfoTO> contractInfoTOList = null;

		try {
			
			switch (searchCondition) {

			case "searchByDate":

				String startDate = paramArray[0];
				String endDate = paramArray[1];

				contractInfoTOList = contractDAO.selectContractListByDate(startDate, endDate);

				break;

			case "searchByCustomer":

				String customerCode = paramArray[0];

				contractInfoTOList = contractDAO.selectContractListByCustomer(customerCode);

				break;

			}

			for (ContractInfoTO bean : contractInfoTOList) {

				bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));

			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractList 종료");
		}
		return contractInfoTOList;

	}
	
	@Override
	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String, String> ableSearchConditionInfo) {
		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getDeliverableContractList 시작");
		}

		ArrayList<ContractInfoTO> contractInfoTOList = null;

		try {

				contractInfoTOList = contractDAO.selectDeliverableContractList(ableSearchConditionInfo);


			for (ContractInfoTO bean : contractInfoTOList) { // 해당 수주의 수주상세 리스트 세팅 

				bean.setContractDetailTOList(contractDetailDAO.selectDeliverableContractDetailList(bean.getContractNo()));

			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getDeliverableContractList 종료");
		}
		return contractInfoTOList;

	}
	
	
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractDetailList 시작");
		}

		ArrayList<ContractDetailTO> contractDetailTOList = null;

		try {

			contractDetailTOList = contractDetailDAO.selectContractDetailList(contractNo);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractDetailList 종료");
		}
		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getEstimateListInContractAvailable 시작");
		}

		ArrayList<EstimateTO> estimateListInContractAvailable = null;

		try {

			estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(startDate, endDate);
			//estimateListInContractAvailable = EstimateListInContractAvailable

			for (EstimateTO bean : estimateListInContractAvailable) {

				bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));

			}
			
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getEstimateListInContractAvailable 종료");
		}
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getNewContractNo 시작");
		}

		StringBuffer newContractNo = null;

		try {

			int i = contractDAO.selectContractCount(contractDate);
			System.out.println(i);
			newContractNo = new StringBuffer();
			newContractNo.append("CO"); //CO
			newContractNo.append(contractDate.replace("-", "")); //CO2020-04-27 -> CO20200427 -> CO2020042701
			newContractNo.append(String.format("%02d", i));	//CO + contractDate + 01 

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getNewContractNo 종료");
		}
		return newContractNo.toString();
	}

	@Override
	public HashMap<String, Object> addNewContract(HashMap<String,String[]>  workingContractList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : addNewContract 시작");
		}

		HashMap<String, Object> resultMap = null;
		HashMap<String,String> setValue = null;
		StringBuffer str = null;
		try {
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
		
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : addNewContracto 종료");
		}
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : batchContractDetailListProcess 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

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

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : batchContractDetailListProcess 종료");
		}
		return resultMap;
	}

	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : changeContractStatusInEstimate 시작");
		}

		try {
													   //estimateNO , "Y"
			estimateDAO.changeContractStatusOfEstimate(estimateNo, contractStatus);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : changeContractStatusInEstimate 종료");
		}
	}

}
