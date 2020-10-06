package com.zieta.tms.common;

import lombok.Getter;

@Getter
public enum StateType {
	
	INITIAL(1, "NULL"),

	START( 2, "Start"),

	INPROCESS(3, "In Process"),
	
	COMPLETE(4,"Complete"),
	
	REJECT(5, "Reject");
	
	long stateTypeId;
	String stateName;
	
	StateType(long stateTypeId, String stateName){
		this.stateTypeId = stateTypeId;
		this.stateName = stateName;
	}
}
