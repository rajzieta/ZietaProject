package com.zietaproj.zieta.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.AccessTypeScreenMapping;
import com.zietaproj.zieta.repository.AccessTypeScreenMappingRepository;
import com.zietaproj.zieta.service.AccessTypeScreenMappingService;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class AccessTypeScreenMappingServiceImpl implements AccessTypeScreenMappingService {
	
	@Autowired
	AccessTypeScreenMappingRepository accessTypeScreenMappingRepository;

	@Override
	public void assignScreenToAccessType(AccessTypeScreenMapping accessTypeScreenMapping) {
		
		try {
			accessTypeScreenMappingRepository.save(accessTypeScreenMapping);
		} catch (Exception e) {
			log.error("Exception occured in saving the entity",e);
		}
	}

}
