package com.zieta.tms.dto;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityMasterDTO extends BaseEntity {

	private Long activityId;
	private Long clientId;
//	private String activityCode;
	private String activityDesc;
	private boolean active;
//	private String createdBy;
//	private String modifiedBy;
//	private boolean isDelete;
	private String clientCode;
	private String clientDescription;
	private Long clientStatus;
}
