package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ClientInfoDTO;
import com.zieta.tms.request.ClientInfoAddRequest;
import com.zieta.tms.request.ClientInfoEditRequest;

public interface ClientInfoService {

	
	public List<ClientInfoDTO> getAllClients();

	public void addClientInfo(ClientInfoAddRequest clientinfo);

	public void deleteClientInfoById(Long id, String modifiedBy) throws Exception;

	public void editClientInfo(@Valid ClientInfoEditRequest clientinfoedit) throws Exception;
}
