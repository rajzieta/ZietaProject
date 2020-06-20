package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcitivityRequest {

	private Long id;
	private Long clientId;
	private String activityCode;
	private String activity_desc;
	private boolean isActive;
	private String modified_by;

}
