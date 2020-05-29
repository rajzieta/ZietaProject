package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.repository.AccessTypeMasterRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.service.AccessTypeMasterService;

@Service
public class AccessTypeMasterServiceImpl implements AccessTypeMasterService {
	@Autowired
	AccessTypeMasterRepository accesstypeMasterRepository;
	
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
			accessTypeMasterDTO.setCreated_by(accesstypeMaster.getCreated_by());
			accessTypeMasterDTO.setModified_by(accesstypeMaster.getModified_by());
			accessTypeMasterDTOs.add(accessTypeMasterDTO);
		}
		return accessTypeMasterDTOs;
	}
	
	@Override
	public void addAccessTypemaster(AccessTypeMaster accesstypemaster)
	{
		accesstypeMasterRepository.save(accesstypemaster);
	}

	@Override
	public List<String> findByClientIdANDAccessTypeId(Long clientId, List<Long> accessIdList) {
		return accesstypeMasterRepository.findByClientIdANDAccessTypeId(clientId, accessIdList);
	}
	

}
