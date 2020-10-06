package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessMasterDTO {

	private Long id;
	private String processName;
	private String processType;
	private Boolean additionalApprover;
}
