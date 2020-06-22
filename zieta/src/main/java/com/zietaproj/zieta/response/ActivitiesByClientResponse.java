package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientResponse {

	long id;
    Long clientId;
    private String activityCode;
    private String activity_desc;
    private String clientCode;
    private boolean isActive;
}
