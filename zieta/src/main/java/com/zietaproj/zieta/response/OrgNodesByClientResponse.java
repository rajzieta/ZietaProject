package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgNodesByClientResponse extends BaseEntity {

	
	long orgUnitId;
	long clientId;
	private String orgNodeName;
	private long orgType;
	private long orgParentId;
	private long orgStatus;
	//private long 
	private String orgUnitTypeDescription;
	
	
}
