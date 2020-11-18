package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.UserInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgUnitUsersResponse {

	
	private Long id;
    private Long clientId;
    private Long orgUnitId;
    private Long userId;
    
    //additional Data
    
    private String clientCode;
    private String clientDescription;
    
    private List<UserInfo> usersByClient;
}
