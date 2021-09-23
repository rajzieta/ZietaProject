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
import com.zieta.tms.dto.VendorAdvanceLineItemsDTO;
import com.zieta.tms.dto.VendorInvoiceDTO;
import com.zieta.tms.dto.VendorInvoiceLineItemsDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.model.VendorInvoicelineItems;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.VendorAdvanceLineItemsRequest;
import com.zieta.tms.request.VendorInvoiceLineItemsByVendorInvoiceIdRequest;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.VendorAdvanceLineItemsService;
import com.zieta.tms.service.VendorAdvanceService;
import com.zieta.tms.service.VendorInvoiceLineItemsService;
import com.zieta.tms.service.VendorInvoiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Vendor Advance LineItems API")
public class VendorAdvanceLineItemsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorAdvanceLineItemsController.class);

	@Autowired
	VendorAdvanceService vendorAdvanceService;
	
	@Autowired
	VendorAdvanceLineItemsService vendorAdvanceLineItemsService;	

	//GET  ACTIVE VENDOR INVOICE LINEITEMS
	@ApiOperation(value = "List OF vendor lineitems advance ", notes = "Table reference:vendor_adv_lineitems")
	@RequestMapping(value = "getVendorAdvanceLineItemsById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public VendorAdvanceLineItemsDTO getVendorAdvanceLineItemsById(@RequestParam(required=true) Long id) {		
		Short notDeleted =0;		
		VendorAdvanceLineItemsDTO vendorAdvanceLineItemsDTO = null;
		try {
			//vendorAdvanceLineItemsDTO = vendorAdvanceLineItemsService.getAllActiveVendorAdvanceLineItemsByVindorAdvanceId(id,notDeleted);
			
			vendorAdvanceLineItemsDTO = vendorAdvanceLineItemsService.findVendorAdvanceById(id);
			
			
			//findVendorAdvanceById
		} catch (Exception e) {
			LOGGER.error("Error Occured in vendorInvoice#getVendorInvoiceById", e);
		}
		return vendorAdvanceLineItemsDTO;
		//return null;
	}
	
	/*
	 * ADD VENDOR INVOICE LINEITEMS
	 */
	
	@RequestMapping(value = "addVendorAdvanceLineItems", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add vendor advance line Item to vendor_adv_lineitems", notes = "Table reference:" + "vendor_adv_lineitems")
	public void addVendorAdvanceLineItems(@Valid @RequestBody List<VendorAdvanceLineItemsRequest> vendorAdvanceLineItems) throws Exception {
		vendorAdvanceLineItemsService.addVendorAdvanceLineItems(vendorAdvanceLineItems);
		
	}
	
	//DELETE VENDOR INVOICE LINEITEMS BY ID	
	@ApiOperation(value = "Deletes VENDOR ADVANCE LINEITEMS from vendor_advance_lineitems based on Id", notes = "Table reference: vendor_advance_lineitems")
	@RequestMapping(value = "deletetVendorAdvanceLineItemsById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteVendorAdvanceLineItemsById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		vendorAdvanceLineItemsService.deleteByVendorAdvanceLineItemsId(id, modifiedBy);
	} 	 
	
	//GET ALL VENDOR INVOICE LINEITEMS BY VENDOR INVOICE ID
	@ApiOperation(value = "List vendor advance lineitems ", notes = "Table reference:vendor_advance_lineitems")
	@RequestMapping(value = "getAllVendorLineItemsByVnedoradvanceId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorAdvanceLineItemsDTO> getAllVendorLineItemsByVendorAdvanceId(@RequestParam(required=true) Long vendorAdvId) {		
		Short notDeleted =0;		
		List<VendorAdvanceLineItemsDTO> advanceLineItemsList = null;
		try {
				advanceLineItemsList = vendorAdvanceLineItemsService.getAllVendorLineItemsByVendorAdvanceId(vendorAdvId);

		} catch (Exception e) {
			LOGGER.error("Error Occured in VendorAdvanceLineItemsController#getAllVendorLineItemsByVendorAdvanceId", e);
		}
		return advanceLineItemsList;
	}	
	
	
	
	 
	 

	
}
