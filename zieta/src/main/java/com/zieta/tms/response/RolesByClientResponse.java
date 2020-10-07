package com.zieta.tms.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesByClientResponse {

	private Long id;

	private String userRole;
	
	private long clientId;
	
	private String clientCode;

	private String clientDescription;
	
	
}
