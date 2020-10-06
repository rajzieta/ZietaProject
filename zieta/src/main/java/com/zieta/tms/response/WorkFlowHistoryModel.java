package com.zieta.tms.response;

import com.zieta.tms.model.WorkflowRequest;

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
