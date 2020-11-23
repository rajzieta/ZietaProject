package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.OrgUnitUserMappingDTO;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.request.OrgUnitUsersRequest;
import com.zieta.tms.response.OrgUnitUsersResponse;

public interface OrgUnitUserMappingService {

	public List<OrgUnitUserMappingDTO> getAllTeamMaster();

	public void addTeamMaster(@Valid OrgUnitUsersRequest usersRequest);

	public void deleteTeamMasterById(Long id) throws Exception;

	void editTeamMaster(@Valid OrgUnitUserMappingDTO teamMasterDTO) throws Exception;

	List<OrgUnitUserMappingDTO> findByClientId(Long clientId);



	
	
}
