package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.AccessTypeScreenMappingDTO;
import com.zieta.tms.dto.ClientInfoDTO;
import com.zieta.tms.dto.OrgInfoDTO;
import com.zieta.tms.model.AccessTypeScreenMapping;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ScreensMasterRepository;
import com.zieta.tms.request.AccessScreensRequest;
import com.zieta.tms.service.AccessTypeScreenMappingService;
import com.zieta.tms.service.ScreensMasterService;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class AccessTypeScreenMappingServiceImpl implements AccessTypeScreenMappingService {
	
	@Autowired
	AccessTypeScreenMappingRepository accessTypeScreenMappingRepository;
	
	@Autowired
	AccessTypeMasterRepository accessTypeRepository;
	
	@Autowired
	ScreensMasterRepository screensmasterRepository;

	@Autowired
	ScreensMasterService screensMasterService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	
	@Override
	public void assignScreenToAccessType(AccessTypeScreenMapping accessTypeScreenMapping) {
		
		try {
			accessTypeScreenMappingRepository.save(accessTypeScreenMapping);
		} catch (Exception e) {
			log.error("Exception occured in saving the entity",e);
		}
	}

	
	@Override
	public void assignMultipleScreensToAccessType(AccessScreensRequest accessScreensRequest) {
		
		List<AccessTypeScreenMapping> accessTypeScreenMappingList = new ArrayList<AccessTypeScreenMapping>();
		AccessTypeScreenMapping accessTypeScreenMapping = null;
		List<Long> screenIds = accessScreensRequest.getScreenIds();
		for (Long screenId : screenIds) {
			accessTypeScreenMapping = new AccessTypeScreenMapping();
			accessTypeScreenMapping.setClientId(accessScreensRequest.getClientId());
			accessTypeScreenMapping.setAccessTypeId(accessScreensRequest.getAccessTypeId());
			accessTypeScreenMapping.setScreenId(screenId);
			accessTypeScreenMappingList.add(accessTypeScreenMapping);
		}
		accessTypeScreenMappingRepository.saveAll(accessTypeScreenMappingList);
		
	}
	
	@Override
	public List<AccessTypeScreenMappingDTO> getAllAccesstypeScreensMapping() {
		short notDeleted = 0;
		//AccessTypeScreenMappingDTO accessScreenmappingDTO = null;
		List<AccessTypeScreenMapping> accessTypeScreenMappinginfos= accessTypeScreenMappingRepository.findByIsDelete(notDeleted);
		//List<AccessTypeScreenMapping> accessControlConfigList = accessTypeScreenMappingRepository.findByClientIdANDAccessTypeIdANDIsDelete(accessScreenmappingDTO.getClientId(), accessScreenmappingDTO.getAccessTypeId(), notDeleted );
		List<AccessTypeScreenMappingDTO> accessScreenmappingDTOs = new ArrayList<AccessTypeScreenMappingDTO>();
		AccessTypeScreenMappingDTO accessScreenmappingDTO = null;
		for (AccessTypeScreenMapping accessscreens : accessTypeScreenMappinginfos) {
			accessScreenmappingDTO = new AccessTypeScreenMappingDTO();
			
				
				accessScreenmappingDTO.setId(accessscreens.getId());
				accessScreenmappingDTO.setClientId(accessscreens.getClientId());
				accessScreenmappingDTO.setAccessTypeId(accessscreens.getAccessTypeId());
				accessScreenmappingDTO.setScreenId(accessscreens.getScreenId());
			//accessScreenmappingDTO = modelMapper.map(accessscreens, AccessTypeScreenMappingDTO.class);

				accessScreenmappingDTO.setClientCode(clientInfoRepository.findById(accessscreens.getClientId())
						.get().getClientCode());
				accessScreenmappingDTO.setClientDescription(clientInfoRepository.findById(accessscreens.getClientId())
               .get().getClientName());
				accessScreenmappingDTO.setClientStatus(clientInfoRepository.findById(accessscreens.getClientId())
			               .get().getClientStatus());
				
			accessScreenmappingDTO.setAccessTypeDescription(accessTypeRepository.findById(accessscreens.getAccessTypeId())
					.get().getAccessType());
//			accessScreenmappingDTO.setScreenDescription(screensmasterRepository.findById(accessscreens.getScreenId())
//					.get().getScreenDesc());
			//List<Long> accessControlConfigList = accessTypeScreenMappingRepository.
			//		findByIdANDClientId(((AccessTypeScreenMapping) accessTypeScreenMappinginfos).getId(), ((AccessTypeScreenMapping) accessTypeScreenMappinginfos).getClientId());
			 List<Long> accessControlConfigList = accessTypeScreenMappingRepository.findByClientIdANDAccessTypeId(accessScreenmappingDTO.getClientId(), accessScreenmappingDTO.getAccessTypeId());
				
				List<ScreensMaster> screensListByClientId = screensMasterService.getScreensByIds(accessControlConfigList);
			accessScreenmappingDTO.setScreensByClient(screensListByClientId);
			accessScreenmappingDTOs.add(accessScreenmappingDTO);
		}
		return accessScreenmappingDTOs;
	}
	
	
	
	@Override
	public List<AccessTypeScreenMappingDTO> getAccessTypeScreenMappingByClient(Long clientId) {
		short notDeleted = 0;
		List<AccessTypeScreenMapping> accessTypeScreenMappinginfos= accessTypeScreenMappingRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		//List<AccessTypeScreenMapping> accessControlConfigList = accessTypeScreenMappingRepository.findByClientIdANDAccessTypeIdANDIsDelete(accessScreenmappingDTO.getClientId(), accessScreenmappingDTO.getAccessTypeId(), notDeleted );
		List<AccessTypeScreenMappingDTO> accessScreenmappingDTOs = new ArrayList<AccessTypeScreenMappingDTO>();
		AccessTypeScreenMappingDTO accessScreenmappingDTO = null;
		for (AccessTypeScreenMapping accessscreens : accessTypeScreenMappinginfos) {
			accessScreenmappingDTO = new AccessTypeScreenMappingDTO();
			
				
				accessScreenmappingDTO.setId(accessscreens.getId());
				accessScreenmappingDTO.setClientId(accessscreens.getClientId());
				accessScreenmappingDTO.setAccessTypeId(accessscreens.getAccessTypeId());
				accessScreenmappingDTO.setScreenId(accessscreens.getScreenId());

				accessScreenmappingDTO.setClientCode(clientInfoRepository.findById(accessscreens.getClientId())
						.get().getClientCode());
				accessScreenmappingDTO.setClientDescription(clientInfoRepository.findById(accessscreens.getClientId())
               .get().getClientName());
			accessScreenmappingDTO.setAccessTypeDescription(accessTypeRepository.findById(accessscreens.getAccessTypeId())
					.get().getAccessType());

			 List<Long> accessControlConfigList = accessTypeScreenMappingRepository.findByClientIdANDAccessTypeId(accessScreenmappingDTO.getClientId(), accessScreenmappingDTO.getAccessTypeId());
				
				List<ScreensMaster> screensListByClientId = screensMasterService.getScreensByIds(accessControlConfigList);
			accessScreenmappingDTO.setScreensByClient(screensListByClientId);
			accessScreenmappingDTOs.add(accessScreenmappingDTO);
		}
		return accessScreenmappingDTOs;
	}
	
	
	@Override
	public void editAccessScreenMapping(AccessTypeScreenMappingDTO accessScreenmapdto) throws Exception {
		
		Optional<AccessTypeScreenMapping> accessscreenmapEntity = accessTypeScreenMappingRepository.findById(accessScreenmapdto.getId());
		if(accessscreenmapEntity.isPresent()) {
			AccessTypeScreenMapping accessScreenInfos = modelMapper.map(accessScreenmapdto, AccessTypeScreenMapping.class);
			accessTypeScreenMappingRepository.save(accessScreenInfos);
			
		}else {
			throw new Exception("AccessTypeScreenMapping not found with the provided ID : "+accessScreenmapdto.getId());
		}
		
		
	}
	
	@Override
	public void deleteAccessScreensMappingById(Long id, String modifiedBy) throws Exception {
		
		Optional<AccessTypeScreenMapping> accessScreensinfo = accessTypeScreenMappingRepository.findById(id);
		if (accessScreensinfo.isPresent()) {
			AccessTypeScreenMapping accessScreensinfoEntity = accessScreensinfo.get();
			short delete = 1;
			accessScreensinfoEntity.setIsDelete(delete);
			accessScreensinfoEntity.setModifiedBy(modifiedBy);
			accessTypeScreenMappingRepository.save(accessScreensinfoEntity);

		}else {
			log.info("No AccessTypeScreenMapping found with the provided ID{} in the DB",id);
			throw new Exception("No AccessTypeScreenMapping found with the provided ID in the DB :"+id);
		}
		
		
	}


	@Override
	@Transactional
	public void  deleteAccessTypeAndScreens(Long clientId, Long accessTypeId) {
		 accessTypeScreenMappingRepository.deleteAccessTypeAndScreens(clientId, accessTypeId);
	}


}
