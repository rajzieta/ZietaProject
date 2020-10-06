package com.zieta.tms.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessScreensRequest {



	private Long clientId;
	
	private List<Long> screenIds;
	
	private Long accessTypeId;
	
}
