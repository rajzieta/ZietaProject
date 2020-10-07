package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskInfoRequest {
	
	private Long taskInfoId;
	private Long sortKey;

}
