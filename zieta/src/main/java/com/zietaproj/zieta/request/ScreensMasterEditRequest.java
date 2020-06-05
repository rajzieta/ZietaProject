package com.zietaproj.zieta.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ScreensMasterEditRequest {

	@NotNull
	private Long id;
	
	@NotBlank
	private String screen_code;

	@NotBlank
	private String screen_category;

	@NotBlank
	private String screen_title;
	
	
	@NotBlank
	private String screen_desc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScreen_code() {
		return screen_code;
	}

	public void setScreen_code(String screen_code) {
		this.screen_code = screen_code;
	}

	public String getScreen_category() {
		return screen_category;
	}

	public void setScreen_category(String screen_category) {
		this.screen_category = screen_category;
	}

	public String getScreen_title() {
		return screen_title;
	}

	public void setScreen_title(String screen_title) {
		this.screen_title = screen_title;
	}

	public String getScreen_desc() {
		return screen_desc;
	}

	public void setScreen_desc(String screen_desc) {
		this.screen_desc = screen_desc;
	}
	
	
}
