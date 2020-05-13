package com.zietaproj.zieta.service;

import java.util.List;


import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.model.ClientInfo;

public interface ClientInfoService {

	
	public List<ClientInfoDTO> getAllInfo();

	public void addClientInfo(ClientInfo clientinfo);
}
