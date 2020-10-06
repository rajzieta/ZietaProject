package com.zieta.tms.response;

import com.zieta.tms.model.CustInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectsByClientResponse {

	Long id;
	private Long clientId;

	private String ProjectTypeName;
	

	private String clientCode;
	private String projectCode;
	private String projectName;
	private String projectManager;
	private Long projectStatus;
	private long projectType;
	private String projectStatusDescription;
	
	String OrgNode;
	short allowUnplannedActivity;
	CustInfo custInfo;
	
	
	
}
