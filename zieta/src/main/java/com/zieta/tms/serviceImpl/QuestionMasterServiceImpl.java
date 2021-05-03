package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.QuestionMasterDTO;

import com.zieta.tms.model.QuestionMaster;


import com.zieta.tms.repository.QuestionMasterRepository; //
import com.zieta.tms.repository.ClientInfoRepository;

import com.zieta.tms.service.QuestionMasterService;
import com.zieta.tms.request.QuestionMasterEditRequest;

import lombok.extern.slf4j.Slf4j;
import com.zieta.tms.util.TSMUtil;

@Service
@Transactional
@Slf4j
public class QuestionMasterServiceImpl implements QuestionMasterService {

	@Autowired
	QuestionMasterRepository questionMasterRepository;
	
	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<QuestionMasterDTO> getAllQuestionMaster() {
		List<QuestionMaster> questionInfos= questionMasterRepository.findAll();
		List<QuestionMasterDTO> questionInfoDTOs = new ArrayList<QuestionMasterDTO>();
		QuestionMasterDTO questionInfoDTO = null;
		for (QuestionMaster questionInfo : questionInfos) {
			questionInfoDTO = modelMapper.map(questionInfo, QuestionMasterDTO.class);
			questionInfoDTOs.add(questionInfoDTO);
		}
		return questionInfoDTOs;
}
	
	
	
	public QuestionMasterDTO findByQuestionMasterId(long id) {
		QuestionMasterDTO questionMasterDTO = null;
		QuestionMaster questionMaster = questionMasterRepository.findById(id).get();
		if(questionMaster !=null) {
			questionMasterDTO =  modelMapper.map(questionMaster, QuestionMasterDTO.class);		
			 
			//questionMasterDTO.setQuestion(questionMasterRepository.findById(questionMaster.getQuestionId()).get().getQuestion());
			//questionMasterDTO.setClientName(clientInfoRepository.findById(questionMaster.getClientId()).get().getClientName());
			 
			 
		}
		return questionMasterDTO;
	}
	
	
	
public void editQuestionMasterById(@Valid QuestionMasterEditRequest questionMasterEditRequest) throws Exception {
		
		Optional<QuestionMaster> questionMasterEntity = questionMasterRepository.findById(questionMasterEditRequest.getId());
		if(questionMasterEntity.isPresent()) {
			
			QuestionMaster questionMaster = questionMasterEntity.get();			
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(questionMasterEditRequest,questionMaster);
			 questionMasterRepository.save(questionMaster);
			
			
		}else {
			throw new Exception("Question  Master not found with the provided ID : "+questionMasterEditRequest.getId());
		}
		
	}
	
	
	public void deleteQuestionMasterById(Long id, String modifiedBy) throws Exception {
		Optional<QuestionMaster> questionMaster = questionMasterRepository.findById(id);
			if (questionMaster.isPresent()) {
				QuestionMaster questionMasterEntitiy = questionMaster.get();
				short delete = 1;
				questionMasterEntitiy.setIsDelete(delete);
				questionMasterEntitiy.setModifiedBy(modifiedBy);
				questionMasterRepository.save(questionMasterEntitiy);
			
			}else {
				log.info("No Question  Master  found with the provided ID{} in the DB",id);
				throw new Exception("No QuestionMaster found with the provided ID in the DB :"+id);
			}


		}
	
}
