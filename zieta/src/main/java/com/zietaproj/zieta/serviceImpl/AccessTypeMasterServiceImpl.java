package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.model.TaskTypeMaster;
import com.zietaproj.zieta.repository.AccessTypeMasterRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.request.AccessTypeAddRequest;
import com.zietaproj.zieta.response.AccesstypesByClientResponse;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.AccessTypeMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccessTypeMasterServiceImpl implements AccessTypeMasterService {
	@Autowired
	AccessTypeMasterRepository accesstypeMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<AccessTypeMasterDTO> getAllAccesstypes() {
		short notDeleted = 0;
		List<AccessTypeMaster> accesstypeMasters= accesstypeMasterRepository.findByIsDelete(notDeleted);
		List<AccessTypeMasterDTO> accessTypeMasterDTOs = new ArrayList<AccessTypeMasterDTO>();
		AccessTypeMasterDTO accessTypeMasterDTO = null;
		for (AccessTypeMaster accesstypeMaster : accesstypeMasters) {
			accessTypeMasterDTO = new AccessTypeMasterDTO();
			accessTypeMasterDTO.setId(accesstypeMaster.getId());
			accessTypeMasterDTO.setClientId(accesstypeMaster.getClientId());
			accessTypeMasterDTO.setAccessType(accesstypeMaster.getAccessType());
			accessTypeMasterDTO.setCreatedBy(accesstypeMaster.getCreatedBy());
			accessTypeMasterDTO.setModifiedBy(accesstypeMaster.getModifiedBy());
			accessTypeMasterDTO.setClientCode(clientInfoRepository.findById(accesstypeMaster.getClientId()).get().getClientCode());
			accessTypeMasterDTO.setClientDescription(clientInfoRepository.findById(accesstypeMaster.getClientId()).get().getClientName());
			
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
	public List<String> findByClientIdANDAccessTypeId(Long clientId, Long id) {
		//short notDeleted=0;
		return accesstypeMasterRepository.findByClientIdANDAccessTypeId(clientId, id);
	}
	
	public List<AccesstypesByClientResponse> getAccessTypesByClient(Long clientId) {
		short notDeleted = 0;
		List<AccessTypeMaster> accesstypesByClientList = accesstypeMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<AccesstypesByClientResponse> accesstypesByClientResponseList = new ArrayList<>();
		AccesstypesByClientResponse accessByClientResponse = null;
		for (AccessTypeMaster accessByClient : accesstypesByClientList) {
			accessByClientResponse = modelMapper.map(accessByClient, 
					AccesstypesByClientResponse.class);
			accessByClientResponse.setClientCode(clientInfoRepository.findById(accessByClient.getClientId()).get().getClientCode());
			accessByClientResponse.setClientDescription(clientInfoRepository.findById(accessByClient.getClientId()).get().getClientName());
			
			accesstypesByClientResponseList.add(accessByClientResponse);
			
		}

		return accesstypesByClientResponseList;
	}

	@Override
	public void editAccessTypesById(@Valid AccessTypeAddRequest accesstypeeditRequest) throws Exception {
		Optional<AccessTypeMaster> accessMasterEntity = accesstypeMasterRepository.findById(accesstypeeditRequest.getId());
		if(accessMasterEntity.isPresent()) {
			AccessTypeMaster accessmaster = modelMapper.map(accesstypeeditRequest, AccessTypeMaster.class);
			accesstypeMasterRepository.save(accessmaster);
			
		}else {
			throw new Exception("UserAccess not found with the provided ID : "+accesstypeeditRequest.getId());
		}
		
	}
	
	
	public void deleteAccessTypesById(Long id, String modifiedBy) throws Exception {
		

		Optional<AccessTypeMaster> accessmaster = accesstypeMasterRepository.findById(id);
		if (accessmaster.isPresent()) {
			AccessTypeMaster accessmasterEntitiy = accessmaster.get();
			short delete = 1;
			accessmasterEntitiy.setIsDelete(delete);
			accessmasterEntitiy.setModifiedBy(modifiedBy);
			accesstypeMasterRepository.save(accessmasterEntitiy);

		}else {
			log.info("No Access Type found with the provided ID{} in the DB",id);
			throw new Exception("No AccessType found with the provided ID in the DB :"+id);
		}
		
		
		
	}
	
	
	
}
