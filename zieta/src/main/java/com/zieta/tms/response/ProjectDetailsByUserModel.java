package com.zieta.tms.response;

import com.zieta.tms.model.CustInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDetailsByUserModel {
	
	//project info details starts
	long projectInfoId;
	long clientId;
	String projectCode;
	String projectName;
	long projectType;
	long projectOrgNode;
	long projectManager;
	long templateId;
	long directApprover;
	short allowUnplanned;
	long custId;
	long projectStatus;
	String createdBy;
	String modifiedBy;
	//project info details ends
	
	//additional details starts
	String projectTypeName;
	String orgNodeName;
	String projectManagerName;
	String projectStatusDescription;
	String clientCode;
	String clientDescription;
	Long clientStatus;
	CustInfo custInfo;
	String templateDesc;
	String approverName;
	//additional details ends
	
	
}
