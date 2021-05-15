package com.zieta.tms.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.zieta.tms.request.QuestionAnswerMasterEditRequest;
import com.zieta.tms.request.QuestionAnswerMasterBulkEditRequest;
import com.zieta.tms.request.QuestionMasterEditRequest;
import com.zieta.tms.dto.QuestionMasterDTO;
import com.zieta.tms.dto.CurrencyMasterDTO;
import com.zieta.tms.service.QuestionMasterService;
import com.zieta.tms.service.QuestionAnswerMasterService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.zieta.tms.dto.QuestionMasterDTO;
import com.zieta.tms.dto.QuestionAnswerMasterDTO;


@RestController
@RequestMapping("/api")
@Api(tags = "Question Answer Master API")
public class QuestionAnswerMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerMasterController.class);

	@Autowired
	QuestionMasterService questionMasterService;
	
	@Autowired
	QuestionAnswerMasterService questionAnswerMasterService; 
	
	@ApiOperation(value = "List Question Master Info", notes = "Table reference:config_question_master")
	@RequestMapping(value = "getAllQuestionMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<QuestionMasterDTO> getAllQuestionMaster() {
		List<QuestionMasterDTO> questionInfos = null;
		
		  try { questionInfos = questionMasterService.getAllQuestionMaster();
		  
		  } catch (Exception e) {
		  LOGGER.error("Error Occured in QuestionMasterController#getAllQuestionMaster", e); }
		 
		return questionInfos;
	}
	
	
	@ApiOperation(value = "List Question Answer Info", notes = "Table reference:config_question_answer,config_question_master")
	@RequestMapping(value = "getAllQuestionAnswerByClientId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<QuestionAnswerMasterDTO> getAllQuestionAnswerMaster(@RequestParam(required = true) Long clientId) {
		List<QuestionAnswerMasterDTO> questionAnswerMasterInfos = null;
		try {
				//questionAnswerMasterInfos = questionAnswerMasterService.getAllQuestionAnswerMasterDetails();
				questionAnswerMasterInfos = questionAnswerMasterService.getAllQuestionAnswerMasterByClientId(clientId);

		} catch (Exception e) {
			LOGGER.error("Error Occured in CurrencyMasterController#getAllCurrencyMaster", e);
		}
		return questionAnswerMasterInfos;		
	}
	
	@ApiOperation(value = "List Question Answer Info with respect to MastreId and ClientId", notes = "Table reference:config_question_answer,config_question_master")
	@RequestMapping(value = "getAllQuestionAnswerByQuestionMasterIdAndClientId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public QuestionAnswerMasterDTO getAllQuestionAnswerByQuestionMasterIdAndClientId(@RequestParam(required = true) Long questionMasterId, @RequestParam(required=true) Long clientId) {
		QuestionAnswerMasterDTO questionAnswerMasterInfos = null;
		try {
				questionAnswerMasterInfos = questionAnswerMasterService.getAllQuestionAnswerMasterByQuestionMasterIdAndClientId(questionMasterId, clientId);

		} catch (Exception e) {
			LOGGER.error("Error Occured in CurrencyMasterController#getAllCurrencyMaster", e);
		}
		return questionAnswerMasterInfos;		
	}
	
	
	@RequestMapping(value = "getQuestionAnswerMasterById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides question answer data based on questionAnswerMasterId",notes="Table reference: config_question_answer,config_question_master")
	public QuestionAnswerMasterDTO getQuestionanswerMasterById(@RequestParam(required = true) Long questionAnswerMasterId) {
		QuestionAnswerMasterDTO questionAnswerMasterDetails = questionAnswerMasterService.findByQuestionAnswerMasterId(questionAnswerMasterId);
		
		return questionAnswerMasterDetails;
	}
	
	
	@RequestMapping(value = "getQuestionMasterById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides question  data based on questionMasterId",notes="Table reference: config_question_master")
	public QuestionMasterDTO getQuestionMasterById(@RequestParam(required = true) Long questionMasterId) {
		QuestionMasterDTO questionMasterDetails = questionMasterService.findByQuestionMasterId(questionMasterId);
		
		return questionMasterDetails;
	}
	
	
	@ApiOperation(value = "Updates the Question Answer Master Information for the provided Id", notes = "Table reference: config_question_answer")
	@RequestMapping(value = "editQuestionAnswerMasterById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editQuestionAnswerMasterById(@Valid @RequestBody QuestionAnswerMasterEditRequest questionAnswerMasterEditRequest) throws Exception {
		questionAnswerMasterService.editQuestionAnswerMasterById(questionAnswerMasterEditRequest);
		
		
	}
	
	
	//BULK QUESTION ANSWER MASTER UPDATE
	@ApiOperation(value = "Bulk Update the Question Answer Master Information for the provided Id", notes = "Table reference: config_question_answer")
	@RequestMapping(value = "editBulkQuestionAnswerMasterById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editBulkQuestionAnswerMasterById(@Valid @RequestBody List<QuestionAnswerMasterBulkEditRequest> questionAnswerMasterBulkEditRequest) throws Exception {
		questionAnswerMasterService.editBulkQuestionAnswerMasterById(questionAnswerMasterBulkEditRequest);
		
		
	}
	
	
	
	
	
	@ApiOperation(value = "Updates the Question  Master Information for the provided Id", notes = "Table reference: config_question_master")
	@RequestMapping(value = "editQuestionMasterById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editQuestionMasterById(@Valid @RequestBody QuestionMasterEditRequest questionMasterEditRequest) throws Exception {
		questionMasterService.editQuestionMasterById(questionMasterEditRequest);
		
		
	}
	
	
	@ApiOperation(value = "Deletes entries from config_question_answer based on Id", notes = "Table reference: config_question_answer")
	@RequestMapping(value = "deleteQuestionAnswerMasterById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteQuestionAnswerMasterById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		questionAnswerMasterService.deleteQuestionAnswerMasterById(id, modifiedBy);
	}
	
	@ApiOperation(value = "Deletes entries from config_question_Master based on Id", notes = "Table reference: config_question_master")
	@RequestMapping(value = "deleteQuestionMasterById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteQuestionMasterById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		questionMasterService.deleteQuestionMasterById(id, modifiedBy);
	}
	
	
}
