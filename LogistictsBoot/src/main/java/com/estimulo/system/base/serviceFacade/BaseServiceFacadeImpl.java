package com.estimulo.system.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.system.base.applicationService.AddressApplicationService;
import com.estimulo.system.base.applicationService.CodeApplicationService;
import com.estimulo.system.base.applicationService.ReportApplicationService;
import com.estimulo.system.base.to.AddressTO;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;

@Service
public class BaseServiceFacadeImpl implements BaseServiceFacade {

	// 참조변수 선언
	@Autowired
	private CodeApplicationService codeApplicationService;
	@Autowired
	private AddressApplicationService addressApplicationService;
	@Autowired
	private ReportApplicationService reportApplicationService;
	
	@Override
	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode) {

		return codeApplicationService.getDetailCodeList(divisionCode);
	}

	@Override
	public ArrayList<CodeTO> getCodeList() {

		return codeApplicationService.getCodeList();
	}

	@Override
	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode) {

		return codeApplicationService.checkCodeDuplication(divisionCode, newDetailCode);
	}

	@Override
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList) {

		return codeApplicationService.batchCodeListProcess(codeList);
	}

	@Override
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList) {

		return codeApplicationService.batchDetailCodeListProcess(detailCodeList);
	}

	@Override
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList) {

		return codeApplicationService.changeCodeUseCheckProcess(detailCodeList);
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber) {

		return addressApplicationService.getAddressList(sidoName, searchAddressType, searchValue, mainNumber);
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
