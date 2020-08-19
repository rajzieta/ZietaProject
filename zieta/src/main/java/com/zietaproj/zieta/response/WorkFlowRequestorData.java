package com.zietaproj.zieta.response;

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

}
