package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientUserModel {
	
	private Long activityId;
	private String activityDesc;
	private String activityCode;
	
	private Long taskId;
	private String taskDescription;
	private String taskCode;
	
	private Long projectId;
	private String projectCode;
	private String projectName;

}
