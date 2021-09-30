package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

import com.zieta.tms.dto.UserConfigDTO;
import com.zieta.tms.model.UserConfig;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.dto.VendorAdvanceDTO;
import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.model.VendorAdvanceLineItems;
import com.zieta.tms.repository.UserConfigRepository;
import com.zieta.tms.repository.VendorAdvanceLineItemsRepository;
import com.zieta.tms.repository.VendorAdvanceRepository;
import com.zieta.tms.request.UserConfigEditRequest;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.response.VendorAdvanceResponse;
import com.zieta.tms.service.UserConfigService;
import com.zieta.tms.service.VendorAdvanceService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserConfigServiceImpl implements UserConfigService {

	@Autowired
	UserConfigRepository userConfigRepository;
	
	

	@Autowired
	ModelMapper modelMapper;	 

	/*
	 * GET ALL ACTIVE VENDOR ADVANCE
	 */
	@Override
	public List<UserConfigDTO> getAllActiveUserConfig(Short notDeleted) {
		
		List<UserConfig> userConfigList = userConfigRepository.findByIsDelete(notDeleted);
		List<UserConfigDTO> userConfigDTOs = new ArrayList<UserConfigDTO>();		
		UserConfigDTO userConfigDTO = null;
		for (UserConfig userConfig : userConfigList) {
			userConfigDTO = modelMapper.map(userConfig, UserConfigDTO.class);
			userConfigDTOs.add(userConfigDTO);
		}
		return userConfigDTOs;
	}
	
		
	/*
	 * TO SAVE VENDOR INVOICE DATA 
	 */
	
	/*@Override
	public VendorAdvance addVendorAdvance(VendorAdvance vendorAdvance) throws Exception {		
		VendorAdvance vndrAdvance = vendorAdvanceRepository.save(vendorAdvance);
		return vndrAdvance;
	}*/
	
	@Override
	public void addUserConfig(UserConfig userConfig) throws Exception {		
		 userConfigRepository.save(userConfig);
		
	}
	
	public void deleteByVendorAdvanceId(Long id, String modifiedBy) throws Exception {
		
		Optional<UserConfig> UserConfig = userConfigRepository.findById(id);
		if (UserConfig.isPresent()) {
			UserConfig userConfigEntitiy = UserConfig.get();
			short delete = 1;
			userConfigEntitiy.setIsDelete(delete);
			userConfigEntitiy.setModifiedBy(modifiedBy);
			userConfigRepository.save(userConfigEntitiy);

		}else {
			
			log.info("No user config found with the provided ID{} in the DB",id);
			throw new Exception("No user config found with the provided ID in the DB :"+id);
		}		
	}

	/*
	 * FIND USER CONFIG BY ID 
	 *  
	 */
	@Override
	public UserConfigDTO findUserConfigById(long id, short isDelete) throws Exception {
		UserConfigDTO userConfigDTO = null;
		UserConfig userConfig =userConfigRepository.findUserConfigByIdAndIsDelete(id,isDelete);
		if(userConfig !=null) {
			userConfigDTO =  modelMapper.map(userConfig, UserConfigDTO.class);
		}		
		return userConfigDTO;
	}


	@Override
	public void deleteByUserConfigId(Long id, String modifiedBy) throws Exception {
		Optional<UserConfig> userConfig = userConfigRepository.findById(id);
		if (userConfig.isPresent()) {
			UserConfig userConfigEntitiy = userConfig.get();
			short delete = 1;
			userConfigEntitiy.setIsDelete(delete);
			userConfigEntitiy.setModifiedBy(modifiedBy);
			userConfigRepository.save(userConfigEntitiy);

		}else {
			log.info("No User config found with the provided ID{} in the DB",id);
			throw new Exception("No UserConfig found with the provided ID in the DB :"+id);
		}
		
	}

	

	@Override
	public List<UserConfigDTO> getAllActiveUserConfigeByUserId(long userId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseMessage editUserConfigByUserId(@Valid UserConfigEditRequest UserConfigEditRequest) throws Exception {
		try {
			
			ResponseMessage resMsg = new ResponseMessage();
			Optional<UserConfig> userConfigDetailsEntity = userConfigRepository.findByUserId(UserConfigEditRequest.getUserId());
			if(userConfigDetailsEntity.isPresent()) {
				UserConfig userConfigDetails = userConfigDetailsEntity.get();
				 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
				 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
				 modelMapper.map(UserConfigEditRequest,userConfigDetails );
				 userConfigRepository.save(userConfigDetails);			
				resMsg.setMessage("User config Details Successfully Updated...");
				resMsg.setStatus(200);
				return resMsg;
				
			}else {
				throw new Exception("User Config Details not found with the provided User ID : "+UserConfigEditRequest.getUserId());
			}
		}catch(Exception e) {
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setMessage("Failed to update User Config Details!!!");
			resMsg.setStatus(403);
			return resMsg;		
		}
	}


	/*@Override
	public List<VendorAdvanceResponse> findVendorAdvanceByUserId(long userId) {

			boolean isDatesValid = TSMUtil.validateDates(startActiondate,endActionDate);
		
		//defaulting to the current week date range, when there is no date range mentioned from front end.
		if(!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en","IN"));
			startActiondate =currentWeek.getFirstDay();
			endActionDate = currentWeek.getLastDay();
		}else {
			startActiondate = TSMUtil.getFormattedDate(startActiondate);
			endActionDate =  TSMUtil.getFormattedDate(endActionDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endActionDate);
//			c.add(Calendar.DATE, 1);
			endActionDate = c.getTime();
		}		//VendorInvoice vendorInvoiceData = vendorInvoiceRepository.findVendorInvoiceByUserIdAndBetweenInvoiceDate(userId,startActiondate,endActionDate);
		
		return null;
	}*/



	
	
	
}
