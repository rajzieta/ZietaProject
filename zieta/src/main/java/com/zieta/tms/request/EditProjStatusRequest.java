package com.zieta.tms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProjStatusRequest {

	private Long projectInfoId;
	private Long projectStatus;
	
}
