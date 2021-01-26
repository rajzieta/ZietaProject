package com.zieta.tms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveReportDTO {
		private String leaveId;
		private String statusId;
	    private String clientName;
	    private String userName;
	    private String leaveDesc;
	    private String leaveType;
	    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	    private String leaveStartDate;
	    private String startSession;
	    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	    private String leaveEndDate;
	    private String endSession;
	    private String approverName;
	    private String approverComments;
	    private String status;


}
