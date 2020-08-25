package com.zietaproj.zieta.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusByClienttypeResponse {

	
	private Long id;
	private Long clientId;
    private String statusCode;
    private String statusDesc;
    private String statusType;
    private String createdBy;
    private Date   createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private short isDelete;
    private Boolean isDefault;
	private String clientCode;
	private String clientDescription;
		
	
}
