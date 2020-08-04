package com.zietaproj.zieta.model;

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
@Table(name = "PROCESS_STEPS")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = {"createdDate", "modifiedDate"}, 
allowGetters = true)
public class ProcessSteps implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;
    
    @Column(name = "project_id")
    private Long projectId;
    
    @Column(name = "process_id")
    private Long processId;
    
    @Column(name = "step_id")
    private Long stepId;
    
    @Column(name = "approver_id")
    private String approverId;
	
}
