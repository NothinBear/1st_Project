package com.estimulo.logistics.sales.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.sales.to.EstimateTO;

@Mapper
public interface EstimateDAO {
	public ArrayList<EstimateTO> selectEstimateList(String dateSearchCondition, String startDate, String endDate);

	public EstimateTO selectEstimate(String estimateNo);

	public int selectEstimateCount(String estimateDate);

	public void insertEstimate(EstimateTO TO);

	public void updateEstimate(EstimateTO TO);

	public void changeContractStatusOfEstimate(HashMap<String,String> param);

}
