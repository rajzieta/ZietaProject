package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.OrgInfoDTO;
import com.zietaproj.zieta.model.OrgInfo;
import com.zietaproj.zieta.response.OrgNodesByClientResponse;

public interface OrgNodesService {

	public List<OrgNodesByClientResponse> getOrgNodesByClient(Long clientId);

	public List<OrgInfoDTO> getAllOrgNodes();

	public void addOrgInfo(@Valid OrgInfo orginfo);

	public void editOrgInfo(@Valid OrgInfoDTO orginfodto) throws Exception;

	public void deleteOrgInfoById(Long id, String modifiedBy) throws Exception;

	public List<OrgNodesByClientResponse> findByClientIdAsHierarchy(Long clientId);

	
}
