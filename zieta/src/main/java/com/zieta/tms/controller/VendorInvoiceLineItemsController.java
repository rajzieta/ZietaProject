package com.zieta.tms.controller;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.dto.VendorInvoiceDTO;
import com.zieta.tms.dto.VendorInvoiceLineItemsDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.model.VendorInvoicelineItems;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.VendorInvoiceLineItemsByVendorInvoiceIdRequest;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.VendorInvoiceLineItemsService;
import com.zieta.tms.service.VendorInvoiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Vendor Invoice LineItems API")
public class VendorInvoiceLineItemsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorInvoiceLineItemsController.class);

	@Autowired
	VendorInvoiceService vendorInvoiceService;
	
	@Autowired
	VendorInvoiceLineItemsService vendorInvoiceLineItemsService;	

	//GET  ACTIVE VENDOR INVOICE LINEITEMS
	@ApiOperation(value = "List OF vendor lineitems invoice ", notes = "Table reference:expense_Template")
	@RequestMapping(value = "getVendorInvoiceLineItemsById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public VendorInvoiceLineItemsDTO getVendorInvoiceLineItemsById(@RequestParam(required=true) Long id) {		
		Short notDeleted =0;		
		VendorInvoiceLineItemsDTO vendorInvoiceLineItemsDTO = null;
		try {
			vendorInvoiceLineItemsDTO = vendorInvoiceLineItemsService.findVendorInvoiceLineItemsById(id);

		} catch (Exception e) {
			LOGGER.error("Error Occured in vendorInvoice#getVendorInvoiceById", e);
		}
		return vendorInvoiceLineItemsDTO;
		//return null;
	}
	
	/*
	 * ADD VENDOR INVOICE LINEITEMS
	 */
	
	@RequestMapping(value = "addVendorInvoiceLineItems", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add vendor invoice line Item to vendor_invoice_lineitems", notes = "Table reference:" + "vendor_invoice_lineitems")
	public void addVendorInvoiceLineItems(@Valid @RequestBody List<VendorInvoiceLineItemsByVendorInvoiceIdRequest> vendorInvoicelineItems) throws Exception {
		vendorInvoiceLineItemsService.addVendorInvoiceLineItems(vendorInvoicelineItems);
		
	}
	
	//DELETE VENDOR INVOICE LINEITEMS BY ID	
	@ApiOperation(value = "Deletes VENDOR INVOICE LINEITEMS from vendor_invoice_lineitems based on Id", notes = "Table reference: vendor_invoice_lineitems")
	@RequestMapping(value = "deletetVendorInvoiceLineItemsById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteVendorInvoiceLineItemsById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		vendorInvoiceLineItemsService.deleteByVendorInvoiceLineItemsId(id, modifiedBy);
	} 	 
	
	//GET ALL VENDOR INVOICE LINEITEMS BY VENDOR INVOICE ID
	@ApiOperation(value = "List vendor invoice lineitems ", notes = "Table reference:vendor_invoice_lineitems")
	@RequestMapping(value = "getAllVendorLineItemsByVnedorInvoiceId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorInvoiceLineItemsDTO> getAllVendorLineItemsByVnedorInvoiceId(@RequestParam(required=true) Long vendorInvId) {		
		Short notDeleted =0;		
		List<VendorInvoiceLineItemsDTO> invoiceLineItemsList = null;
		try {
				invoiceLineItemsList = vendorInvoiceLineItemsService.getAllVendorLineItemsByVendorInvoiceId(vendorInvId);

		} catch (Exception e) {
			LOGGER.error("Error Occured in VendorInvoiceLineItemsController#getAllVendorLineItemsByVnedorInvoiceId", e);
		}
		return invoiceLineItemsList;
	}	
	
	
	
	 
	 

	
}
