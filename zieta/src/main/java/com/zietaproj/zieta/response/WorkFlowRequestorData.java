package com.zietaproj.zieta.response;

import java.util.List;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkFlowRequestorData {
	
	String requestorName;
	String totalApprovedTime;
	String totalSubmittedTime;
	String submittedDate;
	String approvedDate;
	String rejectedDate;
	String status;
	List<TSTimeEntries> tsTimeEntries;
	TSInfo tsInfo;
	

}
