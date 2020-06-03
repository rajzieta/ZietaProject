package com.zietaproj.zieta.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoEditRequest extends ClientInfoAddRequest {
	
	@NotNull
	private Long id;

}
