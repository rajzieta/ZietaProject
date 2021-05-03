package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.dto.TSInfoDTO;
import com.zieta.tms.model.TSTimeEntries;
import com.zieta.tms.model.TimeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSInfoModelNew{	
	String taskDescription;
	short isDeleteTask;
	String activityDescription;
	short isDeleteActivity;
	boolean activityStatus;
	String projectDescription;
	short isDeleteProject;	
	TSInfoDTO tsInfo;
	List<TSTimeEntries> timeEntries;
	String timeTypeDesc;
	String clientCode;
	String clientDescription;

}
