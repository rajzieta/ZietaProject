package com.zieta.tms.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
	
	boolean isLoginValid;
	boolean isActive;
	String infoMessage;
	short isSuperAdmin;

}
