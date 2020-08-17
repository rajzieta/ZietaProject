package com.zietaproj.zieta.common;

import lombok.Getter;

@Getter
public enum Status {
	
	DRAFT("Draft"),

	SUBMITTED("Submitted"),

	APPROVED("Approved"),
	
	REJECTED("Rejected");
	
	String status;
	
	Status(String status){
		this.status = status;
	}
	
}
