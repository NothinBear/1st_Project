package com.estimulo.logistics.sales.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.ContractTO;
import com.estimulo.logistics.sales.to.EstimateTO;

@Mapper
public interface ContractDAO {

	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(HashMap<String, String> param);

	public ArrayList<ContractInfoTO> selectContractList(HashMap<String, String> param);

	public ArrayList<ContractInfoTO> selectDeliverableContractList(HashMap<String, String> ableSearchConditionInfo);
	
	public int selectContractCount(String contractDate);

	public void insertContract(ContractTO TO);

	public void updateContract(ContractTO TO);

	public void deleteContract(ContractTO TO);

}
