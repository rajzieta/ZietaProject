package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "wf_process_steps")
@Data
@EqualsAndHashCode(callSuper=false)
public class ProcessSteps implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;
    
    @Column(name = "project_id")
    private Long projectId;
    
    @Column(name = "template_id")
    private Long templateId;
    
    @Column(name = "project_task_id")
    private Long projectTaskId;
    
    @Column(name = "step_id")
    private Long stepId;
    
    @Column(name = "approver_id")
    private String approverId;
	
}
