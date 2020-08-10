package com.zietaproj.zieta.common;

import lombok.Getter;

@Getter
public enum ApproverType {
	
	TASKMANAGER( "Task Manager"),

	PROJECTMANAGER( "Project Manager"),

	DIRECTAPPROVER("Direct Approver"),
	
	TMORPM("TM or PM");
	
	String approverType;
	
	ApproverType(String approverType){
		this.approverType = approverType;
	}
}
