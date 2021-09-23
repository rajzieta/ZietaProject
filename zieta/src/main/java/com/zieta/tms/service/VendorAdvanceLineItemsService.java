package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.VendorAdvanceLineItemsDTO;
import com.zieta.tms.dto.VendorInvoiceLineItemsDTO;
import com.zieta.tms.model.VendorInvoicelineItems;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.VendorAdvanceLineItemsRequest;
import com.zieta.tms.request.VendorInvoiceLineItemsByVendorInvoiceIdRequest;

public interface VendorAdvanceLineItemsService {
	
	public List<VendorAdvanceLineItemsDTO> getAllActiveVendorAdvanceLineItems(Long clientId, Short isDelete);
	
	public List<VendorAdvanceLineItemsDTO> getAllActiveVendorAdvanceLineItemsByVindorAdvanceId(Long vendorInvId, Short isDelete);
	
	//public VendorInvoicelineItems addVendorInvoiceLineItems(VendorInvoicelineItems vendorInvoicelineItems) throws Exception;
	
	public void addVendorAdvanceLineItems(@Valid List<VendorAdvanceLineItemsRequest> vendorAdvancelineItems) throws Exception;
	
	public VendorAdvanceLineItemsDTO findVendorAdvanceLineItemsById(long id)throws Exception;
	
	public void deleteByVendorAdvanceLineItemsId(Long id, String modifiedBy) throws Exception;
	
	public VendorAdvanceLineItemsDTO findVendorAdvanceById(long id) throws Exception;
	
	public List<VendorAdvanceLineItemsDTO> getAllVendorLineItemsByVendorAdvanceId(Long InvoiceId);

	
	
}
