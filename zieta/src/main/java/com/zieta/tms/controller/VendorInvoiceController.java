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
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.VendorInvoiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Vendor Invoice  API")
public class VendorInvoiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorInvoiceController.class);

	@Autowired
	ExpenseTemplateService expenseTemplateService;
	
	@Autowired
	VendorInvoiceService vendorInvoiceService;
	

	//GET ALL ACTIVE EXPENSE TEMPLATE WITHOUT CLIENT ID
	@ApiOperation(value = "List Expense Template ", notes = "Table reference:expense_Template")
	@RequestMapping(value = "getVendorInvoiceById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public VendorInvoiceDTO getVendorInvoiceById(@RequestParam(required=true) Long id) {		
		Short notDeleted =0;		
		VendorInvoiceDTO vendorInvoiceDTO = null;
		try {
			vendorInvoiceDTO = vendorInvoiceService.findVendorInvoiceById(id);

		} catch (Exception e) {
			LOGGER.error("Error Occured in vendorInvoice#getVendorInvoiceById", e);
		}
		return vendorInvoiceDTO;
	}

	@RequestMapping(value = "addVendorInvoice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add vendor invoice to vendor_invoice", notes = "Table reference:" + "vendor_invoice")
	public VendorInvoice addVendorInvoice(@Valid @RequestBody VendorInvoice vendorInvoice) throws Exception {
		return vendorInvoiceService.addVendorInvoice(vendorInvoice);
	}
	
	//DELETE VENDOR INVOICE BY ID	
	@ApiOperation(value = "Deletes VENDOR INVOICE from vendor_invoce based on Id", notes = "Table reference: vendor_invoce")
	@RequestMapping(value = "deletetVendorInvoiceById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deletetVendorInvoiceById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		vendorInvoiceService.deleteByVendorInvoiceId(id, modifiedBy);
	} 
	 
	
	 
	 

	
}
