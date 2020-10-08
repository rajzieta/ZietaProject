package com.zieta.tms.service;

import java.util.List;


import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.dto.ExpenseEntriesDTO;

public interface ExpenseService {

	public List<ExpenseInfoDTO> getAllExpenses();

	public List<ExpenseMasterDTO> getAllExpenseMasters();

	public List<ExpenseEntriesDTO> getAllExpenseEntries();

	
	
	
}
