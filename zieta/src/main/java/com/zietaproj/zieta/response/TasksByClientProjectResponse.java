package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TasksByClientProjectResponse {
	
	Long id;
	Long project_id;
	Long task_type;
	Long task_parent;
	Long task_status;
	
	
	String projectCode;
	String taskCode;
	String projectDescription;
	String taskDescription;
	
	

}
