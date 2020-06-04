package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TasksByUserModel {
	long projectId;
	long taskId;
	long userId;
	String project_name;
	String task_name;
	String project_code;
	String task_code;

}
