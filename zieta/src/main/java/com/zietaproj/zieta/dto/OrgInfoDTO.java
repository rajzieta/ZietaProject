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
    private String orgNodeName;
    private Long orgType;
    private Long orgParentId;
    private Long orgStatus;
	
}
