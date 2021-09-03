package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.zieta.tms.model.UserInfo;
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
import com.zieta.tms.repository.VendorInvoiceLineItemsRepository;
import com.zieta.tms.repository.VendorInvoiceRepository;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.VendorInvoiceLineItemsService;
import com.zieta.tms.service.VendorInvoiceService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class VendorInvoiceLineItemsServiceImpl implements VendorInvoiceLineItemsService {

	@Autowired
	VendorInvoiceRepository vendorInvoiceRepository;
	
	@Autowired
	VendorInvoiceLineItemsRepository vendorInvoiceLineItemsRepository;

	@Autowired
	ModelMapper modelMapper;	 

	/*
	 * GET ALL ACTIVE VENDOR INVOICE
	 */
	@Override
	public List<VendorInvoiceLineItemsDTO> getAllActiveVendorInvoiceLineItems(Long clientId, Short notDeleted) {
		
		List<VendorInvoicelineItems> vendorInvoiceLineItemsList = vendorInvoiceLineItemsRepository.findByIsDelete(notDeleted);
		List<VendorInvoiceLineItemsDTO> vendorInvoiceLineItemsDTOs = new ArrayList<VendorInvoiceLineItemsDTO>();		
		VendorInvoiceLineItemsDTO vendorInvoiceLineItemsDTO = null;
		for (VendorInvoicelineItems vendorInvoicelineItems : vendorInvoiceLineItemsList) {
			vendorInvoiceLineItemsDTO = modelMapper.map(vendorInvoicelineItems, VendorInvoiceLineItemsDTO.class);
			vendorInvoiceLineItemsDTOs.add(vendorInvoiceLineItemsDTO);
		}
		return vendorInvoiceLineItemsDTOs;
	}
	
		
	/*
	 * TO SAVE VENDOR INVOICE DATA 
	 */
	@Override
	public VendorInvoicelineItems addVendorInvoiceLineItems(VendorInvoicelineItems vendorInvoicelineItems) throws Exception {		
		VendorInvoicelineItems vndrInvoiceLineItems = vendorInvoiceLineItemsRepository.save(vendorInvoicelineItems);
		return vndrInvoiceLineItems;
	}
	
	public void deleteByVendorInvoiceLineItemsId(Long id, String modifiedBy) throws Exception {
		
		Optional<VendorInvoicelineItems> vendorInvoiceLnItem = vendorInvoiceLineItemsRepository.findById(id);
		if (vendorInvoiceLnItem.isPresent()){
			VendorInvoicelineItems vendorInvoiceLinItemsEntitiy = vendorInvoiceLnItem.get();
			short delete = 1;
			vendorInvoiceLinItemsEntitiy.setIsDelete(delete);
			vendorInvoiceLinItemsEntitiy.setModifiedBy(modifiedBy);
			vendorInvoiceLineItemsRepository.save(vendorInvoiceLinItemsEntitiy);

		}else{
			
			log.info("No vendor invoice lineitem found with the provided ID{} in the DB",id);
			throw new Exception("No Vendor Invoice lineitems found with the provided ID in the DB :"+id);
		}		
	}

	/*
	 * FIND VENDOR INVOICE BY ID 
	 *  
	 */
	
	  @Override public VendorInvoiceLineItemsDTO findVendorInvoiceById(long id) throws Exception {
		  
		 VendorInvoiceLineItemsDTO vendorInvoiceLineItemsDTO = null; 
		 VendorInvoicelineItems vendorInvoicelineItems = vendorInvoiceLineItemsRepository.findVendorInvoiceLineItemsById(id);
		 if(vendorInvoicelineItems !=null) { 
			 vendorInvoiceLineItemsDTO = modelMapper.map(vendorInvoicelineItems, VendorInvoiceLineItemsDTO.class);
			 }
		 return vendorInvoiceLineItemsDTO;
	 }
	 




	


	@Override
	public VendorInvoiceLineItemsDTO findVendorInvoiceLineItemsById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<VendorInvoiceLineItemsDTO> getAllActiveVendorInvoiceLineItemsByVindorInvoiceId(Long vendorInvId,
			Short isDelete) {
		VendorInvoiceLineItemsDTO vendorInvoiceLineItemsDTO = null; 
		List<VendorInvoiceLineItemsDTO> vendorInvoiceLineItemsDTOs = new ArrayList<VendorInvoiceLineItemsDTO>();
		List<VendorInvoicelineItems> vendorInvoicelineItemsList = vendorInvoiceLineItemsRepository.findVendorInvoiceLineItemsByVendorInvoiceId(vendorInvId,isDelete);
		for (VendorInvoicelineItems vendorInvoicelineItems : vendorInvoicelineItemsList) {
			vendorInvoiceLineItemsDTO = modelMapper.map(vendorInvoicelineItems, VendorInvoiceLineItemsDTO.class);
			vendorInvoiceLineItemsDTOs.add(vendorInvoiceLineItemsDTO);
		}
		 return vendorInvoiceLineItemsDTOs;	 
		//return null;
	}
	
	
	@Override
	public List<VendorInvoiceLineItemsDTO> getAllVendorLineItemsByVendorInvoiceId(Long InvoiceId) {
		
		List<VendorInvoicelineItems>vendorInvoiceLineItemsList = vendorInvoiceLineItemsRepository.findByVendorInvoiceId(InvoiceId);
		List<VendorInvoiceLineItemsDTO> vendorInvoiceLineItemsDTOs = new ArrayList<VendorInvoiceLineItemsDTO>();
		
		VendorInvoiceLineItemsDTO vendorInvoiceLineItemsDTO = null;
		for (VendorInvoicelineItems invLineItems : vendorInvoiceLineItemsList) {
			vendorInvoiceLineItemsDTO = modelMapper.map(invLineItems, VendorInvoiceLineItemsDTO.class);
			vendorInvoiceLineItemsDTOs.add(vendorInvoiceLineItemsDTO);
		}
		return vendorInvoiceLineItemsDTOs;
	}

	
	
}
