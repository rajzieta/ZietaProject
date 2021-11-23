package com.zieta.tms.dto;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ExternalProjectMasterDTO extends BaseEntity {
	private Long clientId;

	private String extId;
	
	private String projectName;

	private String ExtProjectType;

	private String ExtProjectOrgNode;
	
	private String ExtProjectManagerId;
	
	private Long templateId;
	private String extDirectApprover;
	
    private short allowUnplanned;
	
	private String ExtCustId;
	private String ExtProjectStatus;
	
}
