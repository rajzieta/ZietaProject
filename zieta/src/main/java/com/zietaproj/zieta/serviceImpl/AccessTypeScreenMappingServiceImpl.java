package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.AccessTypeScreenMappingDTO;
import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.dto.OrgInfoDTO;
import com.zietaproj.zieta.model.AccessTypeScreenMapping;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.model.OrgInfo;
import com.zietaproj.zieta.repository.AccessTypeMasterRepository;
import com.zietaproj.zieta.repository.AccessTypeScreenMappingRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ScreensMasterRepository;
import com.zietaproj.zieta.service.AccessTypeScreenMappingService;

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
	public List<AccessTypeScreenMappingDTO> getAllAccesstypeScreensMapping() {
		short notDeleted = 0;
		List<AccessTypeScreenMapping> accessTypeScreenMappinginfos= accessTypeScreenMappingRepository.findByIsDelete(notDeleted);
		List<AccessTypeScreenMappingDTO> accessScreenmappingDTOs = new ArrayList<AccessTypeScreenMappingDTO>();
		AccessTypeScreenMappingDTO accessScreenmappingDTO = null;
		for (AccessTypeScreenMapping accessscreens : accessTypeScreenMappinginfos) {
			accessScreenmappingDTO = modelMapper.map(accessscreens, AccessTypeScreenMappingDTO.class);
			accessScreenmappingDTO.setClientDescription(clientInfoRepository.findById(accessscreens.getClientId())
					.get().getClientName());
			accessScreenmappingDTO.setAccessTypeDescription(accessTypeRepository.findById(accessscreens.getAccessTypeId())
					.get().getAccessType());
			accessScreenmappingDTO.setScreenDescription(screensmasterRepository.findById(accessscreens.getScreenId())
					.get().getScreenDesc());
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
	
}
