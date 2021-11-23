package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmployeeAdvanceDTO extends BaseEntity{

	 private Long id;
		private Long clientId;
		private Long userId;
		private String companyCodename;
		private String companyName;
		private String branchCode;
		private Date advanceReqDate;
		private Long advanceAmt;		
		private String advCurrency;
		private String remarks;
	
}
