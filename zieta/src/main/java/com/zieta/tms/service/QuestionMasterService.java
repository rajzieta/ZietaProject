package com.zieta.tms.service;

import java.util.List;
import com.zieta.tms.request.QuestionMasterEditRequest;
import javax.validation.Valid;
import com.zieta.tms.dto.QuestionMasterDTO;
//import com.zieta.tms.dto.CurrencyMasterDTO;


public interface QuestionMasterService {

	 public List<QuestionMasterDTO> getAllQuestionMaster();
	 public QuestionMasterDTO findByQuestionMasterId(long questionMasterId);
	 public void editQuestionMasterById(@Valid QuestionMasterEditRequest questionMasterEditRequest) throws Exception;
	 
	 public void deleteQuestionMasterById(Long id, String modifiedBy) throws Exception;
	 
	 //public List<CurrencyMasterDTO> getAllCurrencyMaster();

}
