package com.zietaproj.zieta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.CurrentDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "WORKFLOW_REQUEST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)


public class WorkflowRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="client_id")
    private Long clientId;

    @Column(name="template_id")
    private Long templateId;
    
    @Column(name="project_id")
    private Long projectId;
    
    @Column(name="project_task_id")
    private Long projectTaskId;
  
    @Column(name="ts_id")
    private String tsId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="request_date")
    private Date requestDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="current_date")
    private Date currentDate;
    
    
    @Column(name="current_step")
    private Long currentStep;
    
    
    @Column(name="approver_id")
    private Long approverId;
    
    @Column(name="state_type")
    private Long stateType;
    
    @Column(name="action_type")
    private Long actionType;
    
    private String comments;
    
}
