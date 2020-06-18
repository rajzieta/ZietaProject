package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.service.StatusMasterService;

@Service
public class StatusMasterServiceImpl implements StatusMasterService {
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<StatusMasterDTO> getAllStatus() {
		List<StatusMaster> statusMasters= statusMasterRepository.findAll();
		List<StatusMasterDTO> statusMasterDTOs = new ArrayList<StatusMasterDTO>();
		StatusMasterDTO statusMasterDTO = null;
		for (StatusMaster statusMaster : statusMasters) {
			statusMasterDTO = modelMapper.map(statusMaster,StatusMasterDTO.class);
			statusMasterDTO.setClientCode(clientInfoRepository.findById(statusMaster.getClientId()).get().getClient_code());
			statusMasterDTOs.add(statusMasterDTO);
		}
		return statusMasterDTOs;
	}
	
	
	  @Override 
	  public void addStatusmaster(StatusMaster statusmaster) {
	  statusMasterRepository.save(statusmaster); 
	  
	  }
	 
	
	
	

}
