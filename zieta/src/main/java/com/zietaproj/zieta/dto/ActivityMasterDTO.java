package com.zietaproj.zieta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityMasterDTO {

	private Long id;
	private Long clientId;
	private String activityCode;
	private String activity_desc;
	private boolean IS_ACTIVE;
	private String created_by;
	private String modified_by;
	private boolean IS_DELETE;
	private String client_code;
	
}
