package com.zieta.tms.dto;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

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
