package com.estimulo.logistics.sales.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.sales.to.EstimateDetailTO;

@Mapper
public interface EstimateDetailDAO {

	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo);

	public int selectEstimateDetailCount(String estimateNo);

	public void insertEstimateDetail(EstimateDetailTO TO);

	public void updateEstimateDetail(EstimateDetailTO TO);

	public void deleteEstimateDetail(EstimateDetailTO TO);

	public int selectEstimateDetailSeq(String estimateDate);
	
	public void initDetailSeq();
	
	public void reArrangeEstimateDetail(EstimateDetailTO bean,String newSeq);
}