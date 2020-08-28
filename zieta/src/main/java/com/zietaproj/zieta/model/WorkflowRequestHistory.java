package com.zietaproj.zieta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Data;

@Entity
@Table(name = "wf_request_history")
@Data
public class WorkflowRequestHistory extends AbstractPersistable<Long> {


    private Long id;

    @Column(name="client_id")
    private Long clientId;

    @Column(name="template_id")
    private Long templateId;
    
    @Column(name="project_id")
    private Long projectId;
    
    @Column(name="project_task_id")
    private Long projectTaskId;
  
    @Column(name="ts_id")
    private Long tsId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="request_date")
    private Date requestDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="action_date")
    private Date actionDate;
    
    
    @Column(name="current_step")
    private Long currentStep;
    
    
    @Column(name="approver_id")
    private Long approverId;
    
    @Column(name="requestor_id")
    private Long requestorId;
    
    @Column(name="state_type")
    private Long stateType;
    
    @Column(name="action_type")
    private Long actionType;
    
    private String comments;
    
}
