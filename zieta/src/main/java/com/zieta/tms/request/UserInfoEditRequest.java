package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoEditRequest {

	
	    private Long id;
		private Long clientId;
		private String userFname;
		private String userMname;
		private String userLname;
		private String email;
		private String empId;
		private Long accessTypeId;
		private String phoneNo;
		private short isActive;
		private String modifiedBy;
		private Date modifiedDate;
		private short isDelete;
		//private String password;
}
