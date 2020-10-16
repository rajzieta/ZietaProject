package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.WorkflowRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WFRDetailsForApprover {
	
	WorkflowRequest workflowRequest;
	
	TSInfo tsinfo;
	
	List<WFTSTimeEntries> timeEntriesList;
	
	String projectName;
	String clientName;
	String requestorName;
	String wfActionType;
	String wfStateType;
	String taskName;
	String activityName;
	List<WorkFlowComment> workFlowCommentList;
}
