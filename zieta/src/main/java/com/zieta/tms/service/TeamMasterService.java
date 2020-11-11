package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.TeamMasterDTO;
import com.zieta.tms.model.TeamMaster;

public interface TeamMasterService {

	public List<TeamMasterDTO> getAllTeamMaster();

	public void addTeamMaster(@Valid TeamMaster teammaster);

	public void deleteTeamMasterById(Long id) throws Exception;

	void editTeamMaster(@Valid TeamMasterDTO teamMasterDTO) throws Exception;

	List<TeamMasterDTO> findByClientId(Long clientId);

	
	
}
