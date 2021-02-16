package com.zieta.tms.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.response.ExpenseTypeMasterResponse;

@Transactional
public interface ExpenseTypeMasterService {
	
	List<ExpenseTypeMasterResponse> findExpenseTypeByClientId(long clientId);
	
	List<ExpenseTypeMasterResponse> getAllExpenseTypes();
	
	void addExpenseType(ExpenseTypeMaster expenseTypeMaster);
	
	void deleteExpenseType(String expenseType);
	
	void editExpenseType(ExpenseTypeMaster expenseTypeMaster);
	
	void deleteByExpenseTypeId(long expesneId,String modifiedBy);
	
	
	
	

}
