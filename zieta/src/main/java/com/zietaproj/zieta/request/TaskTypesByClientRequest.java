package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskTypesByClientRequest {

	
	Long taskTypeId;
	Long clientId;
	private String taskTypeDescription;
	private boolean isDelete;
	
}
