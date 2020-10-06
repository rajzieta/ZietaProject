package com.zieta.tms.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustInfoDTO {

	private Long custInfoId;
    private Long clientId;
    private String custName;
    private String custAddress;
    private String custDetails;
    private String custCode;
    private String createdBy;
    private String modifiedBy;
    private short isDelete;
	
}
