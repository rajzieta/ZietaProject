package com.zieta.tms.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ScreensMasterAddRequest {

	@NotBlank
	private String screenCode;

	@NotBlank
	private String screenCategory;

	@NotBlank
	private String screenTitle;
	
	
	@NotBlank
	private String screenDesc;


	
	
}
