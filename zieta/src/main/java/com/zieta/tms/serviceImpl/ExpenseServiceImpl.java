package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.zieta.tms.model.CountryMaster;
import com.zieta.tms.model.CurrencyMaster;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.service.ExpenseService;


import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseTypeMasterRepository expenseTypeMasterRepository;
	
	@Autowired
	CountryMasterRepository countryMasterRepository;
	
	@Autowired
	CurrencyMasterRepository currencyMasterRepository;
	
	@Autowired
	ExpenseInfoRepository expenseInfoRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
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
	public List<ExpenseInfoDTO> findByClientIdAndUserId(Long clientId, Long userId) {
		
		 short notDeleted=0;
			List<ExpenseInfoDTO> expenseInfoList = new ArrayList<>();
			List<ExpenseInfo> expenseInfos = expenseInfoRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
			ExpenseInfoDTO expenseInfoDTO = null;
			for (ExpenseInfo expenses : expenseInfos) {
				expenseInfoDTO = modelMapper.map(expenses, ExpenseInfoDTO.class);
				ProjectInfo projectInfo  = projectInfoRepository.findById(expenses.getProjectId()).get();
				expenseInfoDTO.setProjectCode(projectInfo.getProjectCode());
				//ProjectInfo projectInfo  = projectInfoRepository.findById(expenses.getProjectId()).get();
				expenseInfoDTO.setProjectDesc(projectInfo.getProjectName());
				expenseInfoList.add(expenseInfoDTO);
			}
			return expenseInfoList;
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
	@Override
	public List<ExpenseEntriesDTO> findByExpId(Long expId) {
		
		 short notDeleted=0;
			List<ExpenseEntriesDTO> expenseEntriesDTOs = new ArrayList<>();
			List<ExpenseEntries> expenseEntries = expenseEntriesRepository.findByExpIdAndIsDelete(expId, notDeleted);
			ExpenseEntriesDTO expenseEntriDTO = null;
			for (ExpenseEntries expenseEntry : expenseEntries) {
				expenseEntriDTO = modelMapper.map(expenseEntry, ExpenseEntriesDTO.class);
		
				expenseEntriDTO.setExpenseType(StringUtils.EMPTY);
				if(null != expenseEntry.getExpType()) {
					Optional <ExpenseTypeMaster> exps = expenseTypeMasterRepository.findById(expenseEntry.getExpType());
					if(exps.isPresent()) {
						expenseEntriDTO.setExpenseType(exps.get().getExpenseType());
						
					}
				}
				
				
			
				expenseEntriDTO.setExpCountryCode(StringUtils.EMPTY);
				if(null != expenseEntry.getExpCountry()) {
					Optional <CountryMaster> exps = countryMasterRepository.findById(expenseEntry.getExpCountry());
					if(exps.isPresent()) {
						expenseEntriDTO.setExpCountryCode(exps.get().getCountryCode());
						
					}
				}
				
				expenseEntriDTO.setExpCountryName(StringUtils.EMPTY);
				if(null != expenseEntry.getExpCountry()) {
					Optional <CountryMaster> exps = countryMasterRepository.findById(expenseEntry.getExpCountry());
					if(exps.isPresent()) {
						expenseEntriDTO.setExpCountryName(exps.get().getCountryName());
						
					}
				}
				
				
				expenseEntriDTO.setExpCurrencyType(StringUtils.EMPTY);
				if(null != expenseEntry.getExpCurrency()) {
					Optional <CurrencyMaster> exps = currencyMasterRepository.findById(expenseEntry.getExpCurrency());
					if(exps.isPresent()) {
						expenseEntriDTO.setExpCurrencyType(exps.get().getCurrencyName());
						
					}
				}
				
				expenseEntriDTO.setExpCurrencyCode(StringUtils.EMPTY);
				if(null != expenseEntry.getExpCurrency()) {
					Optional <CurrencyMaster> exps = currencyMasterRepository.findById(expenseEntry.getExpCurrency());
					if(exps.isPresent()) {
						expenseEntriDTO.setExpCurrencyCode(exps.get().getCurrencyCode());
						
					}
				}
				
				
				expenseEntriesDTOs.add(expenseEntriDTO);
			}
			return expenseEntriesDTOs;
		
		
	}
	
	
	public void addExpenseEntries(@Valid List<ExpenseEntries> expenseEntries) throws Exception {
			expenseEntriesRepository.saveAll(expenseEntries);

		
	}
	
	
	
}
