package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorAdvanceLineItemsRequest {
	
	private Long id;
	private Long vendorAdvId;	
	private Long ExpType;
	private long amount;
	private String HsnCode;
	private String Remarks;
	private String createdBy;
	private String modifiedBy;
	private short isDelete;
}
