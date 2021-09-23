package com.zieta.tms.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.dto.ScreenCategoryMasterDTO;
import com.zieta.tms.dto.VendorAdvanceDTO;
import com.zieta.tms.dto.VendorInvoiceDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.response.ExpenseWFRDetailsForApprover;
import com.zieta.tms.response.VendorAdvanceResponse;
import com.zieta.tms.response.VendorInvoiceResponse;



public interface VendorAdvanceService {

	//public List<ExpenseTemplateDTO> getAllExpenseTemplate();//
	
	public List<VendorAdvanceDTO> getAllActiveVendorAdvance(Long clientId, Short isDelete);
	
	//public List<ExpTemplateStepsDTO> getAllActiveExpenseTemplateSteps(Long expTemplateId, Short isDelete);

	//public VendorAdvance addVendorAdvance(VendorAdvance vendorAdvance) throws Exception;
	public void addVendorAdvance(VendorAdvance vendorAdvance) throws Exception;
	
	public VendorAdvanceDTO findVendorAdvanceById(long id, short isDelete)throws Exception;
	
	public void deleteByVendorAdvanceId(Long id, String modifiedBy) throws Exception;
	
	public List<VendorAdvanceResponse> findVendorAdvanceByUserId(long userId, Date startActiondate, Date endActionDate);
	
	public List<VendorAdvanceDTO> getAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(long userId, Date startActiondate, Date endActionDate);
	
	public List<VendorAdvanceResponse> findAllActiveVendorAdvanceByUserIdAndBetweenSubmitDate(long userId, String  startVendorAdvacneDate, String endtVendorAdvacneDate);

	
}
