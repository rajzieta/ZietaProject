package com.zieta.tms.request;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditTasksByClientProjectRequest {

	private Long taskInfoId;
	private Long clientId;
	private Long projectId;
	private String taskDescription;
//	private String taskCode;
	private Long taskType;
	private Long taskParent;
	private Long taskManager;
	private Long taskStatus;
	private Date taskStartDate;
    private Date taskendDate;
	private Long sortKey;
	private boolean isDelete;
	private String modifiedBy;
}
