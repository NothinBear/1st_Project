package com.estimulo.hr.affair.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.estimulo.hr.affair.dao.EmpSearchingDAO;
import com.estimulo.hr.affair.dao.EmployeeBasicDAO;
import com.estimulo.hr.affair.dao.EmployeeDetailDAO;
import com.estimulo.hr.affair.dao.EmployeeSecretDAO;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;
import com.estimulo.system.base.dao.CodeDetailDAO;
import com.estimulo.system.base.to.CodeDetailTO;

public class EmpApplicationServiceImpl implements EmpApplicationService {

	private EmployeeBasicDAO employeeBasicDAO;
	private EmployeeDetailDAO employeeDetailDAO;
	private EmployeeSecretDAO employeeSecretDAO;
	private EmpSearchingDAO emplSearchingDAO;
	private CodeDetailDAO codeDetailDAO;
	
	
	public void setEmployeeBasicDAO(EmployeeBasicDAO employeeBasicDAO) {
		this.employeeBasicDAO = employeeBasicDAO;
	}

	public void setEmployeeDetailDAO(EmployeeDetailDAO employeeDetailDAO) {
		this.employeeDetailDAO = employeeDetailDAO;
	}

	public void setEmployeeSecretDAO(EmployeeSecretDAO employeeSecretDAO) {
		this.employeeSecretDAO = employeeSecretDAO;
	}

	public void setEmplSearchingDAO(EmpSearchingDAO emplSearchingDAO) {
		this.emplSearchingDAO = emplSearchingDAO;
	}

	public void setCodeDetailDAO(CodeDetailDAO codeDetailDAO) {
		this.codeDetailDAO = codeDetailDAO;
	}

	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray) {
		ArrayList<EmpInfoTO> empList = null;

			empList = emplSearchingDAO.selectAllEmpList(searchCondition, paramArray);

			for (EmpInfoTO bean : empList) {

				bean.setEmpDetailTOList(
						employeeDetailDAO.selectEmployeeDetailList(bean.getCompanyCode(), bean.getEmpCode()));

				bean.setEmpSecretTOList(
						employeeSecretDAO.selectEmployeeSecretList(bean.getCompanyCode(), bean.getEmpCode()));

			}

		return empList;

	}

	public EmpInfoTO getEmpInfo(String companyCode, String empCode) {

		EmpInfoTO bean = new EmpInfoTO();

			ArrayList<EmployeeDetailTO> empDetailTOList = employeeDetailDAO.selectEmployeeDetailList(companyCode, empCode);

			ArrayList<EmployeeSecretTO> empSecretTOList = employeeSecretDAO.selectEmployeeSecretList(companyCode, empCode);

			bean.setEmpDetailTOList(empDetailTOList);
			bean.setEmpSecretTOList(empSecretTOList);

			EmployeeBasicTO basicBean = employeeBasicDAO.selectEmployeeBasicTO(companyCode, empCode);

			if (basicBean != null) {

				bean.setCompanyCode(companyCode);
				bean.setEmpCode(empCode);
				bean.setEmpName(basicBean.getEmpName());
				bean.setEmpEngName(basicBean.getEmpEngName());
				bean.setSocialSecurityNumber(basicBean.getSocialSecurityNumber());
				bean.setHireDate(basicBean.getHireDate());
				bean.setRetirementDate(basicBean.getRetirementDate());
				bean.setUserOrNot(basicBean.getUserOrNot());
				bean.setBirthDate(basicBean.getBirthDate());
				bean.setGender(basicBean.getGender());

			}

		return bean;

	}

	public String getNewEmpCode(String companyCode) {

		ArrayList<EmployeeBasicTO> empBasicList = null;
		String newEmpCode = null;

			empBasicList = employeeBasicDAO.selectEmployeeBasicList(companyCode);

			TreeSet<Integer> empCodeNoSet = new TreeSet<>();

			for (EmployeeBasicTO TO : empBasicList) {

				if (TO.getEmpCode().startsWith("EMP-")) {

					try {

						Integer no = Integer.parseInt(TO.getEmpCode().split("EMP-")[1]);
						empCodeNoSet.add(no);

					} catch (NumberFormatException e) {

						// "EMP-" �떎�쓬 遺�遺꾩쓣 Integer 濡� 蹂��솚�븯吏� 紐삵븯�뒗 寃쎌슦 : 洹몃깷 �떎�쓬 諛섎났臾� �떎�뻾

					}

				}

			}

			if (empCodeNoSet.isEmpty()) {
				newEmpCode = "EMP-" + String.format("%03d", 1);
			} else {
				newEmpCode = "EMP-" + String.format("%03d", empCodeNoSet.pollLast() + 1);
			}

		return newEmpCode;
	}

	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList) {


		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			// ArrayList<String> updateList = new ArrayList<>();
			// ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeTO = new CodeDetailTO();

			for (EmployeeBasicTO TO : empBasicList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					employeeBasicDAO.insertEmployeeBasic(TO);

					insertList.add(TO.getEmpCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Insert
					detailCodeTO.setDivisionCodeNo("HR-02");
					detailCodeTO.setDetailCode(TO.getEmpCode());
					detailCodeTO.setDetailCodeName(TO.getEmpEngName());

					codeDetailDAO.insertDetailCode(detailCodeTO);

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			// resultMap.put("UPDATE", updateList);
			// resultMap.put("DELETE", deleteList);

		return resultMap;

	}

	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			// ArrayList<String> updateList = new ArrayList<>();
			// ArrayList<String> deleteList = new ArrayList<>();

			for (EmployeeDetailTO bean : empDetailList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					employeeDetailDAO.insertEmployeeDetail(bean);
					insertList.add(bean.getEmpCode());

					// �궗�썝 怨꾩젙 �젙吏� => EMPLOYEE_BASIC �뀒�씠釉붿쓽 USER_OR_NOT 而щ읆�쓣 "N" �쑝濡� 蹂�寃�
					// �깉濡쒖슫 userPassWord 瑜� null 濡� �엯�젰
					if (bean.getUpdateHistory().equals("怨꾩젙 �젙吏�")) {

						changeEmpAccountUserStatus(bean.getCompanyCode(), bean.getEmpCode(), "N");

						// �궗�썝 怨꾩젙 �젙吏� => EMPLOYEE_SECRET �뀒�씠釉붿뿉 userPassWord 媛� null �씤 �깉濡쒖슫 EmployeeSecretTO
						// �깮�꽦, Insert
						int newSeq = employeeSecretDAO.selectUserPassWordCount(bean.getCompanyCode(), bean.getEmpCode());

						EmployeeSecretTO newSecretBean = new EmployeeSecretTO();

						newSecretBean.setCompanyCode(bean.getCompanyCode());
						newSecretBean.setEmpCode(bean.getEmpCode());
						newSecretBean.setSeq(newSeq);

						employeeSecretDAO.insertEmployeeSecret(newSecretBean);

					}

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			// resultMap.put("UPDATE", updateList);
			// resultMap.put("DELETE", deleteList);
		return resultMap;

	}

	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			// ArrayList<String> updateList = new ArrayList<>();
			// ArrayList<String> deleteList = new ArrayList<>();

			for (EmployeeSecretTO TO : empSecretList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					employeeSecretDAO.insertEmployeeSecret(TO);

					insertList.add(TO.getEmpCode());

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			// resultMap.put("UPDATE", updateList);
			// resultMap.put("DELETE", deleteList);

		return resultMap;

	}

	@Override
	public Boolean checkUserIdDuplication(String companyCode, String newUserId) {

		ArrayList<EmployeeDetailTO> empDetailList = null;
		Boolean duplicated = false;

			empDetailList = employeeDetailDAO.selectUserIdList(companyCode);

			for (EmployeeDetailTO TO : empDetailList) {

				if (TO.getUserId().equals(newUserId)) {

					duplicated = true;

				}

			}

		return duplicated; // 以묐났�맂 肄붾뱶�씠硫� true 諛섑솚
	}

	@Override
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode) {

		ArrayList<EmployeeBasicTO> empBasicList = null;
		Boolean duplicated = false;

			empBasicList = employeeBasicDAO.selectEmployeeBasicList(companyCode);

			for (EmployeeBasicTO TO : empBasicList) {

				if (TO.getEmpCode().equals(newEmpCode)) {

					duplicated = true;

				}

			}
			
		return duplicated; // 以묐났�맂 肄붾뱶�씠硫� true 諛섑솚
	}

	@Override
	public void changeEmpAccountUserStatus(String companyCode, String empCode, String userStatus) {

			employeeBasicDAO.changeUserAccountStatus(companyCode, empCode, userStatus);

	}

}
