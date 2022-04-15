package com.estimulo.logistics.logisticsInfo.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItemGroupTO {
	 private String itemGroupCode;
	 private String description;
	 private String itemGroupName;
}