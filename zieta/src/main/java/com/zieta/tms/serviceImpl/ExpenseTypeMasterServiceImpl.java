package com.zieta.tms.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.service.ExpenseTypeMasterService;

@Service
public class ExpenseTypeMasterServiceImpl implements ExpenseTypeMasterService {
	
	@Autowired
	ExpenseTypeMasterRepository expenseTypeMasterRepository;
	
	@Autowired
	ModelMapper modelMapper; 
	
	@Override
	public List<ExpenseTypeMaster> findExpenseTypeByClientId(long clientId) {
		return expenseTypeMasterRepository.findByClientId(clientId);
	}

	@Override
	public List<ExpenseTypeMaster> getAllExpenseTypes() {
		return expenseTypeMasterRepository.findAll();
	}

	@Override
	public void addExpenseType(ExpenseTypeMaster expenseTypeMaster) {
		
		expenseTypeMasterRepository.save(expenseTypeMaster);
		
	}

	@Override
	public void deleteExpenseType(String expenseType) {
		expenseTypeMasterRepository.deleteByExpenseType(expenseType);
	}

	@Override
	public void editExpenseType(ExpenseTypeMaster expenseTypeMaster) {
		expenseTypeMasterRepository.save(expenseTypeMaster);
		
	}

	
}
