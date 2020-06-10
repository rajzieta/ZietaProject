package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.RoleMasterRepository;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.RoleMasterService;

@Service
public class RoleMasterServiceImpl implements RoleMasterService{

	@Autowired
	RoleMasterRepository roleMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<RoleMasterDTO> getAllRoles() {
		List<RoleMaster> roleMasters= roleMasterRepository.findAll();
		List<RoleMasterDTO> roleMasterDTOs = new ArrayList<RoleMasterDTO>();
		RoleMasterDTO roleMasterDTO = null;
		for (RoleMaster roleMaster : roleMasters) {
			roleMasterDTO = new RoleMasterDTO();
			roleMasterDTO.setId(roleMaster.getId());
			roleMasterDTO.setClient_id(roleMaster.getClientId());
			roleMasterDTO.setUser_role(roleMaster.getUser_role());
			roleMasterDTO.setCreated_by(roleMaster.getCreated_by());
			roleMasterDTO.setModified_by(roleMaster.getModified_by());
			ClientInfo clientInfo = clientInfoRepo.findById(roleMaster.getClientId()).get();
			roleMasterDTO.setClientCode(clientInfo.getClient_code());
			roleMasterDTO.setClientName(clientInfo.getClient_name());
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

		List<RoleMaster> rolesByClientList = roleMasterRepository.findByClientId(clientId);
		List<RolesByClientResponse> rolesByClientResponseList = new ArrayList<>();
		RolesByClientResponse rolesByClientResponse = null;
		for (RoleMaster rolesByClient : rolesByClientList) {
			rolesByClientResponse = modelMapper.map(rolesByClient, 
					RolesByClientResponse.class);
			rolesByClientResponse.setClientCode(clientInfoRepo.findById(clientId).get().getClient_code());
			rolesByClientResponse.setClientId(clientId);
			rolesByClientResponseList.add(rolesByClientResponse);
		}

		return rolesByClientResponseList;
		
	
	}
}
