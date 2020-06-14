package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesByClientResponse {

	private Long id;

	private String user_role;
	
	private long clientId;
	
	private String clientCode;
	
	
}
