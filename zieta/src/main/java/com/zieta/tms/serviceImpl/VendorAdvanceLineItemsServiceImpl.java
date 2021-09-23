package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
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
import com.zieta.tms.dto.VendorAdvanceLineItemsDTO;
import com.zieta.tms.dto.VendorInvoiceDTO;
import com.zieta.tms.dto.VendorInvoiceLineItemsDTO;
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
import com.zieta.tms.model.TSTimeEntries;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.VendorAdvanceLineItems;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.model.VendorInvoicelineItems;
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
import com.zieta.tms.repository.VendorAdvanceLineItemsRepository;
import com.zieta.tms.repository.VendorAdvanceRepository;
import com.zieta.tms.repository.VendorInvoiceLineItemsRepository;
import com.zieta.tms.repository.VendorInvoiceRepository;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.VendorAdvanceLineItemsRequest;
import com.zieta.tms.request.VendorInvoiceLineItemsByVendorInvoiceIdRequest;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.VendorAdvanceLineItemsService;
import com.zieta.tms.service.VendorInvoiceLineItemsService;
import com.zieta.tms.service.VendorInvoiceService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class VendorAdvanceLineItemsServiceImpl implements VendorAdvanceLineItemsService {

	@Autowired
	VendorAdvanceRepository vendorAdvanceRepository;
	
	@Autowired
	VendorAdvanceLineItemsRepository vendorAdvanceLineItemsRepository;

	@Autowired
	ModelMapper modelMapper;	 

	/*
	 * GET ALL ACTIVE VENDOR INVOICE
	 */
	@Override
	public List<VendorAdvanceLineItemsDTO> getAllActiveVendorAdvanceLineItems(Long clientId, Short notDeleted) {		
		List<VendorAdvanceLineItems> vendorAdvanceLineItemsList = vendorAdvanceLineItemsRepository.findByIsDelete(notDeleted);
		List<VendorAdvanceLineItemsDTO> vendorAdvanceLineItemsDTOs = new ArrayList<VendorAdvanceLineItemsDTO>();		
		VendorAdvanceLineItemsDTO vendorAdvanceLineItemsDTO = null;
		for (VendorAdvanceLineItems vendorAdvancelineItems : vendorAdvanceLineItemsList) {
			vendorAdvanceLineItemsDTO = modelMapper.map(vendorAdvancelineItems, VendorAdvanceLineItemsDTO.class);
			vendorAdvanceLineItemsDTOs.add(vendorAdvanceLineItemsDTO);
		}
		return vendorAdvanceLineItemsDTOs;
	}
	
		
	/*
	 * TO SAVE VENDOR INVOICE DATA 
	 * 
	 */
	@Override
	@Transactional
	public void addVendorAdvanceLineItems(@Valid List<VendorAdvanceLineItemsRequest> vendorAdvanceLineItemsList) {			
		for (VendorAdvanceLineItemsRequest vendorInvoiceLineItemsRequest : vendorAdvanceLineItemsList) {

			VendorAdvanceLineItems entries = modelMapper.map(vendorInvoiceLineItemsRequest, VendorAdvanceLineItems.class);
			vendorAdvanceLineItemsRepository.save(entries);
		}		
	}
	
	/*
	 * DELETE VENDOR INVIOCE LINEITEMS
	 * 
	 */
	public void deleteByVendorAdvanceLineItemsId(Long id, String modifiedBy) throws Exception {		
		Optional<VendorAdvanceLineItems> vendorAdvLnItem = vendorAdvanceLineItemsRepository.findById(id);
		if (vendorAdvLnItem.isPresent()){
			VendorAdvanceLineItems vendorAdvanceLineItemsEntitiy = vendorAdvLnItem.get();
			short delete = 1;
			vendorAdvanceLineItemsEntitiy.setIsDelete(delete);
			vendorAdvanceLineItemsEntitiy.setModifiedBy(modifiedBy);
			vendorAdvanceLineItemsRepository.save(vendorAdvanceLineItemsEntitiy);

		}else{
			
			log.info("No vendor advance lineitem found with the provided ID{} in the DB",id);
			throw new Exception("No Vendor advance lineitems found with the provided ID in the DB :"+id);
		}		
	}

	/*
	 * FIND VENDOR ADVANCE BY ID  
	 */
	
	  @Override
	  public VendorAdvanceLineItemsDTO findVendorAdvanceById(long id){		  
		 VendorAdvanceLineItemsDTO vendorAdvanceLineItemsDTO = null; 
		 short isDelete = 0;
		 VendorAdvanceLineItems vendorAdvanceLineItems = vendorAdvanceLineItemsRepository.findVendorAdvanceLineItemsById(id,isDelete);
		 if(vendorAdvanceLineItems !=null) { 
			 vendorAdvanceLineItemsDTO = modelMapper.map(vendorAdvanceLineItems, VendorAdvanceLineItemsDTO.class);
			 }
		 return vendorAdvanceLineItemsDTO;
	 }
	  
	@Override
	public VendorAdvanceLineItemsDTO findVendorAdvanceLineItemsById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VendorAdvanceLineItemsDTO> getAllActiveVendorAdvanceLineItemsByVindorAdvanceId(Long vendorInvId,
			Short isDelete) {
		VendorAdvanceLineItemsDTO vendorAdvanceLineItemsDTO = null; 
		List<VendorAdvanceLineItemsDTO> vendorAdvanceLineItemsDTOs = new ArrayList<VendorAdvanceLineItemsDTO>();
		List<VendorAdvanceLineItems> vendorAdvancelineItemsList = vendorAdvanceLineItemsRepository.findVendorAdvanceLineItemsByVendorInvoiceId(vendorInvId,isDelete);
		
		System.out.println("testing===================> "+vendorAdvancelineItemsList);
		
		for (VendorAdvanceLineItems vendorAdvancelineItems : vendorAdvancelineItemsList) {
			vendorAdvanceLineItemsDTO = modelMapper.map(vendorAdvancelineItems, VendorAdvanceLineItemsDTO.class);
			vendorAdvanceLineItemsDTOs.add(vendorAdvanceLineItemsDTO);
		}
		return vendorAdvanceLineItemsDTOs;			
	}


	@Override
	public List<VendorAdvanceLineItemsDTO> getAllVendorLineItemsByVendorAdvanceId(Long vendorInvId) {
		VendorAdvanceLineItemsDTO vendorAdvanceLineItemsDTO = null; 
		short isDelete=0;
		List<VendorAdvanceLineItemsDTO> vendorAdvanceLineItemsDTOs = new ArrayList<VendorAdvanceLineItemsDTO>();
		List<VendorAdvanceLineItems> vendorAdvancelineItemsList = vendorAdvanceLineItemsRepository.findVendorAdvanceLineItemsByVendorInvoiceId(vendorInvId,isDelete);
		
		System.out.println("testing===================> "+vendorAdvancelineItemsList);
		
		for (VendorAdvanceLineItems vendorAdvancelineItems : vendorAdvancelineItemsList) {
			vendorAdvanceLineItemsDTO = modelMapper.map(vendorAdvancelineItems, VendorAdvanceLineItemsDTO.class);
			vendorAdvanceLineItemsDTOs.add(vendorAdvanceLineItemsDTO);
		}
		return vendorAdvanceLineItemsDTOs;
	}
	
	
	/*@Override
	public List<VendorAdvanceLineItemsDTO> getAllVendorAdvanceLineItemsByVendorAdvanceId(Long advanceId) {		
		
		List<VendorInvoicelineItems>vendorInvoiceLineItemsList = vendorInvoiceLineItemsRepository.findByVendorInvoiceId(InvoiceId);
		List<VendorInvoiceLineItemsDTO> vendorInvoiceLineItemsDTOs = new ArrayList<VendorInvoiceLineItemsDTO>();		
		VendorInvoiceLineItemsDTO vendorInvoiceLineItemsDTO = null;
		for (VendorInvoicelineItems invLineItems : vendorInvoiceLineItemsList) {
			vendorInvoiceLineItemsDTO = modelMapper.map(invLineItems, VendorInvoiceLineItemsDTO.class);
			vendorInvoiceLineItemsDTOs.add(vendorInvoiceLineItemsDTO);
		}
		return vendorInvoiceLineItemsDTOs;
	}*/

	
	
}
