package com.zietaproj.zieta.dto;

import javax.persistence.Column;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTypeScreenMappingDTO extends BaseEntity {

	
    private Long id;
	private Long clientId;
	private Long screenId;
	private Long accessTypeId;
	
	//additional
	
	private String ClientDescription;
	private String AccessTypeDescription;
	private String ScreenDescription;
}
