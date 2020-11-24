package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMasterEditRequest {
	
	private Long projectInfoId;
    private Long clientId;
 //   private String projectCode;
    private String projectName;
    private long projectType;
    private long projectOrgNode;
    private Long projectManager;
    private Long templateId;
    private Long directApprover;
    private short allowUnplanned;
    private Long custId;
    private Long projectStatus;
    private String modifiedBy;
    private String createdBy;
    private short isDelete;
    
}
