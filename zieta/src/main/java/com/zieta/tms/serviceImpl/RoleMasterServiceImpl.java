package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.RoleMasterDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.RoleMaster;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.RoleMasterRepository;
import com.zieta.tms.request.RoleMasterEditRequest;
import com.zieta.tms.response.RolesByClientResponse;
import com.zieta.tms.service.RoleMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleMasterServiceImpl implements RoleMasterService{

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleMasterServiceImpl.class);
	@Autowired
	RoleMasterRepository roleMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<RoleMasterDTO> getAllRoles() {
		short notDeleted=0;
		List<RoleMaster> roleMasters= roleMasterRepository.findByIsDelete(notDeleted);
		List<RoleMasterDTO> roleMasterDTOs = new ArrayList<RoleMasterDTO>();
		RoleMasterDTO roleMasterDTO = null;
		for (RoleMaster roleMaster : roleMasters) {
			roleMasterDTO = new RoleMasterDTO();
			roleMasterDTO.setId(roleMaster.getId());
			roleMasterDTO.setClientId(roleMaster.getClientId());
			roleMasterDTO.setUserRole(roleMaster.getUserRole());
			roleMasterDTO.setCreatedBy(roleMaster.getCreatedBy());
			roleMasterDTO.setModifiedBy(roleMaster.getModifiedBy());
			ClientInfo clientInfo = clientInfoRepo.findById(roleMaster.getClientId()).get();
			roleMasterDTO.setClientCode(clientInfo.getClientCode());
			roleMasterDTO.setClientDescription(clientInfo.getClientName());
			roleMasterDTOs.add(roleMasterDTO);
		}
		return roleMasterDTOs;
	}
	
	@Override
	public void addRolemaster(RoleMaster rolemaster)
	{
		roleMasterRepository.save(rolemaster);
	}
	
	@Override
	public List<RolesByClientResponse> getRolesByClient(Long clientId) {
		short notDeleted=0;
		List<RoleMaster> rolesByClientList = roleMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<RolesByClientResponse> rolesByClientResponseList = new ArrayList<>();
		RolesByClientResponse rolesByClientResponse = null;
		for (RoleMaster rolesByClient : rolesByClientList) {
			rolesByClientResponse = modelMapper.map(rolesByClient, 
					RolesByClientResponse.class);
			rolesByClientResponse.setClientCode(clientInfoRepo.findById(clientId).get().getClientCode());
			rolesByClientResponse.setClientDescription(clientInfoRepo.findById(clientId).get().getClientName());

			rolesByClientResponse.setClientId(clientId);
			rolesByClientResponseList.add(rolesByClientResponse);
		}

		return rolesByClientResponseList;
	}
	
	@Override
	public void editUserRolesById(RoleMasterEditRequest rolemastereditRequest) throws Exception {
		
		Optional<RoleMaster> roleMasterEntity = roleMasterRepository.findById(rolemastereditRequest.getId());
		if(roleMasterEntity.isPresent()) {
			RoleMaster rolemaster = modelMapper.map(rolemastereditRequest, RoleMaster.class);
				roleMasterRepository.save(rolemaster);
			
		}else {
			throw new Exception("UserRole not found with the provided ID : "+rolemastereditRequest.getId());
		}
		
		
	}
	
	
	public void deleteUserRolesById(Long id, String modifiedBy) throws Exception {
		
		Optional<RoleMaster> rolemaster = roleMasterRepository.findById(id);
		if (rolemaster.isPresent()) {
			RoleMaster rolemasterEntitiy = rolemaster.get();
			short delete = 1;
			rolemasterEntitiy.setIsDelete(delete);
			rolemasterEntitiy.setModifiedBy(modifiedBy);
			roleMasterRepository.save(rolemasterEntitiy);

		}else {
			log.info("No User Role found with the provided ID{} in the DB",id);
			throw new Exception("No UserRole found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	
	
}
