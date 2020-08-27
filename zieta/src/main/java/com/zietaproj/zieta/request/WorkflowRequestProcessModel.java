package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkflowRequestProcessModel {
	
	long workFlowRequestId;
	String comments;
	short actionType;

}
