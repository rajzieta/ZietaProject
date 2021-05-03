package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.QuestionAnswerMasterDTO;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.request.QuestionAnswerMasterEditRequest;

public interface QuestionAnswerMasterService {

	public List<QuestionAnswerMasterDTO> getAllQuestionAnswerMasterDetails();//getAllQuestionAnswerMasterDetails
	public List<QuestionAnswerMasterDTO> getAllQuestionAnswerMasterByClientId(long clientId);
	public QuestionAnswerMasterDTO findByQuestionAnswerMasterId(long questionanswerMasterId);
	public void editQuestionAnswerMasterById(@Valid QuestionAnswerMasterEditRequest questionAnswerMasterEditRequest) throws Exception;
	public void deleteQuestionAnswerMasterById(Long id, String modifiedBy) throws Exception;
	
}
