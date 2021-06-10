package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQualificationEditRequest {

	
	private Long id;
    private Long userId;
	private Long clientId;
	private Long qualificationDesc;
	private String qualFileName;
	private String qualFilePath;	
	private String modifiedBy;
	private Date modifiedDate;
	private Short isDelete;
	
}
