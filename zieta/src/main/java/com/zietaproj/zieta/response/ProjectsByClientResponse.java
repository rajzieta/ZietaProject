package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectsByClientResponse {

	Long id;
	private Long clientId;
	private String type_name;
	private String clientCode;
	private String project_code;
	private String project_name;
	private String projectManager;
	private long projectStatus;
}
