package com.zieta.tms.controller;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.VendorAdvanceDTO;
import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.response.VendorAdvanceResponse;
import com.zieta.tms.service.VendorAdvanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Vendor Advance  API")
public class VendorAdvanceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorAdvanceController.class);
	
	@Autowired
	VendorAdvanceService vendorAdvanceService;
	


	@ApiOperation(value = "GET vendor advacne ", notes = "Table reference:vendor_advance")
	@RequestMapping(value = "getVendorAdvanceById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public VendorAdvanceDTO getVendorAdvacneById(@RequestParam(required=true) Long id) {		
		Short notDeleted =0;		
		VendorAdvanceDTO vendorAdvanceDTO = null;
		try {
			vendorAdvanceDTO = vendorAdvanceService.findVendorAdvanceById(id,notDeleted);

		} catch (Exception e) {
			LOGGER.error("Error Occured in vendorAdvance#getVendorAdvanceById", e);
		}
		return vendorAdvanceDTO;		
	}

	@RequestMapping(value = "addVendorAdvance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add vendor advance to vendor_advance", notes = "Table reference:" + "vendor_advance")
	public VendorAdvance addVendorAdvance(@Valid @RequestBody VendorAdvance vendorAdvance) throws Exception {
		 vendorAdvanceService.addVendorAdvance(vendorAdvance);
		 return null;
	}
	
	//DELETE VENDOR INVOICE BY ID	
	@ApiOperation(value = "Deletes VENDOR ADVANCE from vendor_advance based on Id", notes = "Table reference: vendor_advance")
	@RequestMapping(value = "deletetVendorAdvacneById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deletetVendorAdvanceById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		vendorAdvanceService.deleteByVendorAdvanceId(id, modifiedBy);
	}
	
	//FOR HISTORY	
	@RequestMapping(value = "getVendorAdvanceHistoryByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorAdvanceResponse> getVendorAdvanceHistoryByUser(@RequestParam(required = true) Long userId,
			@RequestParam(required = true) String  startSubmitDate, 
			@RequestParam(required = true) String endSubmitDate) {
		List<VendorAdvanceResponse> vendorAdvanceRequestList = null;
		try {
			vendorAdvanceRequestList = vendorAdvanceService.findAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(userId, startSubmitDate,endSubmitDate );
		} catch (Exception e) {

			LOGGER.error("Error Occured in vendorAdvanceController#getActiveVendorAdvanceByUser", e);
		}
		return vendorAdvanceRequestList;

	}
	 //getAllActiveVendorInvoiceByUserIdAndBetweenInvoiceDate
	@ApiOperation(value = "List of vendor advance ", notes = "Table reference:vendor_advance")
	@RequestMapping(value = "getVendorAdvanceByUserAndSubmitDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorAdvanceDTO> getVendorAdvanceByUserAndAdvanceDate(@RequestParam(required = true) Long userId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate, 
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		List<VendorAdvanceDTO> vendorAdvanceResponseList = null;
		try {
				vendorAdvanceResponseList = vendorAdvanceService.getAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(userId, startDate, endDate);
			} catch (Exception e) {
				LOGGER.error("Error Occured in VendorSubmitController#getVendorSubmitByUserAndSubmitDate", e);
			}
		return vendorAdvanceResponseList;

	}
	 

	
}
