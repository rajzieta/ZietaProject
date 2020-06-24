package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditTasksByClientProjectRequest {

	 private Long id;
	 private Long clientId;
	 private Long projectId;
	 private String task_name;
	 private String task_code;
	 private Long task_type;
	 private Long task_parent;
	 private Long task_manager;
	  private Long task_status;
	  private boolean IS_DELETE;
}
