package com.estimulo.system.base.applicationService;

import java.util.ArrayList;

import com.estimulo.system.base.to.AddressTO;

public interface AddressApplicationService {
		
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
}
