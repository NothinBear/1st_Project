package com.estimulo.system.basicInfo.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkplaceTO extends BaseTO {
	private String workplaceCeoName;
	private String isMainOffice;
	private String workplaceDetailAddress;
	private String workplaceBusinessConditions;
	private String workplaceBusinessItems;
	private String workplaceFaxNumber;
	private String workplaceEstablishDate;
	private String businessLicenseNumber;
	private String workplaceTelNumber;
	private String workplaceName;
	private String workplaceBasicAddress;
	private String workplaceCloseDate;
	private String workplaceCode;
	private String companyCode;
	private String workplaceOpenDate;
	private String corporationLicenseNumber;
	private String workplaceZipCode;

}