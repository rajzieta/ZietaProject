package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.CustOrgNodeMapping;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.CustOrgNodeMappingRepository;
import com.zietaproj.zieta.response.CustomerInfoModel;
import com.zietaproj.zieta.response.CustomerInformationModel;
import com.zietaproj.zieta.service.CustInfoService;

@Service
public class CustInfoServiceImpl implements CustInfoService {

	
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
			String clientCode = clientInfoRepo.findById(custInfo.getClientId()).get().getClient_code();
			customerInformationModel.setClientCode(clientCode);
			String clientDesc = clientInfoRepo.findById(custInfo.getClientId()).get().getClient_name();
			customerInformationModel.setClientDescription(clientDesc);
			customerInfoList.add(customerInformationModel);
			
		}
		return customerInfoList;
	}

	@Override
	public void addCustInfo(CustInfo custInfo) {
		// TODO Auto-generated method stub
		
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
			String clientCode = clientInfoRepo.findById(clientId).get().getClient_code();
			custInfoModel.setClientCode(clientCode);
			String clientDesc = clientInfoRepo.findById(clientId).get().getClient_name();
			custInfoModel.setClientDescription(clientDesc);
			custInfoModel.setOrgNode(orgNode);
			
			custInfoList.add(custInfoModel);
			
		}
		return custInfoList;
	}

	
	
	
	
	
}
