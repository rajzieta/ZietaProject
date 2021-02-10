package com.zieta.tms.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.service.ExpenseTypeMasterService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags= "Expense Type Master  API")
public class ExpenseTypeMasterController {

	@Autowired
	ExpenseTypeMasterService expenseTypeMasterService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseTypeMasterController.class);

	@RequestMapping(value = "getAllExpenseTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ExpenseTypeMaster> getAllExpenseTypes() {
		List<ExpenseTypeMaster> msgList = null;
		try {
			msgList = expenseTypeMasterService.getAllExpenseTypes();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ExpenseTypeMasterController#ExpenseTypeMasterController",e);

		}
		return msgList;
	}

	
	@RequestMapping(value = "addExpenseType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addExpenseType(@Valid @RequestBody ExpenseTypeMaster expenseTypeMaster) {
		expenseTypeMasterService.addExpenseType(expenseTypeMaster);

	}

	@RequestMapping(value = "editExpenseType", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editExpenseType(@Valid @RequestBody ExpenseTypeMaster expenseTypeMaster) {
		expenseTypeMasterService.editExpenseType(expenseTypeMaster);

	}
	
	@RequestMapping(value = "deleteExpenseType", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteExpenseType(@RequestParam(required = true) String expenseType) {
		expenseTypeMasterService.deleteExpenseType(expenseType);

	}
	

}
