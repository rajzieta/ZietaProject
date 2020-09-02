package com.zietaproj.zieta.dto;

import java.util.List;

import javax.persistence.Column;

import com.zietaproj.zieta.model.BaseEntity;
import com.zietaproj.zieta.model.ScreensMaster;

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
//	private String ScreenDescription;
	
	private List<ScreensMaster> screensByClient;
}
