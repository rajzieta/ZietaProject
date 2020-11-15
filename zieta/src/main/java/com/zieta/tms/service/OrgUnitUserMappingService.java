package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.OrgUnitUserMappingDTO;
import com.zieta.tms.model.OrgUnitUserMapping;

public interface OrgUnitUserMappingService {

	public List<OrgUnitUserMappingDTO> getAllTeamMaster();

	public void addTeamMaster(@Valid OrgUnitUserMapping teammaster);

	public void deleteTeamMasterById(Long id) throws Exception;

	void editTeamMaster(@Valid OrgUnitUserMappingDTO teamMasterDTO) throws Exception;

	List<OrgUnitUserMappingDTO> findByClientId(Long clientId);

	
	
}
