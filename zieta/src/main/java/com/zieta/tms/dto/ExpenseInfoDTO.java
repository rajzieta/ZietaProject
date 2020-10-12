package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ExpenseInfoDTO extends BaseEntity{

	private Long id;
	private Long clientId;
	private Long userId;
	private Long projectId;
	private String expHeading;
	private Date expStartDate;
	private Date expEndDate;
	private Date expPostingDate;
	private float expAmount;
	private float approvedAmt;
	private Long statusId;
	
	
	private String projectCode;
	private String projectDesc;
	
}
