package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.EmployeeAdvanceDTO;
import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.model.EmployeeAdvance;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTypeMaster;

public interface EmployeeAdvanceService {

	public List<EmployeeAdvanceDTO> getAllEmployeeadvance();
	
	public void addEmployeeAdvance(@Valid EmployeeAdvance employeeAdvance) throws Exception;

	public List<EmployeeAdvanceDTO> getAllEmployeeAdvance();
	
	public EmployeeAdvanceDTO findByEmployeeAdvanceId(long id);
	
	public void editEmployeeAdvance(@Valid EmployeeAdvanceDTO employeeAdvanceDTO) throws Exception;
	
	public void deleteEmployeeAdvanceById(Long id, String modifiedBy) throws Exception;
	
	public List<EmployeeAdvanceDTO> findByClientIdAndUserId(Long clientId, Long userId, String startDate, String endDate);

	/*public List<ExpenseEntriesDTO> getAllExpenseEntries();

	public List<ExpenseInfoDTO> findByClientIdAndUserId(Long clientId, Long userId, String startDate, String endDate);

	public List<ExpenseEntriesDTO> findByExpId(Long expId);

	public void addExpenseEntries(@Valid List<ExpenseEntries> expenseEntries) throws Exception;

	public void addExpenseInfo(@Valid ExpenseInfo expenseInfo) throws Exception;
	
	public List<ExpenseInfo> addExpenseInfo(List<ExpenseInfo> expenseInfo) throws Exception;

	public void editExpenseInfoById(@Valid ExpenseInfoDTO expenseInfoDTO) throws Exception;
	
	

	public void editExpenseInfoByIds(@Valid List<ExpenseInfoDTO> expenseInfoDTO) throws Exception;

	
	public void editExpenseEntriesById(@Valid List<ExpenseEntriesDTO> expenseEntriesDTO) throws Exception;

	public void deleteExpenseInfoById(Long id, String modifiedBy) throws Exception;

	public void deleteExpenseEntriesById(Long id, String modifiedBy) throws Exception;
	
	public boolean submitExpenses(List<ExpenseInfo> expenseInfo) throws Exception;

	public List<ExpenseInfoDTO> findActiveExpensesByClientIdAndUserId(Long clientId, Long userId);

	public List<ExpenseTypeMaster> getAllExpenseMastersByClient(Long clientId);
	
	public boolean updateFileDetails(String key) throws Exception;*/
	
	
}
