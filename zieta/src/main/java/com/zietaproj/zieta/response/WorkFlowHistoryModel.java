package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.WorkflowRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkFlowHistoryModel {
	
	WorkflowRequest workflowRequestHistory;
	String  activityName;
	String taskName;
	String approverName;
	String requestorName;

	
	
}
