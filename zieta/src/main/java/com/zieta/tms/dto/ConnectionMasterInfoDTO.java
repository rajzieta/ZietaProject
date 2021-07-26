package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse.AdditionalDetails;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConnectionMasterInfoDTO {

    private Long id;
	private Long clientId;	
	private String connectionName;
	private String sid;
	private String protocol;
	private String host;
	private String port;
	private String sourceType;
	private String loginId;
	private String password;	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;


	
	
}
