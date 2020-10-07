package com.zieta.tms.dto;

import java.util.Date;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusMasterDTO extends BaseEntity {

	private Long id;
	private Long clientId;
	private String clientCode;
    private String statusCode;
    private String statusDesc;
    private String statusType;
	private Boolean isDefault;
	private  String clientDescription;
	private Long clientStatus;

}
