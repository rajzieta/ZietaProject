package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.request.ClientInfoAddRequest;
import com.zietaproj.zieta.request.ClientInfoEditRequest;

public interface ClientInfoService {

	
	public List<ClientInfoDTO> getAllClients();

	public void addClientInfo(ClientInfoAddRequest clientinfo);

	public void deleteClientInfoById(Long id, String modifiedBy) throws Exception;

	public void editClientInfo(@Valid ClientInfoEditRequest clientinfoedit) throws Exception;
}
