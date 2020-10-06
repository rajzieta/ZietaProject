package com.zieta.tms.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectTypeByClientResponse {

	private Long projectTypeId;
    private Long clientId;
    private String clientCode;
    private String clientDescription;
    private String typeName;
    private String createdBy;
    private String modifiedBy;
    private boolean isDELETE;
	
	
	
}
