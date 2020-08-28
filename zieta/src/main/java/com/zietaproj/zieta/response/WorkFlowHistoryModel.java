package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.WorkflowRequestHistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkFlowHistoryModel {
	
	WorkflowRequestHistory workflowRequestHistory;
	String  activityName;
	String taskName;
	String approverName;
	String requestorName;

	
	
}
