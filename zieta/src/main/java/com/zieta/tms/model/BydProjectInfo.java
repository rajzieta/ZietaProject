package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "project_info")
@Data
@EqualsAndHashCode(callSuper=false)
public class BydProjectInfo extends BaseEntity implements Serializable {

	
	
	@NotNull
	@Column(name = "client_id")
	private Long clientId;

//	@NotBlank
	//@Column(name = "project_code")
//	private String projectCode;

	@Column(name = "EXT_ID")
	private String extId;
	
	@Column(name = "project_name")
	private String projectName;

	@Column(name = "project_type" )
	private String ExtProjectType;

	@Column (name = "project_orgnode")
	private String ExtProjectOrgNode;
	
	@Column(name = "project_manager")
	private String ExtProjectManagerId;
	
	@Column(name = "template_id")
	private Long templateId;
	
	@Column(name = "direct_approver")
	private String extDirectApprover;
	
	@Column( name = "allow_unplanned")
    private short allowUnplanned;
	
	@Column(name = "cust_id", nullable = true)
	private String ExtCustId;

	@Column( name = "project_status")
	private String ExtProjectStatus;
	
}
