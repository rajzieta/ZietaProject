package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorInvoiceLineItemsByVendorInvoiceIdRequest {
	
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
	private String createdBy;
	private String modifiedBy;
	private short isDelete;
}
