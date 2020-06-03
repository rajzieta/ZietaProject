package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TasksByClientProjectResponse {
	
	String projectCode;
	String taskCode;
	String projectDescription;
	String taskDescription;

}
