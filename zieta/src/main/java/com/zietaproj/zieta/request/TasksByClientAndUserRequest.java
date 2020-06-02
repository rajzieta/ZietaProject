package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TasksByClientAndUserRequest {
	
	private long clientId;
	private long userId;

}
