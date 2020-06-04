package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivitiesByTaskResponse {

	Long task_id;
    private Long client_id;
    private Long activity_id;
    private String activity_code;
    private String activity_desc;
	 
}
