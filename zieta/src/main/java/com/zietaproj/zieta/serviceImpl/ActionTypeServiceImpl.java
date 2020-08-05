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

import com.zietaproj.zieta.dto.ActionTypeDTO;
import com.zietaproj.zieta.dto.StateTypeDTO;
import com.zietaproj.zieta.model.ActionTypeMaster;
import com.zietaproj.zieta.model.StateTypeMaster;
import com.zietaproj.zieta.repository.ActionTypeMasterRepository;
import com.zietaproj.zieta.repository.StateTypeMasterRepository;
import com.zietaproj.zieta.service.ActionTypeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ActionTypeServiceImpl implements ActionTypeService {

	
	@Autowired
	ActionTypeMasterRepository actiontypeMasterRepository;

	@Autowired
	ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionTypeServiceImpl.class);
	
	@Override
public List<ActionTypeDTO> getAllActionTypes() {
		
		List<ActionTypeMaster> actiontypemasters = actiontypeMasterRepository.findAll();
		List<ActionTypeDTO> actiontypemasterDTOs = new ArrayList<ActionTypeDTO>();
		ActionTypeDTO actionTypeDTO = null;
		for (ActionTypeMaster actionmaster : actiontypemasters) {
			actionTypeDTO = modelMapper.map(actionmaster, ActionTypeDTO.class);
			actiontypemasterDTOs.add(actionTypeDTO);
	}
	
		return actiontypemasterDTOs;
}
	@Override
	public void addActionTypes(ActionTypeMaster actionTypemaster) {
		actiontypeMasterRepository.save(actionTypemaster);
	}

     @Override
	public void editActionTypesById(@Valid ActionTypeDTO actionmasterDto) throws Exception {
		
		Optional<ActionTypeMaster> actionMasterEntity = actiontypeMasterRepository.findById(actionmasterDto.getId());
		if(actionMasterEntity.isPresent()) {
			ActionTypeMaster actionMaster = modelMapper.map(actionmasterDto, ActionTypeMaster.class);
			actiontypeMasterRepository.save(actionMaster);
			
		}else {
			throw new Exception("actiontype not found with the provided ID : "+actionmasterDto.getId());
		}
		
	}
	
	@Override
	public void deleteActionTypesById(Long id) throws Exception {
		
		Optional<ActionTypeMaster> actionmaster = actiontypeMasterRepository.findById(id);
		if (actionmaster.isPresent()) {
			actiontypeMasterRepository.deleteById(id);

		}else {
			log.info("No actiontype found with the provided ID{} in the DB",id);
			throw new Exception("No actiontype found with the provided ID in the DB :"+id);
		}
		
		
	}
	
}
