package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse.AdditionalDetails;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersInfoDTO {

    private Long id;
	private Long clientId;
	private Long expTemplateId;
	private String userFname;
	private String userMname;
	private String userLname;
	private String email;
	private String empId;
	private String extId;
	private Long orgNode; 
	private Long reportingMgr;
	private Long accessTypeId;
	private String phoneNo;
	private short isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;
	private short isTsOpen;
	private short isExpOpen;
	private String password;
	private Long roleId;

	
	
}
