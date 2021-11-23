package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionMasterInfoEditRequest {

	
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
		private String modifiedBy;
		private Date modifiedDate;
		private Short isDelete;
		
}
