package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.model.BaseEntity;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse.AdditionalDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorInvoiceLineItemsDTO extends BaseEntity{
	
    private Long id;
	private Long invId;
	private Date serviceDate;
	private Long expType;
	private Long hsnCode;
	private Short amount;
	private String allocationCode;
	private String internalOrder;
	private int recoverable;
	private String costCenter;
	private String remarks;
		
}
