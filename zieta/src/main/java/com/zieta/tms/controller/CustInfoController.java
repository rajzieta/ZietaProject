package com.zieta.tms.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.CustInfoDTO;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.RoleMaster;
import com.zieta.tms.request.RoleMasterEditRequest;
import com.zieta.tms.response.CustomerInfoModel;
import com.zieta.tms.response.CustomerInformationModel;
import com.zieta.tms.service.CustInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Customer Informationn API")
public class CustInfoController {

	@Autowired
	CustInfoService custInfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustInfoController.class);
	
	@ApiOperation(value = "List Customers along with clientCode", notes = "Table reference:client_info,cust_info")
	@RequestMapping(value = "getAllCustomers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustomerInformationModel>  getAllInfo() {
		List<CustomerInformationModel> clientInformationList = null;
		try {
			clientInformationList = custInfoService.getAllCustomers();
		} catch (Exception e) {
			LOGGER.error("Error Occured in CustInfoController#getAllInfo",e);
		}
		return clientInformationList;
	}

	@ApiOperation(value = "List Customers based on the clientId", notes = "Table reference: cust_info")
	@RequestMapping(value = "getAllCustomersByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustInfo>> getAllCustomersByClient(@RequestParam(required = true) Long  clientId) {
		try {
			List<CustInfo> cusInfoList = custInfoService.getAllCustomersByClient(clientId);

			return new ResponseEntity<List<CustInfo>>(cusInfoList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<CustInfo>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "List Customers based on the Company", notes = "Table reference:cust_orgnode_mapping,cust_info")
	@RequestMapping(value = "getAllCustomersByClientCompany", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerInfoModel>> getAllCustomersByClientCompany(@RequestParam(required = true) Long  clientId,
			@RequestParam(required = true) Long orgNode) {
		try {
			List<CustomerInfoModel> cusInfoList = custInfoService.findByClientIdAndOrgNode(clientId,orgNode);

			return new ResponseEntity<List<CustomerInfoModel>>(cusInfoList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<CustomerInfoModel>>(HttpStatus.NOT_FOUND);
		}
	}
	
	////Add, Edit and Delete API's for CustInfo
	
	
	@RequestMapping(value = "addCustInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addCustInfo(@Valid @RequestBody CustInfo custinfo) {
		custInfoService.addCustInfo(custinfo);
	}
	
	
	@ApiOperation(value = "Updates the UserRoles for the provided Id", notes = "Table reference: cust_info")
	@RequestMapping(value = "editCustInfoById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editCustInfoById(@Valid @RequestBody CustInfoDTO custinfoDTO) throws Exception {
		custInfoService.editCustInfoById(custinfoDTO);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from cust_info based on Id", notes = "Table reference: cust_info")
	@RequestMapping(value = "deleteCustInfoById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteCustInfoById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		custInfoService.deleteCustInfoById(id, modifiedBy);
	}
	
	

}
