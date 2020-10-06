package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessConfigDTO {

	private Long id;
	private Long templateId;
	private Long stepId;
	private String approverType;
	
}
