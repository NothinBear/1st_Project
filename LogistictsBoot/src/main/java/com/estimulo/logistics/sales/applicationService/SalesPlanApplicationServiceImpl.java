package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.sales.mapper.SalesPlanDAO;
import com.estimulo.logistics.sales.to.SalesPlanTO;
@Component
public class SalesPlanApplicationServiceImpl implements SalesPlanApplicationService {

	// DAO 참조변수 선언
	@Autowired
	private SalesPlanDAO salesPlanDAO;

	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {

		HashMap<String, String> param = new HashMap<>();
		param.put("dateSearchCondition", dateSearchCondition);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		return salesPlanDAO.selectSalesPlanList(param);
	}

	public String getNewSalesPlanNo(String salesPlanDate) {

		StringBuffer newEstimateNo = null;

			int newNo = salesPlanDAO.selectSalesPlanCount(salesPlanDate);

			newEstimateNo = new StringBuffer();

			newEstimateNo.append("SA");
			newEstimateNo.append(salesPlanDate.replace("-", ""));
			newEstimateNo.append(String.format("%02d", newNo)); // 2자리 숫자
			
		return newEstimateNo.toString();
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (SalesPlanTO bean : salesPlanTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					// 새로운 판매계획일련번호 생성
					String newSalesPlanNo = getNewSalesPlanNo(bean.getSalesPlanDate());

					// Bean 에 새로운 판매계획일련번호 세팅
					bean.setSalesPlanNo(newSalesPlanNo);

					salesPlanDAO.insertSalesPlan(bean);

					insertList.add(newSalesPlanNo);

					break;

				case "UPDATE":

					salesPlanDAO.updateSalesPlan(bean);

					updateList.add(bean.getSalesPlanNo());

					break;

				case "DELETE":

					salesPlanDAO.deleteSalesPlan(bean);

					deleteList.add(bean.getSalesPlanNo());

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);
			
		return resultMap;

	}

}
