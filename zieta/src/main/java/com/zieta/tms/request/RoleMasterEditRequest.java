package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMasterEditRequest {

	private Long id;
    private Long clientId;
    private String userRole;
    private String createdBy;
    private String modifiedBy;
    private short isDelete;
	
}
