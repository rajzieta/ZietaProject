package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.response.OrgNodesByClientResponse;

public interface OrgNodesService {

	public List<OrgNodesByClientResponse> getOrgNodesByClient(Long clientId);

	
}
