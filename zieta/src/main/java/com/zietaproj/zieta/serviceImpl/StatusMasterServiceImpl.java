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
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.request.AcitivityRequest;
import com.zietaproj.zieta.request.StatusByClientTypeRequest;

import com.zietaproj.zieta.response.StatusByClienttypeResponse;
import com.zietaproj.zieta.service.StatusMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class StatusMasterServiceImpl implements StatusMasterService {
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<StatusMasterDTO> getAllStatus() {
		short notDeleted=0;
		List<StatusMaster> statusMasters= statusMasterRepository.findByIsDelete(notDeleted);
		List<StatusMasterDTO> statusMasterDTOs = new ArrayList<StatusMasterDTO>();
		StatusMasterDTO statusMasterDTO = null;
		for (StatusMaster statusMaster : statusMasters) {
			statusMasterDTO = modelMapper.map(statusMaster,StatusMasterDTO.class);
			statusMasterDTO.setClientCode(clientInfoRepository.findById(statusMaster.getClientId()).get().getClientCode());
			statusMasterDTO.setClientDescription(clientInfoRepository.findById(statusMaster.getClientId()).get().getClientName());
			statusMasterDTO.setClientStatus(clientInfoRepository.findById(statusMaster.getClientId()).get().getClientStatus());

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
		  short notDeleted=0;
			List<StatusMaster> statusList = statusMasterRepository.findByClientIdAndStatusTypeAndIsDelete(clientId, statusType, notDeleted);
			
			List<StatusByClienttypeResponse> statusByClientStatustypeList = new ArrayList<>();
			
			for(StatusMaster statusmaster: statusList) {
				StatusByClienttypeResponse statusbyclienttypeList = new StatusByClienttypeResponse();
				
				statusbyclienttypeList.setId(statusmaster.getId());
				statusbyclienttypeList.setClientId(statusmaster.getClientId());
				statusbyclienttypeList.setStatusCode(statusmaster.getStatusCode());
				statusbyclienttypeList.setStatusDesc(statusmaster.getStatusDesc());
				statusbyclienttypeList.setStatusType(statusmaster.getStatusType());
				statusbyclienttypeList.setCreatedBy(statusmaster.getCreatedBy());
				statusbyclienttypeList.setCreatedDate(statusmaster.getCreatedDate());
				statusbyclienttypeList.setModifiedBy(statusmaster.getModifiedBy());
				statusbyclienttypeList.setModifiedDate(statusmaster.getModifiedDate());
				statusbyclienttypeList.setIsDelete(statusmaster.getIsDelete());
				statusbyclienttypeList.setIsDefault(statusmaster.getIsDefault());
				statusbyclienttypeList.setClientCode(clientInfoRepository.findById(statusmaster.getClientId()).get().getClientCode());
				statusbyclienttypeList.setClientDescription(clientInfoRepository.findById(statusmaster.getClientId()).get().getClientName());

				
				statusByClientStatustypeList.add(statusbyclienttypeList);
			}
			
			return statusByClientStatustypeList;

}

	  @Override
		public void editStatusByClientStatusType(StatusByClientTypeRequest statusbyclienttypeRequest) throws Exception {  
		  StatusMaster statusmaster = modelMapper.map(statusbyclienttypeRequest, StatusMaster.class);
			statusMasterRepository.save(statusmaster);	
	  
	  }
	  
	  
	  public void deleteStatusById(Long id, String modifiedBy) throws Exception {
			
			Optional<StatusMaster> statusmaster = statusMasterRepository.findById(id);
			if (statusmaster.isPresent()) {
				StatusMaster statusmasterEntitiy = statusmaster.get();
				short delete = 1;
				statusmasterEntitiy.setIsDelete(delete);
				statusmasterEntitiy.setModifiedBy(modifiedBy);
				statusMasterRepository.save(statusmasterEntitiy);

			}else {
				log.info("No Status found with the provided ID{} in the DB",id);
				throw new Exception("No Status found with the provided ID in the DB :"+id);
			}
			
			
		}
	  
	  
	  @Override
			public List<StatusByClienttypeResponse> findByClientId(Long clientId) {
			  short notDeleted=0;
				List<StatusMaster> statusList = statusMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
				
				List<StatusByClienttypeResponse> statusByClientStatustypeList = new ArrayList<>();
				
				for(StatusMaster statusmaster: statusList) {
					StatusByClienttypeResponse statusbyclienttypeList = new StatusByClienttypeResponse();
					
					statusbyclienttypeList.setId(statusmaster.getId());
					statusbyclienttypeList.setClientId(statusmaster.getClientId());
					statusbyclienttypeList.setStatusCode(statusmaster.getStatusCode());
					statusbyclienttypeList.setStatusDesc(statusmaster.getStatusDesc());
					statusbyclienttypeList.setStatusType(statusmaster.getStatusType());
					statusbyclienttypeList.setCreatedBy(statusmaster.getCreatedBy());
					statusbyclienttypeList.setCreatedDate(statusmaster.getCreatedDate());
					statusbyclienttypeList.setModifiedBy(statusmaster.getModifiedBy());
					statusbyclienttypeList.setModifiedDate(statusmaster.getModifiedDate());
					statusbyclienttypeList.setIsDelete(statusmaster.getIsDelete());
					statusbyclienttypeList.setIsDefault(statusmaster.getIsDefault());
					statusbyclienttypeList.setClientCode(clientInfoRepository.findById(statusmaster.getClientId()).get().getClientCode());
					statusbyclienttypeList.setClientDescription(clientInfoRepository.findById(statusmaster.getClientId()).get().getClientName());

					
					statusByClientStatustypeList.add(statusbyclienttypeList);
				}
				
				return statusByClientStatustypeList;

	}
		
	  
}