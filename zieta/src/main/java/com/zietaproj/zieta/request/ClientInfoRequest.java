package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoRequest {
	
	

	@NotBlank
	private String client_code;

	@NotBlank
	private String client_name;

	@NotNull
	private Long client_status;
	
	
	@NotBlank
	private String client_comments;
	
	
	@ApiModelProperty(notes="Id is not required in case of creating clientInfo record")
	private Long id;
	
	
	



}
