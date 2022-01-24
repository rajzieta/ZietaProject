package com.zieta.tms.dto;

import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSInfoDTO {

    private Long id;
	private Long clientId;
	private Long tsId;
	private Long projectId;
	private Long activityId;
	private Long taskActivityId;
	private Long userId;
	private Long taskId;
	private Long statusId;
	private boolean plannedActivity;	
	private String tsDate;
	private String submitDate;
	private Time tsTotalSubmittedTime;//convert float to time 24012022
	private Time tsTotalApprovedTime;//convert float to time 24012022	
	private String createdBy;
	private String modifiedBy;	
	private short isDelete;	
	
}
