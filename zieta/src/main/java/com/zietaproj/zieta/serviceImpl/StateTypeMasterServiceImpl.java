package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.StateTypeDTO;
import com.zietaproj.zieta.model.StateTypeMaster;
import com.zietaproj.zieta.repository.StateTypeMasterRepository;
import com.zietaproj.zieta.service.StateTypeService;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class StateTypeMasterServiceImpl implements StateTypeService {

	@Autowired
	StateTypeMasterRepository statetypeMasterRepository;

	@Autowired
	ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StateTypeMasterServiceImpl.class);
	
	@Override
public List<StateTypeDTO> getAllStateTypes() {
		
		List<StateTypeMaster> statetypemasters = statetypeMasterRepository.findAll();
		List<StateTypeDTO> statetypemasterDTOs = new ArrayList<StateTypeDTO>();
		StateTypeDTO stateTypeDTO = null;
		for (StateTypeMaster statemaster : statetypemasters) {
			stateTypeDTO = modelMapper.map(statemaster, StateTypeDTO.class);
			statetypemasterDTOs.add(stateTypeDTO);
	}
	
		return statetypemasterDTOs;
}
	@Override
	public void addStateTypes(StateTypeMaster stateTypemaster) {
		statetypeMasterRepository.save(stateTypemaster);
	}

     @Override
	public void editStateTypesById(@Valid StateTypeDTO statemasterDto) throws Exception {
		
		Optional<StateTypeMaster> stateMasterEntity = statetypeMasterRepository.findById(statemasterDto.getId());
		if(stateMasterEntity.isPresent()) {
			StateTypeMaster stateMaster = modelMapper.map(statemasterDto, StateTypeMaster.class);
			statetypeMasterRepository.save(stateMaster);
			
		}else {
			throw new Exception("statetype not found with the provided ID : "+statemasterDto.getId());
		}
		
	}
	
	@Override
	public void deleteStateTypesById(Long id) throws Exception {
		
		Optional<StateTypeMaster> statemaster = statetypeMasterRepository.findById(id);
		if (statemaster.isPresent()) {
			statetypeMasterRepository.deleteById(id);

		}else {
			log.info("No statetype found with the provided ID{} in the DB",id);
			throw new Exception("No statetype found with the provided ID in the DB :"+id);
		}
		
		
	}
	@Override
	public StateTypeMaster getStateByName(String stateName) {
		// TODO Auto-generated method stub
		return statetypeMasterRepository.findByStateName(stateName);
	}
	
	
}
