package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.request.AcitivityRequest;
import com.zietaproj.zieta.request.StatusByClientTypeRequest;

import com.zietaproj.zieta.response.StatusByClienttypeResponse;
import com.zietaproj.zieta.service.StatusMasterService;

@Service
@Transactional
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
	 
	
	
	  @Override
		public List<StatusByClienttypeResponse> findByClientIdAndStatusType(Long clientId, String statusType) {
			List<StatusMaster> statusList = statusMasterRepository.findByClientIdAndStatusType(clientId, statusType);
			
			List<StatusByClienttypeResponse> statusByClientStatustypeList = new ArrayList<>();
			
			for(StatusMaster statusmaster: statusList) {
				StatusByClienttypeResponse statusbyclienttypeList = new StatusByClienttypeResponse();
				
				statusbyclienttypeList.setId(statusmaster.getId());
				statusbyclienttypeList.setClientId(statusmaster.getClientId());
				statusbyclienttypeList.setStatus(statusmaster.getStatus());
				statusbyclienttypeList.setStatus_type(statusmaster.getStatusType());
				statusbyclienttypeList.setCreated_by(statusmaster.getCreatedBy());
				statusbyclienttypeList.setCreated_date(statusmaster.getCreatedDate());
				statusbyclienttypeList.setModified_by(statusmaster.getModifiedBy());
				statusbyclienttypeList.setModified_date(statusmaster.getModifiedDate());
				statusbyclienttypeList.setIsDelete(statusmaster.getIsDelete());
				statusbyclienttypeList.setIsDefault(statusmaster.getIsDefault());
				
				statusByClientStatustypeList.add(statusbyclienttypeList);
			}
			
			return statusByClientStatustypeList;

}

	  @Override
		public void editStatusByClientStatusType(StatusByClientTypeRequest statusbyclienttypeRequest) throws Exception {  
		  StatusMaster statusmaster = modelMapper.map(statusbyclienttypeRequest, StatusMaster.class);
			statusMasterRepository.save(statusmaster);	
	  
	  }
	  
	  
}