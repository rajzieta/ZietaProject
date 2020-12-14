package com.zieta.tms.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserInfoDTO {

    private Long id;
	private Long clientId;
	private String userFname;
	private String userMname;
	private String userLname;
	private String email;
	private String empId;
	private Long orgNode;
	private Long accessTypeId;
	private String phoneNo;
	private short isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;
	private String password;
	
	//additional values
	private String clientCode;
	private String clientDescription;
	private Long clientStatus;
	private String accessType;
	private String orgNodeName;

	

}
