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

import com.zieta.tms.dto.VendorAdvanceDTO;
import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.model.VendorAdvanceLineItems;
import com.zieta.tms.repository.VendorAdvanceLineItemsRepository;
import com.zieta.tms.repository.VendorAdvanceRepository;
import com.zieta.tms.response.VendorAdvanceResponse;
import com.zieta.tms.service.VendorAdvanceService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class VendorAdvanceServiceImpl implements VendorAdvanceService {

	@Autowired
	VendorAdvanceRepository vendorAdvanceRepository;
	
	@Autowired
	VendorAdvanceLineItemsRepository vendorAdvanceLineItemsRepository;

	@Autowired
	ModelMapper modelMapper;	 

	/*
	 * GET ALL ACTIVE VENDOR ADVANCE
	 */
	@Override
	public List<VendorAdvanceDTO> getAllActiveVendorAdvance(Long clientId, Short notDeleted) {
		
		List<VendorAdvance> vendorAdvanceList = vendorAdvanceRepository.findByClientIdAndIsDelete(clientId,notDeleted);
		List<VendorAdvanceDTO> vendorAdvanceDTOs = new ArrayList<VendorAdvanceDTO>();		
		VendorAdvanceDTO vendorAdvacneDTO = null;
		for (VendorAdvance vendorAdvance : vendorAdvanceList) {
			vendorAdvacneDTO = modelMapper.map(vendorAdvance, VendorAdvanceDTO.class);
			vendorAdvanceDTOs.add(vendorAdvacneDTO);
		}
		return vendorAdvanceDTOs;
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
	public void addVendorAdvance(VendorAdvance vendorAdvance) throws Exception {		
		 vendorAdvanceRepository.save(vendorAdvance);
		
	}
	
	public void deleteByVendorAdvanceId(Long id, String modifiedBy) throws Exception {
		
		Optional<VendorAdvance> vendorAdvance = vendorAdvanceRepository.findById(id);
		if (vendorAdvance.isPresent()) {
			VendorAdvance vendorAdvanceEntitiy = vendorAdvance.get();
			short delete = 1;
			vendorAdvanceEntitiy.setIsDelete(delete);
			vendorAdvanceEntitiy.setModifiedBy(modifiedBy);
			vendorAdvanceRepository.save(vendorAdvanceEntitiy);

		}else {
			
			log.info("No vendor advance found with the provided ID{} in the DB",id);
			throw new Exception("No Vendor Advance found with the provided ID in the DB :"+id);
		}		
	}

	/*
	 * FIND VENDOR INVOICE BY ID 
	 *  
	 */
	@Override
	public VendorAdvanceDTO findVendorAdvanceById(long id, short isDelete) throws Exception {
		VendorAdvanceDTO vendorAdvanceDTO = null;
		VendorAdvance vendorAdvance = vendorAdvanceRepository.findVendorAdvanceByIdAndIsDelete(id,isDelete);
		if(vendorAdvance !=null) {
			vendorAdvanceDTO =  modelMapper.map(vendorAdvance, VendorAdvanceDTO.class);
		}		
		return vendorAdvanceDTO;
	}


	@Override
	public List<VendorAdvanceResponse> findVendorAdvanceByUserId(long userId, Date startActiondate,
			Date endActionDate) {

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
	}


	@Override
	public List<VendorAdvanceDTO> getAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(long userId,
			Date startDate, Date endDate) {
		
		boolean isDatesValid = TSMUtil.validateDates(startDate,endDate);
		
		//defaulting to the current week date range, when there is no date range mentioned from front end.
		if(!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en","IN"));
			startDate =currentWeek.getFirstDay();
			endDate = currentWeek.getLastDay();
		}else {
			startDate = TSMUtil.getFormattedDate(startDate);
			endDate =  TSMUtil.getFormattedDate(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
//			c.add(Calendar.DATE, 1);
			endDate = c.getTime();
		}
		
		Short notDeleted =0;
		
		
		
		
		List<VendorAdvance> vendorAdvanceList = vendorAdvanceRepository.findByUserIdAndIsDeleteAndSubmitDateBetween(userId,notDeleted,startDate,endDate);
		List<VendorAdvanceDTO> vendorAdvanceDTOs = new ArrayList<VendorAdvanceDTO>();		
		VendorAdvanceDTO vendorAdvanceDTO = null;
		for (VendorAdvance vendorAdvance : vendorAdvanceList) {
			vendorAdvanceDTO = modelMapper.map(vendorAdvance, VendorAdvanceDTO.class);
			vendorAdvanceDTOs.add(vendorAdvanceDTO);
		}
		return vendorAdvanceDTOs;
		
	}


	@Override
	public List<VendorAdvanceResponse> findAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(long userId,
			String startVendorInvoiceDate, String endVendorInvoiceDate) {
		
		short notDeleted =0;
		List<VendorAdvance> vendorAdvanceRequestList = vendorAdvanceRepository.findByUserIdAndIsDeleteAndSubmitDateBetween(userId,notDeleted,startVendorInvoiceDate,endVendorInvoiceDate);
		
		System.out.println("vendorAdvanceRequestList ===>"+vendorAdvanceRequestList);
		return getWorkFlowRequestDetails(vendorAdvanceRequestList);
	}
	
	
private List<VendorAdvanceResponse> getWorkFlowRequestDetails(List<VendorAdvance> vendorAdvancetList) {
		
		List<VendorAdvanceResponse> vendorAdvanceResponseList = new ArrayList<>();		
		for (VendorAdvance vendorAdvanceRequest : vendorAdvancetList) {			
			
			VendorAdvanceResponse  vendorAdvanceResponse = new VendorAdvanceResponse();				
				List<VendorAdvanceLineItems> vendorAdvanceLineItemList = vendorAdvanceLineItemsRepository.findByVendorAdvanceId(vendorAdvanceRequest.getId());
				
				vendorAdvanceResponse.setVendorAdvance(vendorAdvanceRequest);
				vendorAdvanceResponse.setVendorAdvancelineItemsList(vendorAdvanceLineItemList);				
				vendorAdvanceResponseList.add(vendorAdvanceResponse);			 
		}
		return vendorAdvanceResponseList;
		//TEMP HIDE
		
		//return null;
	}

	
	
}
