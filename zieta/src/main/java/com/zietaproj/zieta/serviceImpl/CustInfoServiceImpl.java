package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.CustOrgNodeMapping;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.CustOrgNodeMappingRepository;
import com.zietaproj.zieta.service.CustInfoService;

@Service
public class CustInfoServiceImpl implements CustInfoService {

	
	@Autowired
	CustInfoRepository custInfoRepository;
	
	@Autowired
	CustOrgNodeMappingRepository custOrgNodeMappingRepository;
	
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
	public List<CustInfo> findByClientIdAndOrgNode(long clientId, long orgNode) {
		List<CustOrgNodeMapping> custOrgNodeMappingList = custOrgNodeMappingRepository.findByClientIdAndOrgNode(clientId, orgNode);
		
		List<CustInfo> custInfoList = new ArrayList<>();
		for(CustOrgNodeMapping custOrg: custOrgNodeMappingList) {
			custInfoList.add(custInfoRepository.findById(custOrg.getCustId()).get());
			
		}
		return custInfoList;
	}

	
	
	
	
	
}
