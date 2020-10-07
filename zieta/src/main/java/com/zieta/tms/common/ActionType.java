package com.zieta.tms.common;

import lombok.Getter;

@Getter
public enum ActionType {
	
	INITIAL(1),

	APPROVE( 2),

	REJECT(3),
	
	REVISE(4);
	
	
	long actionType;
	
	ActionType(long actionType){
		this.actionType = actionType;
	}
}
