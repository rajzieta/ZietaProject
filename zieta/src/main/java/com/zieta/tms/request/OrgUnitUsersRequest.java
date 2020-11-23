package com.zieta.tms.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrgUnitUsersRequest {

	private Long Id;
private Long clientId;
	
	private List<Long> userIds;
	
	private Long orgUnitId;
}
