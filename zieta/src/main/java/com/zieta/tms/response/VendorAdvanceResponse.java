package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.model.VendorAdvanceLineItems;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorAdvanceResponse {
	
	VendorAdvance vendorAdvance;	
	//List<VendorInvoicelineItems> VendorInvoicelineItemsList;
	List<VendorAdvanceLineItems> VendorAdvancelineItemsList;
	//List<ven>
	
}
