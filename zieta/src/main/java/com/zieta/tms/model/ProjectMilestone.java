package com.zieta.tms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Table(name = "project_milestone")
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_date", "modified_date" }, allowGetters = true)
@Data
public class ProjectMilestone extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "CLIENT_ID")
	private Long clientId;

	@NotNull
	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "MILESTONE_NAME")
	private String milestoneName;

	@Column(name = "PLANNED_INV_DATE")
	private Date plannedInvDate;

	@Column(name = "REVISED_INV_DATE")
	private Date revisedInvDate;

	@Column(name = "INV_AMOUNT")
	private BigDecimal invAmount;

	@Column(name = "INV_CURRENCY")
	private Long invCurrency;

	@Column(name = "INV_AMT_INR")
	private BigDecimal invAmtInr;

	@Column(name = "MILESTONE_STATUS")
	private Long milestoneStatus;

	@Column(name = "INVOICE_STATUS")
	private Long invoice_status;

	@Column(name = "ACTUAL_INV_DATE")
	private Date actualInvDate;

	@Column(name = "INV_DUE_DATE")
	private Date invDueDate;

	@Column(name = "COMMENTS")
	private String comments;

}
