package com.zieta.tms.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTypesByClientResponse {

	private Long id;

	private String timeType;

	private String clientCode;

	private String clientDescription;
}
