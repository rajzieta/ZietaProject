package com.zieta.tms.response;

import java.sql.Time;
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
	Time totalApprovedTime;//convert float to time
	Time totalSubmittedTime;
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
