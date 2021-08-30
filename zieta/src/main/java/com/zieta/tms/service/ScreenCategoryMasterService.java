package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.dto.ScreenCategoryMasterDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.request.ExpenseTemplateEditRequest;

public interface ScreenCategoryMasterService {
	
	public List<ScreenCategoryMasterDTO> getAllScreenCategoryMaster();
	
	public ScreenCategoryMasterDTO findScreenCategoryMasterById(long id);
	
}
