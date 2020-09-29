package com.zietaproj.zieta.service;

import java.util.List;
import javax.validation.Valid;

import org.springframework.data.repository.query.Param;

import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.request.AccessTypeAddRequest;
import com.zietaproj.zieta.response.AccesstypesByClientResponse;



public interface AccessTypeMasterService {

	public List<AccessTypeMasterDTO> getAllAccesstypes();

	public AccessTypeMaster addAccessTypemaster(AccessTypeAddRequest accesstypeparam);
	
	public List<String> findByClientIdANDAccessTypeId(Long clientId,
			  Long accessTypeId);

	public List<AccesstypesByClientResponse> getAccessTypesByClient(Long clientId);

	public void editAccessTypesById(@Valid AccessTypeAddRequest accesstypeeditRequest) throws Exception;

	public void deleteAccessTypesById(Long id, String modifiedBy) throws Exception;
	
}
