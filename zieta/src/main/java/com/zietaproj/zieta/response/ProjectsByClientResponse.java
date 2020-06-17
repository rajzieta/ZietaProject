package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.CustInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectsByClientResponse {

	Long projectId;
	private Long clientId;
	private String projectTypeName;
	private String clientCode;
	private String project_code;
	private String project_name;
	private String projectManager;
	private Long projectStatus;
	private long projectType;
	String OrgNode;
	short allowUnplannedActivity;
	CustInfo custInfo;
}
