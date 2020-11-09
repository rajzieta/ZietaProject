package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.AccessTypeMasterDTO;
import com.zieta.tms.dto.ActivityMasterDTO;
import com.zieta.tms.model.AccessTypeMaster;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.RoleMaster;
import com.zieta.tms.model.TaskTypeMaster;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.AccessTypeAddRequest;
import com.zieta.tms.response.AccesstypesByClientResponse;
import com.zieta.tms.response.RolesByClientResponse;
import com.zieta.tms.service.AccessTypeMasterService;

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
	UserInfoRepository userInfoRepository;
	
	
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
			accessTypeMasterDTO.setAccessDesc(accesstypeMaster.getAccessDesc());
			accessTypeMasterDTO.setCreatedBy(accesstypeMaster.getCreatedBy());
			accessTypeMasterDTO.setModifiedBy(accesstypeMaster.getModifiedBy());
			accessTypeMasterDTO.setClientCode(clientInfoRepository.findById(accesstypeMaster.getClientId()).get().getClientCode());
			accessTypeMasterDTO.setClientDescription(clientInfoRepository.findById(accesstypeMaster.getClientId()).get().getClientName());
			accessTypeMasterDTO.setClientStatus(clientInfoRepository.findById(accesstypeMaster.getClientId()).get().getClientStatus());
			
			accessTypeMasterDTOs.add(accessTypeMasterDTO);
		}
		return accessTypeMasterDTOs;
	}
	
	@Override
	public AccessTypeMaster addAccessTypemaster(AccessTypeAddRequest accesstypeparam)
	{
		AccessTypeMaster accesstypemaster = modelMapper.map(accesstypeparam, AccessTypeMaster.class);
		accesstypemaster = accesstypeMasterRepository.save(accesstypemaster);
		return accesstypemaster;
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
