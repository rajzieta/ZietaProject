package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessStepsDTO {

	private Long id;
    private Long clientId;
    private Long projectId;
    private Long templateId;
    private Long projectTaskId;
    private Long stepId;
    private String approverId;
    
    //description details
    
    private String clientCode;
    private String clientDescription;
    private String projectCode;
    private String projectDescription;
    private String taskCode;
    private String taskDescription;
    private String processDescription;
    private String approverName;
}
