package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.request.ClientInfoAddRequest;
import com.zietaproj.zieta.service.ClientInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientInfoServiceImpl implements ClientInfoService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientInfoServiceImpl.class);
	
	@Autowired
	ClientInfoRepository clientinfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ClientInfoDTO> getAllClients() {
		short notDeleted = 0;
		List<ClientInfo> clientinfos= clientinfoRepository.findByIsDelete(notDeleted);
		List<ClientInfoDTO> clientinfoDTOs = new ArrayList<ClientInfoDTO>();
		ClientInfoDTO clientinfoDTO = null;
		for (ClientInfo clientinfo : clientinfos) {
			clientinfoDTO = modelMapper.map(clientinfo, ClientInfoDTO.class);
			clientinfoDTOs.add(clientinfoDTO);
		}
		return clientinfoDTOs;
	}
	
	@Override
	public void addClientInfo(ClientInfoAddRequest clientInfoParam) {
	
	ClientInfo clientInfo = modelMapper.map(clientInfoParam, ClientInfo.class);
	
		clientinfoRepository.save(clientInfo);
	}
	
	public void deleteClientInfoById(Long id, String modifiedBy) throws Exception {
		
		Optional<ClientInfo> clientinfo = clientinfoRepository.findById(id);
		if (clientinfo.isPresent()) {
			ClientInfo clientinfoEntity = clientinfo.get();
			short delete = 1;
			clientinfoEntity.setIsDelete(delete);
			clientinfoEntity.setModifiedBy(modifiedBy);
			clientinfoRepository.save(clientinfoEntity);

		}else {
			log.info("No ClientInformation found with the provided ID{} in the DB",id);
			throw new Exception("No ClientInformation found with the provided ID in the DB :"+id);
		}
	}
}
