package com.zietaproj.zieta.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

	private String userEmailId;
	private String password;

}
