package com.estimulo.system.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.mapper.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.basicInfo.mapper.DepartmentDAO;
import com.estimulo.system.basicInfo.to.DepartmentTO;

@Component
public class DepartmentApplicationServiceImpl implements DepartmentApplicationService {
	@Autowired
	private DepartmentDAO departmentDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;


	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode, String workplaceCode) {
			
		ArrayList<DepartmentTO> departmentList = null;
		HashMap<String,String> param=new HashMap<>();
		param.put("searchCondition",searchCondition);
		param.put("searchCondition",companyCode);
		param.put("searchCondition",workplaceCode);
		departmentList = departmentDAO.selectDepartmentList(param);

		return departmentList;
	}

	public String getNewDepartmentCode(String companyCode) {
		
		ArrayList<DepartmentTO> departmentList = null;
		String newDepartmentCode = null;

			departmentList = departmentDAO.selectDepartmentListByCompany(companyCode);

			TreeSet<Integer> departmentCodeNoSet = new TreeSet<>();

			for (DepartmentTO bean : departmentList) {

				if (bean.getDeptCode().startsWith("DPT-")) {

					try {

						Integer no = Integer.parseInt(bean.getDeptCode().split("DPT-")[1]);
						departmentCodeNoSet.add(no);

					} catch (NumberFormatException e) {

						// "DPT-" �떎�쓬 遺�遺꾩쓣 Integer 濡� 蹂��솚�븯吏� 紐삵븯�뒗 寃쎌슦 : 洹몃깷 �떎�쓬 諛섎났臾� �떎�뻾

					}

				}

			}

			if (departmentCodeNoSet.isEmpty()) {
				newDepartmentCode = "DPT-" + String.format("%02d", 1);
			} else {
				newDepartmentCode = "DPT-" + String.format("%02d", departmentCodeNoSet.pollLast() + 1);
			}

		return newDepartmentCode;
	}


	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeBean = new CodeDetailTO();

			for (DepartmentTO bean : departmentList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					// �깉濡쒖슫 遺��꽌踰덊샇 �깮�꽦 �썑 bean �뿉 set
					String newDepartmentCode = getNewDepartmentCode(bean.getCompanyCode());
					bean.setDeptCode(newDepartmentCode);

					// 遺��꽌 �뀒�씠釉붿뿉 insert
					departmentDAO.insertDepartment(bean);
					insertList.add(bean.getDeptCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Insert
					detailCodeBean.setDivisionCodeNo("CO-03");
					detailCodeBean.setDetailCode(bean.getDeptCode());
					detailCodeBean.setDetailCodeName(bean.getDeptName());

					codeDetailDAO.insertDetailCode(detailCodeBean);

					break;

				case "UPDATE":

					departmentDAO.updateDepartment(bean);
					updateList.add(bean.getDeptCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Update
					detailCodeBean.setDivisionCodeNo("CO-03");
					detailCodeBean.setDetailCode(bean.getDeptCode());
					detailCodeBean.setDetailCodeName(bean.getDeptName());

					codeDetailDAO.updateDetailCode(detailCodeBean);

					break;

				case "DELETE":

					departmentDAO.deleteDepartment(bean);
					deleteList.add(bean.getDeptCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Delete
					detailCodeBean.setDivisionCodeNo("CO-03");
					detailCodeBean.setDetailCode(bean.getDeptCode());
					detailCodeBean.setDetailCodeName(bean.getDeptName());

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
