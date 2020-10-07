package com.zieta.tms.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreensMasterEditRequest {

	@NotNull
	private Long id;
	
	@NotBlank
	private String screenCode;

	@NotBlank
	private String screenCategory;

	@NotBlank
	private String screenTitle;
	
	
	@NotBlank
	private String screenDesc;

	

	
	
	
}
