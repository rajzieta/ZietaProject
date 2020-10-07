package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditTasksByClientProjectRequest {

	private Long taskInfoId;
	private Long clientId;
	private Long projectId;
	private String taskDescription;
	private String taskCode;
	private Long taskType;
	private Long taskParent;
	private Long taskManager;
	private Long taskStatus;
	private Long sortKey;
	private boolean isDelete;
	private String modifiedBy;
}
