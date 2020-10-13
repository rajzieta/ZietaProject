package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.dto.ExpenseEntriesDTO;

public interface ExpenseService {

	public List<ExpenseInfoDTO> getAllExpenses();

	public List<ExpenseMasterDTO> getAllExpenseMasters();

	public List<ExpenseEntriesDTO> getAllExpenseEntries();

	public List<ExpenseInfoDTO> findByClientIdAndUserId(Long clientId, Long userId);

	public List<ExpenseEntriesDTO> findByExpId(Long expId);

	public void addExpenseEntries(@Valid List<ExpenseEntries> expenseEntries) throws Exception;

	
	
	
}
