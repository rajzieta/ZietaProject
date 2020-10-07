package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ts_workflow")
@Data
public class TSWorkflow {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  
	  @Column(name = "ts_id")
	  private Long tsId;
	  
	  @Column(name = "workflow_step_id")
	  private Long workflowStepId;
	  
	  @Column(name = "status_id")
	  private Long statusId;
	  
	  @Column(name = "status_comment")
	  private String statusComment;
	  
	  @Column(name = "commented_by")
	  private Long commentedBy;
	
}
