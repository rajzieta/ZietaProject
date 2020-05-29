package com.zietaproj.zieta.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.AccessControlConfig;
import com.zietaproj.zieta.service.AccessControlConfigService;



@Service
public class AccessControlConfigServiceImpl implements AccessControlConfigService {

	@Autowired
	AccessControlConfigService accessControlConfigService;

	@Override
	public List<Long> findByClientIdANDAccessTypeId(Long clientId, List<Long> accessIdList) {

		List<Long> accessControlConfigList =  accessControlConfigService.findByClientIdANDAccessTypeId(clientId, accessIdList);
		
		return accessControlConfigList;
	}

	
	

	
}
