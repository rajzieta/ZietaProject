package com.zietaproj.zieta.service;

import java.util.List;
import javax.validation.Valid;

import org.springframework.data.repository.query.Param;

import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;



public interface AccessTypeMasterService {

	public List<AccessTypeMasterDTO> getAllAccesstypes();

	public void addAccessTypemaster(AccessTypeMaster accesstypemaster);
	
	public List<String> findByClientIdANDAccessTypeId(Long clientId,
			  List<Long> accessIdList);
	
}
