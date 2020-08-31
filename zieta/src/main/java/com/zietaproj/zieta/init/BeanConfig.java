package com.zietaproj.zieta.init;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zietaproj.zieta.model.ActionTypeMaster;
import com.zietaproj.zieta.model.StateTypeMaster;
import com.zietaproj.zieta.repository.ActionTypeMasterRepository;
import com.zietaproj.zieta.repository.StateTypeMasterRepository;

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

}
