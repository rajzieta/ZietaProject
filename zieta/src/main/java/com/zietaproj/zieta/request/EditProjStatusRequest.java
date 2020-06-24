package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProjStatusRequest {

	private Long id;
	
	//private Long clientId;
	//private String project_code;
	//private String project_name;
	//private Long project_type;
	private Long project_status;
   // private Long projectManager;
   // private short allow_unplanned;
	
	private String ProjectStatusDescription;
	
}
