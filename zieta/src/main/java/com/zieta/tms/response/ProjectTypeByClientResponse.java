package com.zieta.tms.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectTypeByClientResponse {

	private Long projectTypeId;
    private Long clientId;
    private String extId;
    private String clientCode;
    private String clientDescription;
    private String typeName;
    private Boolean custMandatory;
    private Boolean isEditable;
    private String createdBy;
    private String modifiedBy;
    private boolean isDELETE;
	
	
	
}
