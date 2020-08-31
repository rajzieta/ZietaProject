package com.zietaproj.zieta.common;

import lombok.Getter;

@Getter
public enum ApproverType {
	
	TASKMANAGER( "Task Manager"),

	PROJECTMANAGER( "Project Manager"),

	DIRECTAPPROVER("Direct Approver"),
	
	TMORPM("Task Manager or Project Manager");
	
	String approverType;
	
	ApproverType(String approverType){
		this.approverType = approverType;
	}
}
