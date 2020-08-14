package com.zietaproj.zieta.common;

import lombok.Getter;

@Getter
public enum StateType {
	
	INITIAL(1),

	START( 2),

	INPROCESS(3),
	
	COMPLETE(4),
	
	REJECT(5);
	
	long stateType;
	
	StateType(long stateType){
		this.stateType = stateType;
	}
}
