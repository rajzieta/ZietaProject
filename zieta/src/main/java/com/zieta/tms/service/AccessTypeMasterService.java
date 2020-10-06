package com.zieta.tms.service;

import java.util.List;
import javax.validation.Valid;

import org.springframework.data.repository.query.Param;

import com.zieta.tms.dto.AccessTypeMasterDTO;
import com.zieta.tms.model.AccessTypeMaster;
import com.zieta.tms.request.AccessTypeAddRequest;
import com.zieta.tms.response.AccesstypesByClientResponse;



public interface AccessTypeMasterService {

	public List<AccessTypeMasterDTO> getAllAccesstypes();

	public AccessTypeMaster addAccessTypemaster(AccessTypeAddRequest accesstypeparam);
	
	public List<String> findByClientIdANDAccessTypeId(Long clientId,
			  Long accessTypeId);

	public List<AccesstypesByClientResponse> getAccessTypesByClient(Long clientId);

	public void editAccessTypesById(@Valid AccessTypeAddRequest accesstypeeditRequest) throws Exception;

	public void deleteAccessTypesById(Long id, String modifiedBy) throws Exception;
	
}
