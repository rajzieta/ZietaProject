package com.zieta.tms.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TimeEntriesByTimesheetIDResponse {

	
	private Long id;
	private Long tsId;

	private Long timeType;
	private Long statusId;
	 private  float tsStartTime;
	 private  float tsEndTime;
	 private  float tsDuration;
	 private  String timeDesc;
	 private String createdBy;
	 private String modifiedBy;
	 private Date createdDate;
	 private Date modifiedDate;
	 private short isDelete;
}
