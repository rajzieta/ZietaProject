package com.zietaproj.zieta.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.UserAccessType;
import com.zietaproj.zieta.repository.UserAccessMappingRepository;
import com.zietaproj.zieta.service.UserAccessTypeService;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UserAccessTypeServiceImpl implements UserAccessTypeService {

	@Autowired
	UserAccessMappingRepository  userAccessMappingRepository;

	@Override
	public List<UserAccessType> findByClientIdAndUserId(long clientId, long userId) {
		// TODO Auto-generated method stub
		return userAccessMappingRepository.findByClientIdAndUserId(clientId, userId);
	}
	
	
	public List<UserAccessType> findAll(){
		return userAccessMappingRepository.findAll();
	}


	@Override
	public void assignAccessTypeToUser(UserAccessType userAccessType) {
		try {
			userAccessMappingRepository.save(userAccessType);
		} catch (Exception e) {
			log.error("Exception occured during saving the entity: ",e);
		}
	}
	
	

	
}
