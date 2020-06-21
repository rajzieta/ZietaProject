package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusByClientTypeRequest {

	private Long id;
	private Long clientId;
	 private String status;
	    private String statusType;

}
