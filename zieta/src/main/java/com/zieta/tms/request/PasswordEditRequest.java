package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordEditRequest {

	private Long id;
	private String email;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
