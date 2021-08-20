package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.Decimal;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.DateRange;
import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.model.CountryMaster;
import com.zieta.tms.model.CurrencyMaster;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.model.ExpenseWorkflowRequest;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.repository.ExpTemplateStepsRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTemplateRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.repository.ExpenseWorkflowRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ExpenseTemplateServiceImpl implements ExpenseTemplateService {

	
	@Autowired
	ExpTemplateStepsRepository expTemplateStepsRepository;
	
	@Autowired
    ExpenseTemplateRepository expenseTemplateRepository;

	@Autowired
	ModelMapper modelMapper;	 

	@Override
	public List<ExpenseTemplateDTO> getAllExpenseTemplate() {
		/*short notDeleted = 0;
		List<ExpenseTemplate> expenseTemplateList = expenseTemplateRepository.findAll();
		List<ExpenseTemplateDTO> expenseTemplateDTOs = new ArrayList<ExpenseTemplateDTO>();
		ExpenseTemplateDTO expenseTemplateDTO = null;
		for (ExpenseTemplate expenseTemplate : expenseTemplateList) {
			expenseTemplateDTO = modelMapper.map(expenseTemplate, ExpenseTemplateDTO.class);
			expenseTemplateDTOs.add(expenseTemplateDTO);
		}*/
		return null;
	}

	@Override
	public List<ExpenseTemplateDTO> getAllActiveExpenseTemplate(Long clientId, Short notDeleted) {
		
		List<ExpenseTemplate> expenseTemplateList = expenseTemplateRepository.findByClientIdAndIsDelete(clientId,notDeleted);
		List<ExpenseTemplateDTO> expenseTemplateDTOs = new ArrayList<ExpenseTemplateDTO>();
		
		ExpenseTemplateDTO expenseTemplateDTO = null;
		for (ExpenseTemplate expenseTemplate : expenseTemplateList) {
			expenseTemplateDTO = modelMapper.map(expenseTemplate, ExpenseTemplateDTO.class);
			expenseTemplateDTOs.add(expenseTemplateDTO);
		}
		return expenseTemplateDTOs;
	}
	
	@Override
	public List<ExpenseTemplateDTO> getAllExpenseTemplate(Short notDeleted) {
		
		List<ExpenseTemplate> expenseTemplateList = expenseTemplateRepository.findByIsDelete(notDeleted);
		List<ExpenseTemplateDTO> expenseTemplateDTOs = new ArrayList<ExpenseTemplateDTO>();
		
		ExpenseTemplateDTO expenseTemplateDTO = null;
		for (ExpenseTemplate expenseTemplate : expenseTemplateList) {
			expenseTemplateDTO = modelMapper.map(expenseTemplate, ExpenseTemplateDTO.class);
			expenseTemplateDTOs.add(expenseTemplateDTO);
		}
		return expenseTemplateDTOs;
	}
	

	@Override
	public ExpenseTemplate addExpenseTemplate(ExpenseTemplate expTemplate) throws Exception {
		ExpenseTemplate expTmplt = expenseTemplateRepository.save(expTemplate);
		return expTmplt;
	}
	

	@Override
	public void editExpenseTemplateById(ExpenseTemplateEditRequest expTemplate) throws Exception {		
			//ExpenseTemplate expTmplt = expenseTemplateRepository.save(expTemplate);			
			Optional<ExpenseTemplate> expenseTemplateEntity = expenseTemplateRepository.findById(expTemplate.getId());
			if(expenseTemplateEntity.isPresent()) {
				
				
				ExpenseTemplate expTmplt = expenseTemplateEntity.get();
				
				
				log.info("post data ===>"+expTemplate);
				log.info("map data ==>"+expTmplt);
				
				 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
				 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
				 modelMapper.map(expTemplate,expTmplt );
				 expenseTemplateRepository.save(expTmplt);
				
			}else {
				throw new Exception("User not found with the provided ID : "+expTemplate.getId());
			}
			
		//return null;
	}

	@Override
	public List<ExpTemplateSteps> addExpenseTemplateSteps(List<ExpTemplateSteps> expTemplateStepList) throws Exception {
		List<ExpTemplateSteps> exptmpltlst = expTemplateStepsRepository.saveAll(expTemplateStepList);
		return exptmpltlst;
	}

	@Override
	public List<ExpTemplateStepsDTO> getAllActiveExpenseTemplateSteps(Long expTemplateId, Short notDeleted) {
		
		List<ExpTemplateSteps> expenseTemplateStepsList = expTemplateStepsRepository.findByTemplateIdAndIsDelete(expTemplateId,notDeleted);
		List<ExpTemplateStepsDTO> expenseTemplateStepDTOs = new ArrayList<ExpTemplateStepsDTO>();
		
		ExpTemplateStepsDTO expenseTemplateStepDTO = null;
		for (ExpTemplateSteps expTemplateSteps : expenseTemplateStepsList) {
			expenseTemplateStepDTO = modelMapper.map(expTemplateSteps, ExpTemplateStepsDTO.class);
			expenseTemplateStepDTOs.add(expenseTemplateStepDTO);
		}
		return expenseTemplateStepDTOs;
		
	}
	
	public void deletetTemplateById(Long id, String modifiedBy) throws Exception {
		Optional<ExpenseTemplate> expenseTemplate = expenseTemplateRepository.findById(id);
		if (expenseTemplate.isPresent()) {
			ExpenseTemplate expenseTemplateEntitiy = expenseTemplate.get();
			short delete = 1;
			expenseTemplateEntitiy.setIsDelete(delete);
			expenseTemplateEntitiy.setModifiedBy(modifiedBy);
			expenseTemplateRepository.save(expenseTemplateEntitiy);

		}else {
			log.info("No Expense Template found with the provided ID{} in the DB",id);
			throw new Exception("No ExpenseTemplate found with the provided ID in the DB :"+id);
		}
		
		
	}

	public void deletetTemplateStepsById(Long id, String modifiedBy) throws Exception {
		Optional<ExpTemplateSteps> expTemplateSteps = expTemplateStepsRepository.findById(id);
		if (expTemplateSteps.isPresent()) {
			ExpTemplateSteps expenseTemplateStepEntitiy = expTemplateSteps.get();
			short delete = 1;
			expenseTemplateStepEntitiy.setIsDelete(delete);
			expenseTemplateStepEntitiy.setModifiedBy(modifiedBy);
			expTemplateStepsRepository.save(expenseTemplateStepEntitiy);

		}else {
			log.info("No Expense Template Steps found with the provided ID{} in the DB",id);
			throw new Exception("No ExpenseTemplateSteps found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	
	
	
}
