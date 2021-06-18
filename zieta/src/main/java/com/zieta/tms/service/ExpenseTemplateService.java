package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.request.ExpenseTemplateEditRequest;



public interface ExpenseTemplateService {

	public List<ExpenseTemplateDTO> getAllExpenseTemplate();//
	
	public List<ExpenseTemplateDTO> getAllActiveExpenseTemplate(Long clientId, Short isDelete);
	
	public List<ExpTemplateStepsDTO> getAllActiveExpenseTemplateSteps(Long expTemplateId, Short isDelete);

	public ExpenseTemplate addExpenseTemplate(ExpenseTemplate expTemplate) throws Exception;

	public void editExpenseTemplateById(ExpenseTemplateEditRequest expTemplate) throws Exception;
	
	public List<ExpTemplateSteps> addExpenseTemplateSteps(List<ExpTemplateSteps> expTemplateStepList) throws Exception;
	
}
