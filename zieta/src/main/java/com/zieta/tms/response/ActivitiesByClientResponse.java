package com.zieta.tms.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientResponse {

	long activityId;
    Long clientId;
    private String activityCode;
    private String activityDesc;
    private String clientCode;
    private String clientDescription;
    private boolean active;
	
}
