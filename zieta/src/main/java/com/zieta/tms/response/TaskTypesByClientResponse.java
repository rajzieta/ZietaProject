package com.zieta.tms.response;

import java.util.Date;

import com.zieta.tms.model.TaskTypeMaster;

import lombok.Data;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TaskTypesByClientResponse {

	long taskTypeId;
	long clientId;
	String taskTypeDescription;
	String createdBy;
	String modifiedBy;
	Date createdDate;
	Date modifiedDate;
	boolean isDelete;
	String clientCode;
	String clientDescription;
}
