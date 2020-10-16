package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.OrgInfoDTO;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.response.OrgNodesByClientResponse;

public interface OrgNodesService {

	public List<OrgNodesByClientResponse> getOrgNodesByClient(Long clientId);

	public List<OrgInfoDTO> getAllOrgNodes();

	public void addOrgInfo(@Valid OrgInfo orginfo);

	public void editOrgInfo(@Valid OrgInfoDTO orginfodto) throws Exception;

	public void deleteOrgInfoById(Long id, String modifiedBy) throws Exception;

	public List<OrgNodesByClientResponse> findByClientIdAsHierarchy(Long clientId);

//	public List<OrgUnitTypeMasterDTO> getAllOrgUnitTypeMaster();

	public List<OrgInfoDTO> getAllOrgNodesAsHeirarchy();



	
}
