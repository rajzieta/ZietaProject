package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.service.ClientInfoService;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {

	
	@Autowired
	ClientInfoRepository clientinfoRepository;
	
	@Override
	public List<ClientInfoDTO> getAllInfo() {
		List<ClientInfo> clientinfos= clientinfoRepository.findAll();
		List<ClientInfoDTO> clientinfoDTOs = new ArrayList<ClientInfoDTO>();
		ClientInfoDTO clientinfoDTO = null;
		for (ClientInfo clientinfo : clientinfos) {
			clientinfoDTO = new ClientInfoDTO();
			clientinfoDTO.setId(clientinfo.getId());
			clientinfoDTO.setClient_code(clientinfo.getClient_code());
			clientinfoDTO.setClient_name(clientinfo.getClient_name());
			clientinfoDTO.setClient_status(clientinfo.getClient_status());
			clientinfoDTO.setCreated_by(clientinfo.getCreated_by());
			clientinfoDTO.setModified_by(clientinfo.getModified_by());
			clientinfoDTOs.add(clientinfoDTO);
		}
		return clientinfoDTOs;
	}
	
	@Override
	public void addClientInfo(ClientInfo clientinfo)
	{
		clientinfoRepository.save(clientinfo);
	}
}
