package com.estimulo.system.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.estimulo.system.base.dao.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.basicInfo.dao.CustomerDAO;
import com.estimulo.system.basicInfo.to.CustomerTO;

public class CustomerApplicationServiceImpl implements CustomerApplicationService {

	// 李몄“蹂��닔 �꽑�뼵
	private CustomerDAO customerDAO;
	private CodeDetailDAO codeDetailDAO;
	public void setCodeDetailDAO(CodeDetailDAO codeDetailDAO) {
		this.codeDetailDAO = codeDetailDAO;
	}
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode,String itemGroupCode) {

		ArrayList<CustomerTO> customerList = null;
			customerList = customerDAO.selectCustomerList(searchCondition, companyCode,  workplaceCode, itemGroupCode);

		return customerList;
	}

	public String getNewCustomerCode(String companyCode) {

		ArrayList<CustomerTO> customerList = null;
		String newCustomerCode = null;

			customerList = customerDAO.selectCustomerListByCompany();

			TreeSet<Integer> customerCodeNoSet = new TreeSet<>();

			for (CustomerTO bean : customerList) {

				if (bean.getCustomerCode().startsWith("PTN-")) {

					try {

						Integer no = Integer.parseInt(bean.getCustomerCode().split("PTN-")[1]);
						customerCodeNoSet.add(no);

					} catch (NumberFormatException e) {

						// "PTN-" �떎�쓬 遺�遺꾩쓣 Integer 濡� 蹂��솚�븯吏� 紐삵븯�뒗 寃쎌슦 : 洹몃깷 �떎�쓬 諛섎났臾� �떎�뻾

					}

				}

			}

			if (customerCodeNoSet.isEmpty()) {
				newCustomerCode = "PTN-" + String.format("%02d", 1);
			} else {
				newCustomerCode = "PTN-" + String.format("%02d", customerCodeNoSet.pollLast() + 1);
			}

		return newCustomerCode;
	}

	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeBean = new CodeDetailTO();

			for (CustomerTO bean : customerList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					// �깉濡쒖슫 遺��꽌踰덊샇 �깮�꽦 �썑 bean �뿉 set
					String newCustomerCode = getNewCustomerCode(bean.getWorkplaceCode());
					bean.setCustomerCode(newCustomerCode);

					// 遺��꽌 �뀒�씠釉붿뿉 insert
					customerDAO.insertCustomer(bean);
					insertList.add(bean.getCustomerCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Insert
					detailCodeBean.setDivisionCodeNo("CL-01");
					detailCodeBean.setDetailCode(bean.getCustomerCode());
					detailCodeBean.setDetailCodeName(bean.getCustomerName());

					codeDetailDAO.insertDetailCode(detailCodeBean);

					break;

				case "UPDATE":

					customerDAO.updateCustomer(bean);
					updateList.add(bean.getCustomerCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Update
					detailCodeBean.setDivisionCodeNo("CL-01");
					detailCodeBean.setDetailCode(bean.getCustomerCode());
					detailCodeBean.setDetailCodeName(bean.getCustomerName());

					codeDetailDAO.updateDetailCode(detailCodeBean);

					break;

				case "DELETE":

					customerDAO.deleteCustomer(bean);
					deleteList.add(bean.getCustomerCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Delete
					detailCodeBean.setDivisionCodeNo("CL-01");
					detailCodeBean.setDetailCode(bean.getCustomerCode());
					detailCodeBean.setDetailCodeName(bean.getCustomerName());

					codeDetailDAO.deleteDetailCode(detailCodeBean);

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		return resultMap;
	}

}
