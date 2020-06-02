package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.request.ClientInfoRequest;
import com.zietaproj.zieta.service.ClientInfoService;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {

	
	@Autowired
	ClientInfoRepository clientinfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ClientInfoDTO> getAllClients() {
		List<ClientInfo> clientinfos= clientinfoRepository.findAll();
		List<ClientInfoDTO> clientinfoDTOs = new ArrayList<ClientInfoDTO>();
		ClientInfoDTO clientinfoDTO = null;
		for (ClientInfo clientinfo : clientinfos) {
			clientinfoDTO = modelMapper.map(clientinfo, ClientInfoDTO.class);
			clientinfoDTOs.add(clientinfoDTO);
		}
		return clientinfoDTOs;
	}
	
	@Override
	public void addClientInfo(ClientInfoRequest clientInfoParam) {
	
	ClientInfo clientInfo = modelMapper.map(clientInfoParam, ClientInfo.class);
	
		clientinfoRepository.save(clientInfo);
	}
	
	
}
