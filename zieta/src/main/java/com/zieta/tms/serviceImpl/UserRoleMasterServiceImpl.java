package com.zieta.tms.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.UserRoleMasterDTO;
import com.zieta.tms.model.UserRoleMaster;
import com.zieta.tms.repository.UserRoleMasterRepository;
import com.zieta.tms.service.UserRoleMasterService;

@Service
public  class UserRoleMasterServiceImpl implements UserRoleMasterService {

	private static final Logger log = LoggerFactory.getLogger(UserRoleMasterServiceImpl.class);

	@Autowired
	UserRoleMasterRepository userRoleMasterRepository;

	@Autowired
	ModelMapper modelMapper;

	
	@Override
	public void addUserRoleMaster(@Valid UserRoleMaster userRoleMaster) {

		userRoleMasterRepository.save(userRoleMaster);
	}


	@Override
	public List<UserRoleMasterDTO> getAllUserRoleMaster() {
		List<UserRoleMaster> userroleMaster = userRoleMasterRepository.findAll();
		List<UserRoleMasterDTO> userrolemasterDTOs = new ArrayList<UserRoleMasterDTO>();
		UserRoleMasterDTO userRoleMasterDTO = null;
		for (UserRoleMaster userrolemaster : userroleMaster) {
			userRoleMasterDTO =  modelMapper.map(userrolemaster, UserRoleMasterDTO.class);
			userrolemasterDTOs.add(userRoleMasterDTO);
		 }
		return userrolemasterDTOs;
	}


	@Override
	public void deleteUserRoleMasterById(Long id) throws Exception {
		Optional<UserRoleMaster> userRoleMaster= userRoleMasterRepository.findById(id);
		if (userRoleMaster.isPresent()) {
			userRoleMasterRepository.deleteById(id);

		}else {
			log.info("No userRoleMaster found with the provided ID{} in the DB",id);
			throw new Exception("No userRoleMaster found with the provided ID in the DB :"+id);
		}
	}


	@Override
	public void editUserRoleMasterById(@Valid UserRoleMasterDTO userRoleMasterDTO) throws Exception {

		Optional<UserRoleMaster> userRoleEntity = userRoleMasterRepository.findById(userRoleMasterDTO.getId());
		if(userRoleEntity.isPresent()) {
			UserRoleMaster userRoleMaster = modelMapper.map(userRoleMasterDTO, UserRoleMaster.class);
			userRoleMasterRepository.save(userRoleMaster);
			
		}else {
			throw new Exception("userRoleMaster not found with the provided ID : "+userRoleMasterDTO.getId());
		}
		
	}
		
	}

	

