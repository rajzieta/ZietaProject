package com.zieta.tms.dto;

import java.util.List;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;
import com.zieta.tms.model.ScreensMaster;

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
	

	private String clientCode;
	private String clientDescription;
	private Long clientStatus;
	private String accessTypeDescription;



//	private String ScreenDescription;
	
	private List<ScreensMaster> screensByClient;
}
