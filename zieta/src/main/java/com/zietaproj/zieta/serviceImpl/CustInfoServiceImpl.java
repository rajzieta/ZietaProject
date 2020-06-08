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
import com.zietaproj.zieta.service.CustInfoService;

@Service
public class CustInfoServiceImpl implements CustInfoService {

	
	@Autowired
	CustInfoRepository custInfoRepository;
	
	@Autowired
	CustOrgNodeMappingRepository custOrgNodeMappingRepository;
	
	@Autowired
	ClientInfoRepository clietinfoRepo;
	
	
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CustInfo> getAllCustomers() {
		
		return custInfoRepository.findAll();
	}

	@Override
	public void addCustInfo(CustInfo custInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustInfo> getAllCustomersByClient(Long clientId) {
		return custInfoRepository.findByClientId(clientId);
	}

	@Override
	public List<CustomerInfoModel> findByClientIdAndOrgNode(long clientId, long orgNode) {
		List<CustOrgNodeMapping> custOrgNodeMappingList = custOrgNodeMappingRepository.findByClientIdAndOrgNode(clientId, orgNode);
		
		List<CustomerInfoModel> custInfoList = new ArrayList<>();
		for(CustOrgNodeMapping custOrg: custOrgNodeMappingList) {
			
			CustInfo custInfo = custInfoRepository.findById(custOrg.getCustId()).get();
			CustomerInfoModel custInfoModel = modelMapper.map(custInfo, CustomerInfoModel.class);
			String clientCode = clietinfoRepo.findById(clientId).get().getClient_code();
			custInfoModel.setClient_code(clientCode);
			custInfoModel.setOrgNode(orgNode);
			
			custInfoList.add(custInfoModel);
			
		}
		return custInfoList;
	}

	
	
	
	
	
}
