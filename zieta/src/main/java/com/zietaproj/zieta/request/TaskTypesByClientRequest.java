package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskTypesByClientRequest {

	
	Long id;
	Long clientId;
	private String type_name;
	private boolean IS_DELETE;
	
}
