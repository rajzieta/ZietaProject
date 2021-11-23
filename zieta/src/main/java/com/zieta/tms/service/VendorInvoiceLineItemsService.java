package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.VendorInvoiceLineItemsDTO;
import com.zieta.tms.model.VendorInvoicelineItems;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.VendorInvoiceLineItemsByVendorInvoiceIdRequest;

public interface VendorInvoiceLineItemsService {
	
	public List<VendorInvoiceLineItemsDTO> getAllActiveVendorInvoiceLineItems(Long clientId, Short isDelete);
	
	public List<VendorInvoiceLineItemsDTO> getAllActiveVendorInvoiceLineItemsByVindorInvoiceId(Long vendorInvId, Short isDelete);
	
	//public VendorInvoicelineItems addVendorInvoiceLineItems(VendorInvoicelineItems vendorInvoicelineItems) throws Exception;
	
	public void addVendorInvoiceLineItems(@Valid List<VendorInvoiceLineItemsByVendorInvoiceIdRequest> vendorInvoicelineItems) throws Exception;
	
	public VendorInvoiceLineItemsDTO findVendorInvoiceLineItemsById(long id)throws Exception;
	
	public void deleteByVendorInvoiceLineItemsId(Long id, String modifiedBy) throws Exception;
	
	public VendorInvoiceLineItemsDTO findVendorInvoiceById(long id) throws Exception;
	
	public List<VendorInvoiceLineItemsDTO> getAllVendorLineItemsByVendorInvoiceId(Long InvoiceId);

	
	
}
