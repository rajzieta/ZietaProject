package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.dto.ScreenCategoryMasterDTO;
import com.zieta.tms.dto.VendorInvoiceDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.request.ExpenseTemplateEditRequest;



public interface VendorInvoiceService {

	//public List<ExpenseTemplateDTO> getAllExpenseTemplate();//
	
	public List<VendorInvoiceDTO> getAllActiveVendorInvoice(Long clientId, Short isDelete);
	
	//public List<ExpTemplateStepsDTO> getAllActiveExpenseTemplateSteps(Long expTemplateId, Short isDelete);

	public VendorInvoice addVendorInvoice(VendorInvoice vendorInvoice) throws Exception;
	
	public VendorInvoiceDTO findVendorInvoiceById(long id)throws Exception;
	
	public void deleteByVendorInvoiceId(Long id, String modifiedBy) throws Exception;

	//public void editExpenseTemplateById(ExpenseTemplateEditRequest expTemplate) throws Exception;
	
	//public List<ExpTemplateSteps> addExpenseTemplateSteps(List<ExpTemplateSteps> expTemplateStepList) throws Exception;
	
	//public void deletetTemplateById(Long id, String modifiedBy) throws Exception;
	
	//public void deletetTemplateStepsById(Long[] id, String modifiedBy) throws Exception;
	
	//public List<ExpenseTemplateDTO> getAllExpenseTemplate(Short isDelete);
	
}
