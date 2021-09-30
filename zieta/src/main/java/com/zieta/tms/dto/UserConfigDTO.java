package com.zieta.tms.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserConfigDTO {	
	private Long id;	
	private Long userId;	
	private Long orgnode;	
	private Long reportingMgr;	
	private Long accessTypeId;	
	private Long expTemplateId;	
	private short tsOpen;	
	private short expOpen;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;
	
	
}
