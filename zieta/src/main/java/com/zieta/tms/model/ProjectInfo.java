package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "project_info")
@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectInfo extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long projectInfoId;

	@NotNull
	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "project_code")
	private String projectCode;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "project_type" )
	private Long projectType;

	@Column (name = "project_orgnode")
	private Long projectOrgNode;
	
	@Column(name = "project_manager")
	private Long projectManager;
	
	@Column(name = "template_id")
	private Long templateId;
	
	@Column(name = "direct_approver")
	private Long directApprover;
	
	@Column( name = "allow_unplanned")
    private short allowUnplanned;
	
	@Column(name = "cust_id")
	private Long custId;

	@Column( name = "project_status")
	private Long projectStatus;
	
}
