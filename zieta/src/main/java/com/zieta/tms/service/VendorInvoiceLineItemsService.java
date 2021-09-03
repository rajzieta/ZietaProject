package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.dto.VendorInvoiceLineItemsDTO;
import com.zieta.tms.model.VendorInvoicelineItems;

public interface VendorInvoiceLineItemsService {
	
	public List<VendorInvoiceLineItemsDTO> getAllActiveVendorInvoiceLineItems(Long clientId, Short isDelete);
	
	public List<VendorInvoiceLineItemsDTO> getAllActiveVendorInvoiceLineItemsByVindorInvoiceId(Long vendorInvId, Short isDelete);
	
	public VendorInvoicelineItems addVendorInvoiceLineItems(VendorInvoicelineItems vendorInvoicelineItems) throws Exception;
	
	public VendorInvoiceLineItemsDTO findVendorInvoiceLineItemsById(long id)throws Exception;
	
	public void deleteByVendorInvoiceLineItemsId(Long id, String modifiedBy) throws Exception;
	
	public VendorInvoiceLineItemsDTO findVendorInvoiceById(long id) throws Exception;
	
	public List<VendorInvoiceLineItemsDTO> getAllVendorLineItemsByVendorInvoiceId(Long InvoiceId);

	
	
}
