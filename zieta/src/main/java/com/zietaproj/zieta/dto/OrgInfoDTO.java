package com.zietaproj.zieta.dto;

import javax.persistence.Column;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgInfoDTO extends BaseEntity {

	private Long orgUnitId;
    private Long clientId;
    private String orgNodeCode;
    private String orgNodeName;
    private Long orgType;
    private Long orgParentId;
    private Long orgStatus;
    
    //additional fields
    private String clientCode;
    private String clientDescription;
    private Long clientStatus;
    private String orgUnitTypeDescription;
	
}
