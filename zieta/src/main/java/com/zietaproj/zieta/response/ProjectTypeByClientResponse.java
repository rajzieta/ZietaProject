package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectTypeByClientResponse {

	private Long projectTypeId;
    private Long clientId;
    private String typeName;
    private String createdBy;
    private String modifiedBy;
    private boolean isDELETE;
	
	
}
