package com.zieta.tms.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.zieta.tms.dto.UserConfigDTO;
import com.zieta.tms.dto.VendorAdvanceDTO;
import com.zieta.tms.model.UserConfig;
import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.request.UserConfigEditRequest;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.response.VendorAdvanceResponse;




public interface UserConfigService {

	//public List<ExpenseTemplateDTO> getAllExpenseTemplate();//
	
	public List<UserConfigDTO> getAllActiveUserConfig(Short isDelete);
	
	public ResponseMessage editUserConfigByUserId(@Valid @RequestBody UserConfigEditRequest UserConfigEditRequest) throws Exception;
	//public List<ExpTemplateStepsDTO> getAllActiveExpenseTemplateSteps(Long expTemplateId, Short isDelete);

	//public VendorAdvance addVendorAdvance(VendorAdvance vendorAdvance) throws Exception;
	public void addUserConfig(UserConfig userConfig) throws Exception;
	
	public UserConfigDTO findUserConfigById(long id, short isDelete)throws Exception;
	
	public void deleteByUserConfigId(Long id, String modifiedBy) throws Exception;
	
	//public List<VendorAdvanceResponse> findVendorAdvanceByUserId(long userId, Date startActiondate, Date endActionDate);
	
	public List<UserConfigDTO> getAllActiveUserConfigeByUserId(long userId);
	
	//public List<VendorAdvanceResponse> findAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(long userId, String  startVendorAdvacneDate, String endtVendorAdvacneDate);

	
}
