package com.zietaproj.zieta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityMasterDTO {

	private Long activityId;
	private Long clientId;
	private String activityCode;
	private String activityDesc;
	private boolean active;
	private String createdBy;
	private String modifiedBy;
	private boolean isDelete;
	private String clientCode;
	
}
