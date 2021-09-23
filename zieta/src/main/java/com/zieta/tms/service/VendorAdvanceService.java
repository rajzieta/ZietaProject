package com.zieta.tms.service;

import java.util.Date;
import java.util.List;


import com.zieta.tms.dto.VendorAdvanceDTO;

import com.zieta.tms.model.VendorAdvance;


import com.zieta.tms.response.VendorAdvanceResponse;




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
