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
import com.zieta.tms.dto.VendorInvoiceDTO;
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
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.VendorInvoice;
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
import com.zieta.tms.repository.VendorInvoiceRepository;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.VendorInvoiceService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class VendorInvoiceServiceImpl implements VendorInvoiceService {

	@Autowired
	VendorInvoiceRepository vendorInvoiceRepository;

	@Autowired
	ModelMapper modelMapper;	 

	/*
	 * GET ALL ACTIVE VENDOR INVOICE
	 */
	@Override
	public List<VendorInvoiceDTO> getAllActiveVendorInvoice(Long clientId, Short notDeleted) {
		
		List<VendorInvoice> vendorInvoiceList = vendorInvoiceRepository.findByClientIdAndIsDelete(clientId,notDeleted);
		List<VendorInvoiceDTO> vendorInvoiceDTOs = new ArrayList<VendorInvoiceDTO>();		
		VendorInvoiceDTO vendorInvoiceDTO = null;
		for (VendorInvoice vendorInvoice : vendorInvoiceList) {
			vendorInvoiceDTO = modelMapper.map(vendorInvoice, VendorInvoiceDTO.class);
			vendorInvoiceDTOs.add(vendorInvoiceDTO);
		}
		return vendorInvoiceDTOs;
	}
	
		
	/*
	 * TO SAVE VENDOR INVOICE DATA 
	 */
	@Override
	public VendorInvoice addVendorInvoice(VendorInvoice vendorInvoice) throws Exception {		
		VendorInvoice vndrInvoice = vendorInvoiceRepository.save(vendorInvoice);
		return vndrInvoice;
	}
	
	public void deleteByVendorInvoiceId(Long id, String modifiedBy) throws Exception {
		
		Optional<VendorInvoice> vendorInvoice = vendorInvoiceRepository.findById(id);
		if (vendorInvoice.isPresent()) {
			VendorInvoice vendorInvoiceEntitiy = vendorInvoice.get();
			short delete = 1;
			vendorInvoiceEntitiy.setIsDelete(delete);
			vendorInvoiceEntitiy.setModifiedBy(modifiedBy);
			vendorInvoiceRepository.save(vendorInvoiceEntitiy);

		}else {
			
			log.info("No vendor invoice found with the provided ID{} in the DB",id);
			throw new Exception("No Vendor Invoice found with the provided ID in the DB :"+id);
		}		
	}

	/*
	 * FIND VENDOR INVOICE BY ID 
	 *  
	 */
	@Override
	public VendorInvoiceDTO findVendorInvoiceById(long id) throws Exception {
		VendorInvoiceDTO vendorInvoiceDTO = null;
		VendorInvoice vendorInvoice = vendorInvoiceRepository.findVendorInvoiceById(id);
		if(vendorInvoice !=null) {
			vendorInvoiceDTO =  modelMapper.map(vendorInvoice, VendorInvoiceDTO.class);
		}		
		return vendorInvoiceDTO;
	}

	
	
}
