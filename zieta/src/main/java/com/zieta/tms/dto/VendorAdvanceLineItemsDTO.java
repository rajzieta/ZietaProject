package com.zieta.tms.dto;

import com.zieta.tms.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorAdvanceLineItemsDTO extends BaseEntity{	
	
    private Long id;
	private Long vendorAdvId;	
	private Long ExpType;	
	private String HsnCode;
	private Long amount;
	private String Remarks; 
		
}
