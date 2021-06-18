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
@Table(name = "expense_template_steps")
@Data
@EqualsAndHashCode(callSuper=false)
public class ExpTemplateSteps extends BaseEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    
    @Column(name = "template_id")
    private Long templateId;
    
    @Column(name = "step_id")
    private Long stepId;
    
	
	  @Column(name = "check_amt") 
	  private int checkAmt;
	 
    
    @Column(name = "approver_id")
    private Long approverId;
    
    @Column(name = "approver_amount")
    private Double approverAmount;
    
	
}
