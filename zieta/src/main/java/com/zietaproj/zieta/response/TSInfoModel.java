package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.TSInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSInfoModel {
	
	String projectCode;
	String taskCode;
	String activityCode;
	
	TSInfo tsInfo;

}
