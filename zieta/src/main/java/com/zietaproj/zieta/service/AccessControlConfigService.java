package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.AccessControlConfig;

public interface AccessControlConfigService {

	List<Long> findByClientIdANDAccessTypeId(Long clientId, 
			 List<Long> accessIdList);

}
