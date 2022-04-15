package com.estimulo.system.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.base.applicationService.AddressApplicationService;
import com.estimulo.system.base.applicationService.CodeApplicationService;
import com.estimulo.system.base.applicationService.ReportApplicationService;
import com.estimulo.system.base.to.AddressTO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;


public class BaseServiceFacadeImpl implements BaseServiceFacade {

	// 참조변수 선언
	private CodeApplicationService codeApplicationService;
	private AddressApplicationService addressApplicationService;
	private ReportApplicationService reportApplicationService;
	public void setCodeApplicationService(CodeApplicationService codeApplicationService) {
		this.codeApplicationService = codeApplicationService;
	}
	public void setAddressApplicationService(AddressApplicationService addressApplicationService) {
		this.addressApplicationService = addressApplicationService;
	}
	public void setReportApplicationService(ReportApplicationService reportApplicationService) {
		this.reportApplicationService = reportApplicationService;
	}
	@Override
	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode) {

		ArrayList<CodeDetailTO> codeDetailList = null;

			codeDetailList = codeApplicationService.getDetailCodeList(divisionCode);

		return codeDetailList;
	}

	@Override
	public ArrayList<CodeTO> getCodeList() {

		ArrayList<CodeTO> codeList = null;

			codeList = codeApplicationService.getCodeList();

		return codeList;
	}

	@Override
	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode) {

		Boolean flag = false;

			flag = codeApplicationService.checkCodeDuplication(divisionCode, newDetailCode);
			
		return flag;
	}

	@Override
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList) {

		HashMap<String, Object> resultMap = null;

			resultMap = codeApplicationService.batchCodeListProcess(codeList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList) {

		HashMap<String, Object> resultMap = null;

			resultMap = codeApplicationService.batchDetailCodeListProcess(detailCodeList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList) {

		HashMap<String, Object> resultMap = null;

			resultMap = codeApplicationService.changeCodeUseCheckProcess(detailCodeList);
			
		return resultMap;
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber) {

		ArrayList<AddressTO> addressList = null;

			addressList = addressApplicationService.getAddressList(sidoName, searchAddressType, searchValue, mainNumber);

		return addressList;
	}
	@Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {
		// TODO Auto-generated method stub
		return reportApplicationService.getEstimateReport(estimateNo);
	}

	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {
		// TODO Auto-generated method stub
		return reportApplicationService.getContractReport(contractNo);
	}

}
