package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoAddRequest {
	
	

	@NotBlank
	private String client_code;

	@NotBlank
	private String client_name;

	@NotNull
	private Long client_status;
	
	
	@NotBlank
	private String client_comments;
	
	
	private boolean IS_DELETE;



}
