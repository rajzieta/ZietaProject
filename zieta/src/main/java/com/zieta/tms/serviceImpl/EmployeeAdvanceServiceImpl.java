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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.DateRange;
import com.zieta.tms.dto.EmployeeAdvanceDTO;
import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.CountryMaster;
import com.zieta.tms.model.CurrencyMaster;
import com.zieta.tms.model.EmployeeAdvance;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.model.ExpenseWorkflowRequest;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.WorkflowRequest;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.repository.EmployeeAdvanceRepository;
import com.zieta.tms.repository.ExpTemplateStepsRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.repository.ExpenseWorkflowRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.service.EmployeeAdvanceService;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin.Engine.Source.Empty;

@Service
@Transactional
@Slf4j
public class EmployeeAdvanceServiceImpl implements EmployeeAdvanceService {
	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	EmployeeAdvanceRepository employeeAdvanceRepository;
	
	
	
	@Override
	public void addEmployeeAdvance(@Valid EmployeeAdvance employeeAdvance) throws Exception {
		employeeAdvanceRepository.save(employeeAdvance);
	}

	@Override
	public List<EmployeeAdvanceDTO> getAllEmployeeadvance() {
		short notDeleted = 0;
		List<EmployeeAdvance> employeeAdvanceList = employeeAdvanceRepository.findByIsDelete(notDeleted);
		List<EmployeeAdvanceDTO> employeeAdvanceDTOs = new ArrayList<EmployeeAdvanceDTO>();
		EmployeeAdvanceDTO employeeAdvanceDTO = null;
		for (EmployeeAdvance employeeAdvance : employeeAdvanceList) {
			employeeAdvanceDTO = modelMapper.map(employeeAdvance, EmployeeAdvanceDTO.class);


			employeeAdvanceDTOs.add(employeeAdvanceDTO);
		}
		return employeeAdvanceDTOs;		
	}

	@Override
	public List<EmployeeAdvanceDTO> getAllEmployeeAdvance() {
		short notDeleted = 0;
		List<EmployeeAdvance> employeeAdvanceList = employeeAdvanceRepository.findByIsDelete(notDeleted);
		List<EmployeeAdvanceDTO> employeeAdvanceDTOs = new ArrayList<EmployeeAdvanceDTO>();
		EmployeeAdvanceDTO employeeAdvanceDTO = null;
		for (EmployeeAdvance employeeAdvance : employeeAdvanceList) {
			employeeAdvanceDTO = modelMapper.map(employeeAdvance, EmployeeAdvanceDTO.class);
			employeeAdvanceDTOs.add(employeeAdvanceDTO);
		}
		return employeeAdvanceDTOs;		

	}
	
	/*
	 * GET EMPLOYEE ADVANCE DY ID
	 * 
	 */
	@Override
	public EmployeeAdvanceDTO findByEmployeeAdvanceId(long id) {
		EmployeeAdvanceDTO employeeAdvanceDTO = null;
		EmployeeAdvance employeeAdvance = employeeAdvanceRepository.findById(id).get();
		if(employeeAdvance !=null) {
			employeeAdvanceDTO =  modelMapper.map(employeeAdvance, EmployeeAdvanceDTO.class);
			
		}
		return employeeAdvanceDTO;
	}
	
	
	public void editEmployeeAdvance(@Valid EmployeeAdvanceDTO employeeAdvanceDTO) throws Exception {

		Optional<EmployeeAdvance> employeeAdvanceData = employeeAdvanceRepository.findById(employeeAdvanceDTO.getId());
		if (employeeAdvanceData.isPresent()) {
			EmployeeAdvance employeeAdvance = modelMapper.map(employeeAdvanceDTO, EmployeeAdvance.class);
			employeeAdvanceRepository.save(employeeAdvance);

		} else {
			throw new Exception("employee advance data not found with the provided ID : " + employeeAdvanceDTO.getId());
		}
	}
	
	public void deleteEmployeeAdvanceById(Long id, String modifiedBy) throws Exception {
		
		Optional<EmployeeAdvance> employeeAdvance = employeeAdvanceRepository.findById(id);
		if (employeeAdvance.isPresent()) {
			EmployeeAdvance employeeAdvanceEntitiy = employeeAdvance.get();
			short delete = 1;
			employeeAdvanceEntitiy.setIsDelete(delete);
			employeeAdvanceEntitiy.setModifiedBy(modifiedBy);
			employeeAdvanceRepository.save(employeeAdvanceEntitiy);

		} else {
			log.info("No Employee Advance found with the provided ID{} in the DB", id);
			throw new Exception("No EmployeeAdvance found with the provided ID in the DB :" + id);
		}
	}

	@Override
	public List<EmployeeAdvanceDTO> findByClientIdAndUserId(Long clientId, Long userId, String startDate, String endDate) {		
		short notDeleted = 0;		
		List<EmployeeAdvanceDTO> employeeAdvanceList = new ArrayList<>();
		List<EmployeeAdvance> employeeAdvances = employeeAdvanceRepository.findByClientIdAndUserIdAndIsDeleteAndStartDateBetween(clientId, userId,
				notDeleted, startDate, endDate);
		EmployeeAdvanceDTO employeeAdvanceDTO = null;
		
		for (EmployeeAdvance employeeAdvance : employeeAdvances) {			
			employeeAdvanceDTO = modelMapper.map(employeeAdvance, EmployeeAdvanceDTO.class);
			employeeAdvanceList.add(employeeAdvanceDTO);			
		}
	
		return employeeAdvanceList;
	}
	
	
	
	
	
	
	
}
