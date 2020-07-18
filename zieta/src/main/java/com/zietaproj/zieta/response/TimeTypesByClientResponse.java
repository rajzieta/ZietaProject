package com.zietaproj.zieta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTypesByClientResponse {

	private Long id;

	private String time_type;

	private String clientCode;

	private String clientDescription;
}
