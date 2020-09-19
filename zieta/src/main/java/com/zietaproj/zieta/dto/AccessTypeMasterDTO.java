package com.zietaproj.zieta.dto;

import lombok.Data;

@Data
public class AccessTypeMasterDTO {

	private Long id;
    private Long clientId;
    private String clientCode;
    private String clientDescription;
    private Long clientStatus;
    private String accessType;
    private String createdBy;
    private String modifiedBy;
    private boolean isDelete;
	
	
    
    
}
