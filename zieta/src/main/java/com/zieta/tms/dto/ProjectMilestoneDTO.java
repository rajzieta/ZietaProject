package com.zieta.tms.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ProjectMilestoneDTO {

	private Long id;
	private Long clientId;
	private Long projectId;
	private String milestoneName;
	private Date plannedInvDate;
	private Date revisedInvDate;
	private BigDecimal invAmount;
	private Long invCurrency;
	private BigDecimal invAmtInr;
	private Long milestoneStatus;
	private Long invoice_status;
	private Date actualInvDate;
	private Date invDueDate;
	private String comments;
}
