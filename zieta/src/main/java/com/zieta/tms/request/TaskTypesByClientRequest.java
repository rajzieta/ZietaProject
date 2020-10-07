package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskTypesByClientRequest {

	
	Long taskTypeId;
	Long clientId;
	private String taskTypeDescription;
	private short isDelete;
	private String modifiedBy;
	
}
