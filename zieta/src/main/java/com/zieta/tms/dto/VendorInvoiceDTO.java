package com.zieta.tms.dto;

import java.util.Date;

import org.apache.poi.hpsf.Decimal;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VendorInvoiceDTO extends BaseEntity{

	private Long id;
	private Long clientId;
	private String comapnyCodename;
	private String comapnyName;
	private String branchCode;
	private String branchName;
	private String vendorCode;
	private String vendorName;
	private String invoiceNum;
	private Date invoiceDate;
	private Date submitDate;
	private Decimal invoiceAmt;
	private String invoiceCurrency;
	private String openAdvance;
	private String advAdjustmentRemarks;
	private String supplierState;
	private String receipientState;
	private String briefRemarks;
	private String remarks;
	
	
	
	
}
