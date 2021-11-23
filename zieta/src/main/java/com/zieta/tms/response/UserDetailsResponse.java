package com.zieta.tms.response;

import java.io.Serializable;
import java.util.List;

import com.zieta.tms.model.ScreenCategoryMaster;
import com.zieta.tms.model.ScreensMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsResponse implements Serializable {
	
	private long clientId;
	private String infoMessage;
	private short status;
	private String firstName;
	private String middleName;
	private String lastName;
	private String userEmailId;
	private String empId;
	private String extId;
	private long orgNode;
	private Long reportingMgr;
	private Long accessTypeId;
	private long userId;
	private String clientCode;
	private String clientDescription;
	private String orgNodeName;
	private String reportingManagerName;
	private short isTsOpen;
	private short isExpOpen;	
	private List<ScreensMaster> screensByClient;
	private List<String> accessTypesByClient;
	private List<ScreenCategoryMaster> screenCategoryMaster;
	
	

}
