package com.zietaproj.zieta.response;

import java.util.Date;
import java.util.List;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkFlowRequestorData {
	
	String requestorName;
	float totalApprovedTime;
	float totalSubmittedTime;
	Date submittedDate;
	Date approvedDate;
	Date rejectedDate;
	String status;
	List<TSTimeEntries> tsTimeEntries;
	TSInfo tsInfo;
	

}
