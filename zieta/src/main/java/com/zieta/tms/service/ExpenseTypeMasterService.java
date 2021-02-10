package com.zieta.tms.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.model.ExpenseTypeMaster;

@Transactional
public interface ExpenseTypeMasterService {
	
	List<ExpenseTypeMaster> findExpenseTypeByClientId(long clientId);
	
	List<ExpenseTypeMaster> getAllExpenseTypes();
	
	void addExpenseType(ExpenseTypeMaster expenseTypeMaster);
	
	void deleteExpenseType(String expenseType);
	
	void editExpenseType(ExpenseTypeMaster expenseTypeMaster);
	
	
	
	

}
