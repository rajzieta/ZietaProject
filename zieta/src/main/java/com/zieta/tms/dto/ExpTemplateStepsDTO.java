package com.zieta.tms.dto;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpTemplateStepsDTO extends BaseEntity {

	private Long id;

    private Long templateId;
   
    private Long stepId;
    
    private int checkAmt;
    
    private String approverId;
    
    private Double approverAmount;    
    
}
