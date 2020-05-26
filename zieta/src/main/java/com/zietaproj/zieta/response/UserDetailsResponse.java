package com.zietaproj.zieta.response;

import java.io.Serializable;

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
public class UserDetailsResponse implements Serializable {
	
	private long clientId;
	private String infoMessage;

	

}
