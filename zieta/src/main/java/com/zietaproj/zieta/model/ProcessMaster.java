package com.zietaproj.zieta.model;

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
@Table(name = "WF_TEMPLATE_MASTER")
@Data
@EqualsAndHashCode(callSuper=false)
public class ProcessMaster implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_name")
    private String processName;
    
    @Column(name = "process_type")
    private String processType;
    
    @Column(name = "additional_approver")
    private boolean additionalApprover;
    
}
