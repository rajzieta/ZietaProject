package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoEditRequest {

	
	    private Long id;
		private Long clientId;
		private Long roleId;
		private Long expTemplateId;//due to table split has commented
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
		private Short isActive;
		private String modifiedBy;
		private Date modifiedDate;
		private Short isDelete;
		private short isTsOpen;
		private short isExpOpen;
		//private String password;
}
