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
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Expense Template API")
public class ExpenseTemplateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseTemplateController.class);

	@Autowired
	ExpenseTemplateService expenseTemplateService;
	

	@ApiOperation(value = "List Expense Template Info", notes = "Table reference:expense_Template")
	@RequestMapping(value = "getAllActiveExpenseTemplate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ExpenseTemplateDTO> getAllActiveExpenseTemplates(@RequestParam(required = true) Long clientId) {
		
		Short notDeleted =0;		
		List<ExpenseTemplateDTO> expenseTemplates = null;
		try {
				expenseTemplates = expenseTemplateService.getAllActiveExpenseTemplate(clientId, notDeleted);

		} catch (Exception e) {
			LOGGER.error("Error Occured in ExpenseInfoController#getAllExpenses", e);
		}
		return expenseTemplates;
	}
	
	@ApiOperation(value = "List Expense Template Step", notes = "Table reference:expense_Template_steps")
	@RequestMapping(value = "getAllActiveExpenseTemplateStepByTemplateId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ExpTemplateStepsDTO> getAllActiveExpenseTemplateStepByTemplateId(@RequestParam(required = true) Long expTemplateId) {
		
		Short notDeleted =0;		
		List<ExpTemplateStepsDTO> expenseTemplateSteps = null;
		try {
			expenseTemplateSteps = expenseTemplateService.getAllActiveExpenseTemplateSteps(expTemplateId,notDeleted);

		} catch (Exception e) {
			LOGGER.error("Error Occured in ExpenseInfoController#getAllExpensetesmplatesteps", e);
		}
		return expenseTemplateSteps;
	}

	@RequestMapping(value = "addExpenseTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Adds list of rows in expenseInfo", notes = "Table reference:" + "expense_template")
	public ExpenseTemplate addExpenseTemplate(@Valid @RequestBody ExpenseTemplate expenseTemplate) throws Exception {
		return expenseTemplateService.addExpenseTemplate(expenseTemplate);

	}

	@RequestMapping(value = "addExpenseTemplateStepList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Adds list of rows in expenseInfo", notes = "Table reference:" + "expense_template_steps")
	public ResponseEntity<List<ExpTemplateSteps>> addExpenseTemplateStepList(@Valid @RequestBody List<ExpTemplateSteps> expenseTemplateSteps)
			throws Exception {
		List<ExpTemplateSteps> expenseTemplateStepList = expenseTemplateService.addExpenseTemplateSteps(expenseTemplateSteps);

		return new ResponseEntity<List<ExpTemplateSteps>>(expenseTemplateStepList, HttpStatus.OK);

	}

	/*@ApiOperation(value = "Updates the ExpenseTemplate for the provided Id", notes = "Table reference: expense_template")
	@RequestMapping(value = "editExpenseTemplateById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editExpenseTemplateById(@Valid @RequestBody List<ExpenseTemplateEditRequest> expenseTemplateEditRequestList) throws Exception {
		expenseTemplateService.editExpenseTemplateStepById(expenseTemplateEditRequestList);

	}*/

	
}
