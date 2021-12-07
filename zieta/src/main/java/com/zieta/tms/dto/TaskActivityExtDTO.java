package com.zieta.tms.dto;

import java.util.Date;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskActivityExtDTO extends BaseEntity {

	private Long clientId;
	private String extId;
	private String extActivityId;
	private String extTaskId;
	private String extProjectId;
	private String extUserId;
	private Long activityId;
	private String createdBy;
	private String modifiedBy;
	private Date startDate;
	private Date endDate;
	private float plannedHrs;
	private float actualHrs;
	private Long userId;

}
