package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpTemplateStepsDTO {

	private Long id;

    private Long templateId;
   
    private Long stepId;
    
    private int checkAmt;
    
    private String approverId;
    
    private Double approverAmount;    
    
}
