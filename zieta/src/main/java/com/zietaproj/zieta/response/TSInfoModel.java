package com.zietaproj.zieta.response;

import java.util.List;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSInfoModel {
	
	String projectCode;
	String taskCode;
	String taskDescription;
	String activityCode;
	String activityDescription;
	
	TSInfo tsInfo;
	List<TSTimeEntries> timeEntries;
	
	String clientCode;

	String clientDescription;
	
		
	

}
