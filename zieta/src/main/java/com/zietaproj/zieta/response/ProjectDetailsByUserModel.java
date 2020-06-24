package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.CustInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDetailsByUserModel {
	
	String project_code;
	long projectId;
	String projectTypeName;
	String project_name;
	long projectType;
	String OrgNode;
	long projectStatus;
	String projectStatusDescription;
	long clientId;
	String clientCode;
	
	String projectManager;
	short allowUnplannedActivity;
	CustInfo custInfo;
	
}
