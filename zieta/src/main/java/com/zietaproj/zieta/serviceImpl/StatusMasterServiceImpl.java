package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.service.StatusMasterService;

@Service
public class StatusMasterServiceImpl implements StatusMasterService {
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Override
	public List<StatusMasterDTO> getAllStatus() {
		List<StatusMaster> statusMasters= statusMasterRepository.findAll();
		List<StatusMasterDTO> statusMasterDTOs = new ArrayList<StatusMasterDTO>();
		StatusMasterDTO statusMasterDTO = null;
		for (StatusMaster statusMaster : statusMasters) {
			statusMasterDTO = new StatusMasterDTO();
			statusMasterDTO.setId(statusMaster.getId());
			statusMasterDTO.setStatus(statusMaster.getStatus());
			statusMasterDTO.setStatus_type(statusMaster.getStatus_type());
			statusMasterDTO.setCreated_by(statusMaster.getCreated_by());
			statusMasterDTO.setCreated_date(statusMaster.getCreated_date());
			statusMasterDTO.setModified_by(statusMaster.getModified_by());
			statusMasterDTO.setModified_date(statusMaster.getModified_date());
			
			statusMasterDTOs.add(statusMasterDTO);
		}
		return statusMasterDTOs;
	}
	
	
	  @Override 
	  public void addStatusmaster(StatusMaster statusmaster) {
	  statusMasterRepository.save(statusmaster); 
	  
	  }
	 
	
	
	

}
