package com.zieta.tms.request;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTypeAddRequest {


    private Long id;
	private Long clientId;
    private String accessType;
	private String createdBy;
    private String modifiedBy;
	private boolean isDelete;
	
}
