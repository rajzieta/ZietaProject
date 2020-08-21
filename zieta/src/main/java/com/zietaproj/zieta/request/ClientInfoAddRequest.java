package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoAddRequest {
	
	


	private String clientCode;
	private String clientName;
	private Long clientStatus;
	private String clientComments;
	private String createdBy;
	private String modifiedBy;
	private boolean isDelete;



}
