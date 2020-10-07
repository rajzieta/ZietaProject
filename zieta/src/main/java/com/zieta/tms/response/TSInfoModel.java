package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TSTimeEntries;

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
