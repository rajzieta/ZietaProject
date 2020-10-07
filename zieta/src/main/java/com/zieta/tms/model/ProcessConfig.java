package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "wf_process_config")
@Data
@EqualsAndHashCode(callSuper=false)
public class ProcessConfig implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	 
    @Column(name = "template_id")
    private Long templateId;
    
    
	@Column(name = "step_id")
    private Long stepId;
    
    @Column(name = "approver_type")
    private String approverType;
	
}
