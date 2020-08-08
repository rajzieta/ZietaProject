package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMasterEditRequest {
	
	private Long projectTypeId;
    private Long clientId;
    private String typeName;
    private String modifiedBy;
    private String createdBy;
    private short isDelete;
    
}
