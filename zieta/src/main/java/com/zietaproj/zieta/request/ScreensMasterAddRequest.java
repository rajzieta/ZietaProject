package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ScreensMasterAddRequest {

	@NotBlank
	private String screen_code;

	@NotBlank
	private String screen_category;

	@NotBlank
	private String screen_title;
	
	
	@NotBlank
	private String screen_desc;


	
	
}
