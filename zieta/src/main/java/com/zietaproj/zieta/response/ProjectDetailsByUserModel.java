package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDetailsByUserModel {
	
	String projectCode;
	String projectName;
	long projectType;
	String OrgNode;
	short projectStatus;
	long clientId;

}
