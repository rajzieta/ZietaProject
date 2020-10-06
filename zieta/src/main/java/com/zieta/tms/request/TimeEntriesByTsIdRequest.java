package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeEntriesByTsIdRequest {

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
	 private short isDelete;
}
