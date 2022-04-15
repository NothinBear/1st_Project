package com.estimulo.logistics.production.to;

import com.estimulo.system.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MrpTO extends BaseTO {
	
	private String mrpNo;
	private String mpsNo;
	private String mrpGatheringNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String unitOfMrp;
	private int requiredAmount;
	private String orderDate;
	private String requiredDate;
	private String mrpGatheringStatus;
	private String requestStatus;
	private String planAmount;
	private String firstMrpNo; 
	private String lastMrpNo;
	private int leng;
	  
}