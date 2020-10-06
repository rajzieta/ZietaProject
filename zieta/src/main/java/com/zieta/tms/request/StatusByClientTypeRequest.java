package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusByClientTypeRequest {

	private Long id;
	private Long clientId;
	 private String statusCode;
	 private String statusDesc;
	    private String statusType;
	    private Boolean isDefault;
	    private String modifiedBy;

}
