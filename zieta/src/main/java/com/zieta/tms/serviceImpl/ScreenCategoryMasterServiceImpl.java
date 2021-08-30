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
import com.zieta.tms.dto.ScreenCategoryMasterDTO;
import com.zieta.tms.dto.UserDetailsDTO;
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
import com.zieta.tms.model.ScreenCategoryMaster;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.UserDetails;
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
import com.zieta.tms.repository.ScreenCategoryMasterRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.ScreenCategoryMasterService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ScreenCategoryMasterServiceImpl implements ScreenCategoryMasterService {
	
	@Autowired
	ScreenCategoryMasterRepository screenCategoryMasterRepository;

	@Autowired
	ModelMapper modelMapper;
	

	@Override
	public List<ScreenCategoryMasterDTO> getAllScreenCategoryMaster() {		
		List<ScreenCategoryMaster> screenCategoryMasterList = screenCategoryMasterRepository.findAllScreenCategoryMaster();		
		List<ScreenCategoryMasterDTO> screenCategoryMasterDTOs = new ArrayList<ScreenCategoryMasterDTO>();
		
		ScreenCategoryMasterDTO screenCategoryMasterDTO = null;
		for (ScreenCategoryMaster screenCategoryMaster : screenCategoryMasterList) {
			screenCategoryMasterDTO = modelMapper.map(screenCategoryMaster, ScreenCategoryMasterDTO.class);
			screenCategoryMasterDTOs.add(screenCategoryMasterDTO);
		}
		return screenCategoryMasterDTOs;
	}	
	
	@Override
	public ScreenCategoryMasterDTO findScreenCategoryMasterById(long id) {
		ScreenCategoryMasterDTO screenCategoryMasterDTO = null;
		ScreenCategoryMaster screenCategoryMaster = screenCategoryMasterRepository.findScreenCategoryMasterById(id);
		if(screenCategoryMaster !=null) {
			screenCategoryMasterDTO =  modelMapper.map(screenCategoryMaster, ScreenCategoryMasterDTO.class);
		}
		return screenCategoryMasterDTO;
		
	}
	
	
	
}
