package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcitivityRequest {

	private Long activityId;
	private Long clientId;
//	private String activityCode;
	private String activityDesc;
	private boolean active;
	private String modifiedBy;

}
