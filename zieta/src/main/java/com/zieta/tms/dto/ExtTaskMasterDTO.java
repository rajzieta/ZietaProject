package com.zieta.tms.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtTaskMasterDTO extends BaseEntity implements Serializable {
	
	private Long clientId;
	private String extTaskInfoId;
	private String extProjectId;
	private String taskDescription;
	private String extTaskType;
	private String extTaskParent;
	private String extTaskManager;
	private String extTaskStatus;
	private Date taskStartDate;
	private Date taskendDate;
	private Long sortKey;

}
