package com.zietaproj.zieta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskMasterDTO {
	private Long taskTypeId;
    private Long clientId;
    private String taskTypeDescription;
    private String modifiedBy;
    private String createdBy;
    private boolean isDelete;
    private String clientCode;
	private String clientDescription;
	private Long clientStatus;
   
	
}
