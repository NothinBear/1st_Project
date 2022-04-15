package com.estimulo.logistics.sales.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.estimulo.logistics.sales.to.ContractDetailTO;

@Mapper
public interface ContractDetailDAO {

	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo);

	public ArrayList<ContractDetailTO> selectDeliverableContractDetailList(String contractNo);
	
	public int selectContractDetailCount(String contractNo);

	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(HashMap<String,String> param);
/*
	public void insertContractDetail(ContractDetailTO TO);

	public void updateContractDetail(ContractDetailTO TO);*/

	public void changeMpsStatusOfContractDetail(HashMap<String,String> param);

	public void deleteContractDetail(ContractDetailTO TO);
	
	public HashMap<String,Object> insertContractDetail(HashMap<String,String>  workingContractList);

}
