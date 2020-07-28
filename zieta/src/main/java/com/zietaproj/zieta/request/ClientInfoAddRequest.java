package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoAddRequest {
	
	

	@NotBlank
	private String clientCode;

	@NotBlank
	private String clientName;

	@NotNull
	private Long clientStatus;
	
	
	@NotBlank
	private String clientComments;
	
	
	private boolean isDelete;



}
