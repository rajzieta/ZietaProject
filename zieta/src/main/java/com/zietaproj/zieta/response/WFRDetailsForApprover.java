package com.zietaproj.zieta.response;

import java.util.List;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.WorkflowRequest;

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
}
