package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.AccessTypeScreenMappingDTO;
import com.zietaproj.zieta.model.AccessTypeScreenMapping;

public interface AccessTypeScreenMappingService {
	
	
	public void assignScreenToAccessType(AccessTypeScreenMapping accessTypeScreenMapping);

	public List<AccessTypeScreenMappingDTO> getAllAccesstypeScreensMapping();

	public void editAccessScreenMapping(AccessTypeScreenMappingDTO accessScreenmapdto) throws Exception;

	public void deleteAccessScreensMappingById(Long id, String modifiedBy) throws Exception;

}
