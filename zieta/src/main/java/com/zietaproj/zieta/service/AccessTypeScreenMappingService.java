package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.AccessTypeScreenMappingDTO;
import com.zietaproj.zieta.model.AccessTypeScreenMapping;
import com.zietaproj.zieta.request.AccessScreensRequest;

public interface AccessTypeScreenMappingService {
	
	
	public void assignScreenToAccessType(AccessTypeScreenMapping accessTypeScreenMapping);
	
	public void assignMultipleScreensToAccessType(AccessScreensRequest accessScreensRequest);

	public List<AccessTypeScreenMappingDTO> getAllAccesstypeScreensMapping();

	public void editAccessScreenMapping(AccessTypeScreenMappingDTO accessScreenmapdto) throws Exception;

	public void deleteAccessScreensMappingById(Long id, String modifiedBy) throws Exception;
	
	public void deleteAccessTypeAndScreens(Long clientId, Long accessTypeId);

	public List<AccessTypeScreenMappingDTO> getAccessTypeScreenMappingByClient(Long clientId);



}
