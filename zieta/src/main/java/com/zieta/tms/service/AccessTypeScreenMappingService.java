package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.dto.AccessTypeScreenMappingDTO;
import com.zieta.tms.model.AccessTypeScreenMapping;
import com.zieta.tms.request.AccessScreensRequest;

public interface AccessTypeScreenMappingService {
	
	
	public void assignScreenToAccessType(AccessTypeScreenMapping accessTypeScreenMapping);
	
	public void assignMultipleScreensToAccessType(AccessScreensRequest accessScreensRequest);

	public List<AccessTypeScreenMappingDTO> getAllAccesstypeScreensMapping();

	public void editAccessScreenMapping(AccessTypeScreenMappingDTO accessScreenmapdto) throws Exception;

	public void deleteAccessScreensMappingById(Long id, String modifiedBy) throws Exception;
	
	public void deleteAccessTypeAndScreens(Long clientId, Long accessTypeId);

	public List<AccessTypeScreenMappingDTO> getAccessTypeScreenMappingByClient(Long clientId);



}
