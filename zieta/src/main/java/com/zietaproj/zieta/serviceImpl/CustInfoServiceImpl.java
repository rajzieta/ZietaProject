package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.CustInfoDTO;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.CustOrgNodeMapping;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.CustOrgNodeMappingRepository;
import com.zietaproj.zieta.response.CustomerInfoModel;
import com.zietaproj.zieta.response.CustomerInformationModel;
import com.zietaproj.zieta.service.CustInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustInfoServiceImpl implements CustInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustInfoServiceImpl.class);
	
	@Autowired
	CustInfoRepository custInfoRepository;
	
	@Autowired
	CustOrgNodeMappingRepository custOrgNodeMappingRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepo;
	
	
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CustomerInformationModel> getAllCustomers() {
		short notDeleted = 0;
		List<CustInfo> custInfoList = custInfoRepository.findByIsDelete(notDeleted);
		List<CustomerInformationModel>  customerInfoList = new ArrayList<>();
		
		for (CustInfo custInfo : custInfoList) {
			CustomerInformationModel customerInformationModel = modelMapper.map(
					custInfo, CustomerInformationModel.class);
			String clientCode = clientInfoRepo.findById(custInfo.getClientId()).get().getClientCode();
			customerInformationModel.setClientCode(clientCode);
			String clientDesc = clientInfoRepo.findById(custInfo.getClientId()).get().getClientName();
			customerInformationModel.setClientDescription(clientDesc);
			customerInfoList.add(customerInformationModel);
			
		}
		return customerInfoList;
	}

	@Override
	public void addCustInfo(CustInfo custInfo) {
		custInfoRepository.save(custInfo);
		
	}

	@Override
	public List<CustInfo> getAllCustomersByClient(Long clientId) {
		short notDeleted = 0;
		return custInfoRepository.findByClientIdAndIsDelete(clientId, notDeleted);
	}

	@Override
	public List<CustomerInfoModel> findByClientIdAndOrgNode(long clientId, long orgNode) {
		short notDeleted = 0;
		List<CustOrgNodeMapping> custOrgNodeMappingList = custOrgNodeMappingRepository.findByClientIdAndOrgNodeAndIsDelete(clientId, orgNode, notDeleted);
		
		List<CustomerInfoModel> custInfoList = new ArrayList<>();
		for(CustOrgNodeMapping custOrg: custOrgNodeMappingList) {
			
			CustInfo custInfo = custInfoRepository.findById(custOrg.getCustId()).get();
			CustomerInfoModel custInfoModel = modelMapper.map(custInfo, CustomerInfoModel.class);
			String clientCode = clientInfoRepo.findById(clientId).get().getClientCode();
			custInfoModel.setClientCode(clientCode);
			String clientDesc = clientInfoRepo.findById(clientId).get().getClientName();
			custInfoModel.setClientDescription(clientDesc);
			custInfoModel.setOrgNode(orgNode);
			
			custInfoList.add(custInfoModel);
			
		}
		return custInfoList;
	}

	
	public void editCustInfoById(CustInfoDTO custinfoDTO) throws Exception {
		
		Optional<CustInfo> custinfoEntity = custInfoRepository.findById(custinfoDTO.getCustInfoId());
		if(custinfoEntity.isPresent()) {
			CustInfo custinfo = modelMapper.map(custinfoDTO, CustInfo.class);
			custInfoRepository.save(custinfo);
			
		}else {
			throw new Exception("Customer Information not found with the provided ID : "+custinfoDTO.getCustInfoId());
		}
		
		
	}
	
	
	public void deleteCustInfoById(Long id, String modifiedBy) throws Exception {
		
		Optional<CustInfo> custinfo = custInfoRepository.findById(id);
		if (custinfo.isPresent()) {
			CustInfo custinfoEntity = custinfo.get();
			short delete = 1;
			custinfoEntity.setIsDelete(delete);
			custinfoEntity.setModifiedBy(modifiedBy);
			custInfoRepository.save(custinfoEntity);

		}else {
			log.info("No Customer Information found with the provided ID{} in the DB",id);
			throw new Exception("No CustomerInformation found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	
		
	
	
	
}
