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

import com.zieta.tms.common.MessagesConstants;
import com.zieta.tms.dto.QuestionAnswerMasterDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.QuestionAnswerMaster;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.QuestionAnswerMasterRepository;
import com.zieta.tms.repository.QuestionMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.service.QuestionAnswerMasterService;
import com.zieta.tms.request.QuestionAnswerMasterBulkEditRequest;
import com.zieta.tms.request.QuestionAnswerMasterEditRequest;

import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionAnswerMasterServiceImpl implements QuestionAnswerMasterService {	

	
	@Autowired
	QuestionMasterRepository questionMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	QuestionAnswerMasterService questionAnswerMasterService;
	
	@Autowired
	QuestionAnswerMasterRepository questionAnswerMasterRepository;
	

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<QuestionAnswerMasterDTO> getAllQuestionAnswerMasterDetails() {
		short notDeleted = 0;
		List<QuestionAnswerMaster> questionAnswerMasterList= questionAnswerMasterRepository.findByIsDelete(notDeleted);
		List<QuestionAnswerMasterDTO> questionAnswerMasterDTOs = new ArrayList<QuestionAnswerMasterDTO>();
		mapQuestionAnswerMasterModelToDTO(questionAnswerMasterList, questionAnswerMasterDTOs);
		
		return questionAnswerMasterDTOs;		
	}
	//GET QUESTION ANSWER DATA BY CLIENT ID
	@Override
	public List<QuestionAnswerMasterDTO> getAllQuestionAnswerMasterByClientId(long clientId) {
		short notDeleted = 0;
		List<QuestionAnswerMaster> questionAnswerMasterList= questionAnswerMasterRepository.findByClientId(clientId);
		List<QuestionAnswerMasterDTO> questionAnswerMasterDTOs = new ArrayList<QuestionAnswerMasterDTO>();
		mapQuestionAnswerMasterModelToDTO(questionAnswerMasterList, questionAnswerMasterDTOs);
		
		return questionAnswerMasterDTOs;		
	}

	private void mapQuestionAnswerMasterModelToDTO(List<QuestionAnswerMaster> questionAnswerMasterList, List<QuestionAnswerMasterDTO> questionAnswerMasterDTOs) {
		QuestionAnswerMasterDTO questionAnswerMasterDTO = null;
		for (QuestionAnswerMaster questionAnswerMaster : questionAnswerMasterList) {
			questionAnswerMasterDTO =  modelMapper.map(questionAnswerMaster, QuestionAnswerMasterDTO.class);
			
			questionAnswerMasterDTO.setQuestion(questionMasterRepository.findById(questionAnswerMaster.getQuestionId()).get().getQuestion());
			questionAnswerMasterDTO.setClientName(clientInfoRepository.findById(questionAnswerMaster.getClientId()).get().getClientName());
						
			questionAnswerMasterDTOs.add(questionAnswerMasterDTO);
		}
	}
	
	
	
	
	
	//@Override
	public QuestionAnswerMasterDTO findByQuestionAnswerMasterId(long id) {
		QuestionAnswerMasterDTO questionAnswerMasterDTO = null;
		QuestionAnswerMaster questionAnswerMaster = questionAnswerMasterRepository.findById(id).get();
		if(questionAnswerMaster !=null) {
			questionAnswerMasterDTO =  modelMapper.map(questionAnswerMaster, QuestionAnswerMasterDTO.class);
			questionAnswerMasterDTO.setQuestion(questionMasterRepository.findById(questionAnswerMaster.getQuestionId()).get().getQuestion());
			questionAnswerMasterDTO.setClientName(clientInfoRepository.findById(questionAnswerMaster.getClientId()).get().getClientName());
			 
			 
		}
		return questionAnswerMasterDTO;
	}
	
	
	
public void editQuestionAnswerMasterById(@Valid QuestionAnswerMasterEditRequest questionAnswerMasterEditRequest) throws Exception {
		
		Optional<QuestionAnswerMaster> questionAnswerMasterEntity = questionAnswerMasterRepository.findById(questionAnswerMasterEditRequest.getId());
		if(questionAnswerMasterEntity.isPresent()) {
			
			QuestionAnswerMaster questionAnswerMaster = questionAnswerMasterEntity.get();
			
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(questionAnswerMasterEditRequest,questionAnswerMaster );
			 questionAnswerMasterRepository.save(questionAnswerMaster);
			
			
		}else {
			throw new Exception("Question Answer Master not found with the provided ID : "+questionAnswerMasterEditRequest.getId());
		}
		
	}

//Bulk edit question answer master

public void editBulkQuestionAnswerMasterById(List<QuestionAnswerMasterBulkEditRequest> questionAnswerMasterList) throws Exception {
	
	
	try {
		for (QuestionAnswerMasterBulkEditRequest questionAnswerMasterEditRequest : questionAnswerMasterList) {
		
			Optional<QuestionAnswerMaster> questionAnswerMasterEntity = questionAnswerMasterRepository.findById(questionAnswerMasterEditRequest.getId());
			if(questionAnswerMasterEntity.isPresent()) {
				
				QuestionAnswerMaster questionAnswerMaster = questionAnswerMasterEntity.get();
				
				 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
				 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
				 modelMapper.map(questionAnswerMasterEditRequest,questionAnswerMaster );
				 questionAnswerMasterRepository.save(questionAnswerMaster);
				
				
			}else {
				throw new Exception("Question Answer Master not found with the provided ID : "+questionAnswerMasterEditRequest.getId());
			}
		
		
		}
	}catch(Exception e) {
		log.info("Error in bulk question answer master edit "+e);
	}
	
	
	
}

	
public void deleteQuestionAnswerMasterById(Long id, String modifiedBy) throws Exception {
Optional<QuestionAnswerMaster> questionAnswerMaster = questionAnswerMasterRepository.findById(id);
	if (questionAnswerMaster.isPresent()) {
		QuestionAnswerMaster questionAnswerMasterEntitiy = questionAnswerMaster.get();
		short delete = 1;
		questionAnswerMasterEntitiy.setIsDelete(delete);
		questionAnswerMasterEntitiy.setModifiedBy(modifiedBy);
		questionAnswerMasterRepository.save(questionAnswerMasterEntitiy);
	
	}else {
		log.info("No Question Answer Master  found with the provided ID{} in the DB",id);
		throw new Exception("No QuestionAnswerMaster found with the provided ID in the DB :"+id);
	}


}

	

	
	
}
