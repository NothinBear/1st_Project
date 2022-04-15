package com.estimulo.logistics.production.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkSiteLogTO {
	private String workOrderNo;
	private String itemCode;
	private String itemName;
	private String reaeson;
	private String workSiteName;
	private String workDate;
	private String productionProcessCode;
	private String productionProcessName;

}
