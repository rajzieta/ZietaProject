package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgNodesByClientResponse extends BaseEntity {

	
	long orgUnitId;
	long clientId;
	private String clientCode;
	private String clientDescription;
	private String orgNodeCode;
	private String orgNodeName;
	private long orgType;
	private long orgParentId;
	private long orgStatus;
	private String orgUnitTypeDescription;
	
	
	
}
