package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientResponse {

	long id;
    Long clientId;
    private String activity_code;
    private String activity_desc;
    private String clientCode;
	private boolean IS_ACTIVE;
}
