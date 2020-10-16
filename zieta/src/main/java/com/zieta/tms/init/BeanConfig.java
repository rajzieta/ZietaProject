package com.zieta.tms.init;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.common.base.Strings;
import com.zieta.tms.model.ActionTypeMaster;
import com.zieta.tms.model.StateTypeMaster;
import com.zieta.tms.repository.ActionTypeMasterRepository;
import com.zieta.tms.repository.StateTypeMasterRepository;

@Configuration
public class BeanConfig {
	
	@Autowired
	StateTypeMasterRepository statetypeMasterRepository;
	
	
	@Autowired
	ActionTypeMasterRepository actionTypeMasterRepository;
	
	
	@Bean
	@Qualifier("stateByName")
	public Map<String, Long> getStateByName(){
		
		List<StateTypeMaster> stateTypeMasterList = statetypeMasterRepository.findAll();
		
		return stateTypeMasterList.stream().collect(Collectors.toMap(StateTypeMaster::getStateName, StateTypeMaster::getId));
	}
	
	
	@Bean
	@Qualifier("actionTypeByName")
	public Map<String, Long> getActionTypeByName(){
		
		List<ActionTypeMaster> actionTypeMasterList = actionTypeMasterRepository.findAll();
		return actionTypeMasterList.stream().collect(Collectors.toMap(ActionTypeMaster::getActionName, ActionTypeMaster::getId));
	}
	
	
	@Bean
	@Qualifier("stateById")
	public Map<Long,String> getStateById(){
		
		List<StateTypeMaster> stateTypeMasterList = statetypeMasterRepository.findAll();
		return stateTypeMasterList.stream().collect(Collectors.toMap(StateTypeMaster::getId, StateTypeMaster::getStateName));
	}
	
	
	@Bean
	@Qualifier("actionTypeById")
	public Map<Long, String> getActionTypeById(){
		
		List<ActionTypeMaster> actionTypeMasterList = actionTypeMasterRepository.findAll();
		return actionTypeMasterList.stream().collect(Collectors.toMap(ActionTypeMaster::getId, ActionTypeMaster::getActionName));
	}

	@Bean
	@Profile("!dev")
	public AmazonS3Client amazonS3Client() {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withForceGlobalBucketAccessEnabled(true)
				.withRegion(Strings.isNullOrEmpty("ap-south-1") ? Regions.DEFAULT_REGION : Regions.fromName("ap-south-1")).build();
		return (AmazonS3Client) s3Client;
	}

}
