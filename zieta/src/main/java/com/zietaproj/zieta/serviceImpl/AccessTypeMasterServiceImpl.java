package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.repository.AccessTypeMasterRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.request.AccessTypeAddRequest;
import com.zietaproj.zieta.response.AccesstypesByClientResponse;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.AccessTypeMasterService;

@Service
public class AccessTypeMasterServiceImpl implements AccessTypeMasterService {
	@Autowired
	AccessTypeMasterRepository accesstypeMasterRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<AccessTypeMasterDTO> getAllAccesstypes() {
		List<AccessTypeMaster> accesstypeMasters= accesstypeMasterRepository.findAll();
		List<AccessTypeMasterDTO> accessTypeMasterDTOs = new ArrayList<AccessTypeMasterDTO>();
		AccessTypeMasterDTO accessTypeMasterDTO = null;
		for (AccessTypeMaster accesstypeMaster : accesstypeMasters) {
			accessTypeMasterDTO = new AccessTypeMasterDTO();
			accessTypeMasterDTO.setId(accesstypeMaster.getId());
			accessTypeMasterDTO.setClient_id(accesstypeMaster.getClientId());
			accessTypeMasterDTO.setAccess_type(accesstypeMaster.getAccessType());
			accessTypeMasterDTO.setCreated_by(accesstypeMaster.getCreatedBy());
			accessTypeMasterDTO.setModified_by(accesstypeMaster.getModifiedBy());
			accessTypeMasterDTOs.add(accessTypeMasterDTO);
		}
		return accessTypeMasterDTOs;
	}
	
	@Override
	public void addAccessTypemaster(AccessTypeAddRequest accesstypeparam)
	{
		AccessTypeMaster accesstypemaster = modelMapper.map(accesstypeparam, AccessTypeMaster.class);
		accesstypeMasterRepository.save(accesstypemaster);
	}

	@Override
	public List<String> findByClientIdANDAccessTypeId(Long clientId, List<Long> accessIdList) {
		return accesstypeMasterRepository.findByClientIdANDAccessTypeId(clientId, accessIdList);
	}
	
	public List<AccesstypesByClientResponse> getAccessTypesByClient(Long clientId) {
	
		List<AccessTypeMaster> accesstypesByClientList = accesstypeMasterRepository.findByClientId(clientId);
		List<AccesstypesByClientResponse> accesstypesByClientResponseList = new ArrayList<>();
		AccesstypesByClientResponse accessByClientResponse = null;
		for (AccessTypeMaster accessByClient : accesstypesByClientList) {
			accessByClientResponse = modelMapper.map(accessByClient, 
					AccesstypesByClientResponse.class);
		
			accesstypesByClientResponseList.add(accessByClientResponse);
			
		}

		return accesstypesByClientResponseList;
	}

}
