package com.zieta.tms.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.dto.EmployeeAdvanceDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ProcessStepsDTO;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UserQualificationDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserQualification;
import com.zieta.tms.model.EmployeeAdvance;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.request.LoginRequest;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserQualificationEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.service.EmployeeAdvanceService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.service.UserQualificationService;
import com.zieta.tms.serviceImpl.UserInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "Employee Advance API")
public class EmployeeAdvanceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeAdvanceController.class);

	@Autowired
	EmployeeAdvanceService employeeAdvanceService;

	@RequestMapping(value = "getAllEmployeeAdvance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeAdvanceDTO> getAllEmployeeAdvances() {
		List<EmployeeAdvanceDTO> allEmployeeAdvanceData = null;
		try {

			allEmployeeAdvanceData = employeeAdvanceService.getAllEmployeeadvance();
			LOGGER.info("Total number of EmployeeAdvance: " + allEmployeeAdvanceData.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting all employee advance", e);
		}
		return allEmployeeAdvanceData;
	}

	@GetMapping("/getAllEmployeeAdvanceByClientUser")
	@ApiOperation(value = "List employee advance based on the  clientId and userId", notes = "Table reference:"
			+ "employee_advance")
	public List<EmployeeAdvanceDTO> getAllEmployeeAdvanceByClientUser(
			@RequestParam(required = true) Long clientId, @RequestParam(required = true) Long userId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) {
		try {
			List<EmployeeAdvanceDTO> employeeAdvanceList = employeeAdvanceService.findByClientIdAndUserId(clientId, userId, startDate, endDate);
			return employeeAdvanceList;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	
	//FIND BY ID
	 @RequestMapping(value = "getEmployeeAdvanceById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	  
	 @ApiOperation(value = "provides employee advance data based on id" ,notes="Table reference: employee_advance")
	 public	EmployeeAdvanceDTO getEmployeeAdvanceDataById(@RequestParam(required = true) Long id) {
		 
		 EmployeeAdvanceDTO employeeAdvanceDetails = employeeAdvanceService.findByEmployeeAdvanceId(id);	  
		 return employeeAdvanceDetails; 
	  }
	 

	//SAVE EMPLOYEE ADVANCE DATA
	@ApiOperation(value = "creates entries in the employee_advance table", notes = "Table reference: employee_advance")
	@RequestMapping(value = "addEmployeeAdvance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addEmployeeAdvance(@Valid @RequestBody EmployeeAdvance employeeAdvance) {

		try {
			  employeeAdvanceService.addEmployeeAdvance(employeeAdvance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@ApiOperation(value = "Updates the EmployeeAdvance for the provided Id", notes = "Table reference: employee_advance")
	@RequestMapping(value = "editEmployeeAdvanceByIds", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editEmployeeAdvanceByIds(@Valid @RequestBody EmployeeAdvanceDTO employeeAdvanceDTO) throws Exception {
		employeeAdvanceService.editEmployeeAdvance(employeeAdvanceDTO);

	}
	
	@ApiOperation(value = "Deletes entries from employee_advance based on Id", notes = "Table reference: employee_advance")
	@RequestMapping(value = "deleteEmployeeadvanceById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEmployeeadvanceById(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String modifiedBy) throws Exception {
		employeeAdvanceService.deleteEmployeeAdvanceById(id, modifiedBy);
	}
	
	
	

}
