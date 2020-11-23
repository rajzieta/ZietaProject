package com.zieta.tms.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.lang.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.service.ExpenseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Expenses API")
public class ExpensesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpensesController.class);

	@Autowired
	ExpenseService expenseService;

	@ApiOperation(value = "List Expenses Info", notes = "Table reference:expense_info")
	@RequestMapping(value = "getAllExpenses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ExpenseInfoDTO> getAllExpenses() {
		List<ExpenseInfoDTO> expenseInfos = null;
		try {
			expenseInfos = expenseService.getAllExpenses();

		} catch (Exception e) {
			LOGGER.error("Error Occured in ExpenseInfoController#getAllExpenses", e);
		}
		return expenseInfos;
	}

	// filter based on client and userid

//	@Bean
	@GetMapping("/getAllExpensesHistoryByClientUser")
	@ApiOperation(value = "List expenses based on the  clientId and userId", notes = "Table reference:"
			+ "expense_info")
	public ResponseEntity<List<ExpenseInfoDTO>> getAllExpensesHistoryByClientUser(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long userId) {
		try {
			List<ExpenseInfoDTO> expensesList = expenseService.findByClientIdAndUserId(clientId, userId);
			return new ResponseEntity<List<ExpenseInfoDTO>>(expensesList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ExpenseInfoDTO>>(HttpStatus.NOT_FOUND);
		}
	}
	
	//get only Draft State expenses 
	
	@GetMapping("/getAllExpensesByClientUser")
	@ApiOperation(value = "List Draft expenses based on the  clientId and userId and StatusId", notes = "Table reference:"
			+ "expense_info")
	public ResponseEntity<List<ExpenseInfoDTO>> getAllExpensesByClientUser(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long userId) {
		try {
			List<ExpenseInfoDTO> expensesList = expenseService.findActiveExpensesByClientIdAndUserId(clientId, userId);
			return new ResponseEntity<List<ExpenseInfoDTO>>(expensesList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ExpenseInfoDTO>>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Lists ExpenseTypeMaster Information", notes = "Table reference:expense_type_master")
	@RequestMapping(value = "getAllExpenseMasters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ExpenseMasterDTO> getAllExpenseMasters() {
		List<ExpenseMasterDTO> expenseMasters = null;
		try {
			expenseMasters = expenseService.getAllExpenseMasters();

		} catch (Exception e) {
			LOGGER.error("Error Occured in ExpenseMasterController#getAllExpenseMasters", e);
		}
		return expenseMasters;
	}

	@ApiOperation(value = "Lists ExpenseEntries Information", notes = "Table reference:expense_entries")
	@RequestMapping(value = "getAllExpenseEntries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ExpenseEntriesDTO> getAllExpenseEntries() {
		List<ExpenseEntriesDTO> expenseEntries = null;
		try {
			expenseEntries = expenseService.getAllExpenseEntries();

		} catch (Exception e) {
			LOGGER.error("Error Occured in ExpenseEntriesController#getAllExpenseEntries", e);
		}
		return expenseEntries;
	}

	@GetMapping("/getAllExpenseEntriesByExpId")
	@ApiOperation(value = "List expenseEntries based on the expId", notes = "Table reference:" + "expense_entries")
	public ResponseEntity<List<ExpenseEntriesDTO>> getAllExpenseEntriesByExpId(
			@RequestParam(required = true) Long expId) {
		try {
			List<ExpenseEntriesDTO> expenseEntriesList = expenseService.findByExpId(expId);
			return new ResponseEntity<List<ExpenseEntriesDTO>>(expenseEntriesList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ExpenseEntriesDTO>>(HttpStatus.NOT_FOUND);
		}
	}

	// CUD Ops for expense Entries

	@RequestMapping(value = "addExpenseEntries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Adds list of rows in expenseEntries", notes = "Table reference:" + "expense_entries")
	public void addExpenseEntries(@Valid @RequestBody List<ExpenseEntries> expenseEntries) throws Exception {
		expenseService.addExpenseEntries(expenseEntries);
	}

	@ApiOperation(value = "Updates the ExpenseEntries for the provided Id", notes = "Table reference: expense_entries")
	@RequestMapping(value = "editExpenseEntriesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editExpenseEntriesById(@Valid @RequestBody List<ExpenseEntriesDTO> expenseEntriesDTO) throws Exception {
		expenseService.editExpenseEntriesById(expenseEntriesDTO);

	}

	@ApiOperation(value = "Deletes entries from expense_entries based on Id", notes = "Table reference: expense_entries")
	@RequestMapping(value = "deleteExpenseEntriesById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteExpenseEntriesById(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String modifiedBy) throws Exception {
		expenseService.deleteExpenseEntriesById(id, modifiedBy);
	}

	// CUD Ops for Expense Info

	@RequestMapping(value = "addExpenseInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Adds list of rows in expenseInfo", notes = "Table reference:" + "expense_info")
	public void addExpenseInfo(@Valid @RequestBody ExpenseInfo expenseInfo) throws Exception {
		expenseService.addExpenseInfo(expenseInfo);

	}

	@RequestMapping(value = "addExpenseInfoList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Adds list of rows in expenseInfo", notes = "Table reference:" + "expense_info")
	public ResponseEntity<List<ExpenseInfo>> addExpenseInfoList(@Valid @RequestBody List<ExpenseInfo> expenseInfo)
			throws Exception {
		List<ExpenseInfo> expenseInfoList = expenseService.addExpenseInfo(expenseInfo);

		return new ResponseEntity<List<ExpenseInfo>>(expenseInfoList, HttpStatus.OK);

	}

	@ApiOperation(value = "Updates the ExpenseInfo for the provided Id", notes = "Table reference: expense_info")
	@RequestMapping(value = "editExpenseInfoById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editExpenseInfoById(@Valid @RequestBody ExpenseInfoDTO expenseInfoDTO) throws Exception {
		expenseService.editExpenseInfoById(expenseInfoDTO);

	}

	@ApiOperation(value = "Updates the ExpenseInfo for the provided Id", notes = "Table reference: expense_info")
	@RequestMapping(value = "editExpenseInfoByIds", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editExpenseInfoByIds(@Valid @RequestBody List<ExpenseInfoDTO> expenseInfoDTO) throws Exception {
		expenseService.editExpenseInfoByIds(expenseInfoDTO);

	}
	
	@ApiOperation(value = "Deletes entries from expense_info based on Id", notes = "Table reference: expense_info")
	@RequestMapping(value = "deleteExpenseInfoById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteExpenseInfoById(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String modifiedBy) throws Exception {
		expenseService.deleteExpenseInfoById(id, modifiedBy);
	}
	
	@ApiOperation(value = "Saves the expense entries into expwf_request", notes = "Table reference: expwf_request")
	@RequestMapping(value = "submitExpenseSheet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> submitExpenseSheet(@Valid @RequestBody List<ExpenseInfo> expenseInfoList)
			throws Exception {
		boolean status = expenseService.submitExpenses(expenseInfoList);
		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}

}
