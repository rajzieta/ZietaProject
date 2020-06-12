package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientResponse {

	long id;
    Long clientId;
   // private Long project_id;
    private String activity_code;
    private String activity_desc;
  //  private String projectCode;
    private String clientCode;
	
}
