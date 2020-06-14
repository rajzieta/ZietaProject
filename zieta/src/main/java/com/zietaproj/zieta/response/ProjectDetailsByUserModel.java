package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.CustInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDetailsByUserModel {
	
	String projectCode;
	long projectId;
	String projectTypeName;
	String projectName;
	long projectType;
	String OrgNode;
	long projectStatus;
	long clientId;

	String projectManager;
	short allowUnplannedActivity;
	CustInfo custInfo;
	
}
