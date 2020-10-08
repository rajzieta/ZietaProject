package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.service.ExpenseService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseTypeMasterRepository expenseTypeMasterRepository;
	
	@Autowired
	ExpenseInfoRepository expenseInfoRepository;
	
	@Autowired
	ExpenseEntriesRepository expenseEntriesRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ExpenseInfoDTO> getAllExpenses() {
		short notDeleted = 0;
		List<ExpenseInfo> expenseInfos= expenseInfoRepository.findByIsDelete(notDeleted);
		List<ExpenseInfoDTO> expenseInfoDTOs = new ArrayList<ExpenseInfoDTO>();
		ExpenseInfoDTO expenseInfoDTO = null;
		for (ExpenseInfo expenseInfo : expenseInfos) {
			expenseInfoDTO = modelMapper.map(expenseInfo, ExpenseInfoDTO.class);
			expenseInfoDTOs.add(expenseInfoDTO);
		}
		return expenseInfoDTOs;
}
	
	
	
	@Override
	public List<ExpenseMasterDTO> getAllExpenseMasters() {
		short notDeleted = 0;
		List<ExpenseTypeMaster> expenseTypeMasters= expenseTypeMasterRepository.findByIsDelete(notDeleted);
		List<ExpenseMasterDTO> expenseMasterDTOs = new ArrayList<ExpenseMasterDTO>();
		ExpenseMasterDTO expenseMasterDTO = null;
		for (ExpenseTypeMaster expenseTypeMaster : expenseTypeMasters) {
			expenseMasterDTO = modelMapper.map(expenseTypeMaster, ExpenseMasterDTO.class);
			expenseMasterDTOs.add(expenseMasterDTO);
		}
		return expenseMasterDTOs;
}
	
	
	@Override
	public List<ExpenseEntriesDTO> getAllExpenseEntries() {
		short notDeleted = 0;
		List<ExpenseEntries> expenseEntries= expenseEntriesRepository.findByIsDelete(notDeleted);
		List<ExpenseEntriesDTO> expenseEntriesDTOs = new ArrayList<ExpenseEntriesDTO>();
		ExpenseEntriesDTO expenseEntriesDTO = null;
		for (ExpenseEntries expenseEntry : expenseEntries) {
			expenseEntriesDTO = modelMapper.map(expenseEntry, ExpenseEntriesDTO.class);
			expenseEntriesDTOs.add(expenseEntriesDTO);
		}
		return expenseEntriesDTOs;
}
	
	
}
