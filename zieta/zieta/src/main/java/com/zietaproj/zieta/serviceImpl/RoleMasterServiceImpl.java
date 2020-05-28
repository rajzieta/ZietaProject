package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.repository.RoleMasterRepository;
import com.zietaproj.zieta.service.RoleMasterService;

@Service
public class RoleMasterServiceImpl implements RoleMasterService{

	@Autowired
	RoleMasterRepository roleMasterRepository;
	
	@Override
	public List<RoleMasterDTO> getAllRoles() {
		List<RoleMaster> roleMasters= roleMasterRepository.findAll();
		List<RoleMasterDTO> roleMasterDTOs = new ArrayList<RoleMasterDTO>();
		RoleMasterDTO roleMasterDTO = null;
		for (RoleMaster roleMaster : roleMasters) {
			roleMasterDTO = new RoleMasterDTO();
			roleMasterDTO.setId(roleMaster.getId());
			roleMasterDTO.setClient_id(roleMaster.getClient_id());
			roleMasterDTO.setUser_role(roleMaster.getUser_role());
			roleMasterDTO.setCreated_by(roleMaster.getCreated_by());
			roleMasterDTO.setModified_by(roleMaster.getModified_by());
			roleMasterDTOs.add(roleMasterDTO);
		}
		return roleMasterDTOs;
	}
	
	@Override
	public void addRolemaster(RoleMaster rolemaster)
	{
		roleMasterRepository.save(rolemaster);
	}

}
