package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.request.ClientInfoAddRequest;

public interface ClientInfoService {

	
	public List<ClientInfoDTO> getAllClients();

	public void addClientInfo(ClientInfoAddRequest clientinfo);
}
