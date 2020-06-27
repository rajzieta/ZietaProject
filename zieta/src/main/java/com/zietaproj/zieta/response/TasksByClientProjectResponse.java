package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TasksByClientProjectResponse {

	Long taskInfoId;
	Long projectId;
	Long taskType;
	Long taskParent;
	Long taskStatus;
	String taskManagerName;
	Long taskManager;
	String taskStatusDescription;
	String projectCode;
	String taskCode;
	String projectDescription;
	String taskDescription;
	String tasktypeDescription;

}
