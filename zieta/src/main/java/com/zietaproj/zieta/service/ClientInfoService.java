package com.zietaproj.zieta.service;

import java.util.List;


import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.request.ClientInfoRequest;

public interface ClientInfoService {

	
	public List<ClientInfoDTO> getAllClients();

	public void addClientInfo(ClientInfoRequest clientinfo);
}
