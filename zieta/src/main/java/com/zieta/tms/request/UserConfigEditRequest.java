package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserConfigEditRequest {

	private Long id;	
	private Long userId;
	private Long roleId;
	private Long orgnode;	
	private Long reportingMgr;	
	private Long accessTypeId;	
	private Long expTemplateId;	
	private short tsOpen;	
	private short expOpen;	
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;
	
}
