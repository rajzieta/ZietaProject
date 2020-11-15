package com.zieta.tms.dto;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrgUnitUserMappingDTO {

	private Long id;
    private Long clientId;
    private Long orgUnitId;
    private Long userId;
   
}
