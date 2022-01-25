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
	 private  float tsStartTime;//convert Float to String
	 private  float tsEndTime;//convert Float to String
	 private  float tsDuration;//convert Float to String
	 private  String timeDesc;
	 private String createdBy;
	 private String modifiedBy;
	 private short isDelete;
}
