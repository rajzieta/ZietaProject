package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TasksByUserModel {
	long projectId;
	String project_name;
	String task_name;

}
