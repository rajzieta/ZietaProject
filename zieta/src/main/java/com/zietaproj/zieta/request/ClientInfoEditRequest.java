package com.zietaproj.zieta.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoEditRequest {
	
	@NotNull
	private Long id;
	
	private String clientCode;
	private String clientName;
	private Long clientStatus;
	private String clientComments;
	private String modifiedBy;
	private boolean isDelete;
	private short superAdmin;
}
