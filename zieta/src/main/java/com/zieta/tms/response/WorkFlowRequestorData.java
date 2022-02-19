package com.zieta.tms.response;

import java.util.Date;
import java.util.List;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TSTimeEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkFlowRequestorData {
	
	String requestorName;
	/*Float totalApprovedTime;//DUE TO CONVERT FROM FLOAT TO TIME(00:00:00)
	Float totalSubmittedTime;*/
	
	String totalApprovedTime;
	String totalSubmittedTime;
	Date submittedDate;
	Date approvedDate;
	Date rejectedDate;
	String status;
	List<TSTimeEntries> tsTimeEntries;
	TSInfo tsInfo;
	String projectName;
	String clientName;
	String taskName;
	String activityName;
	

}
