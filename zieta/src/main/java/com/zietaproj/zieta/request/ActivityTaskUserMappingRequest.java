package com.zietaproj.zieta.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityTaskUserMappingRequest {
	
	private ActivityUser activityUser;
	private TaskActivity taskActivity;
	
	public ActivityTaskUserMappingRequest() {
		activityUser = new ActivityTaskUserMappingRequest.ActivityUser();
		taskActivity = new ActivityTaskUserMappingRequest.TaskActivity();
		
	}
	
	

	/**
	 * This class is used to map the activity with user in the activity_user_mapping
	 * table
	 * 
	 */
	@Getter
	@Setter
	public static class ActivityUser {
		
		private long activityUserId;
		private long activityId;
		private long taskId;
		private Long userId;
		private long projectId;
		private long clientId;
		private String createdBy;
		private String modifiedBy;
	}

	/**
	 * This class is used to map the activity with task in the task_activity table
	 * 
	 */
	@Getter
	@Setter
	public static class TaskActivity {
		private long taskActivityId;
		private long activityId;
		private long taskId;
		private long projectId;
		private long clientId;
		private String createdBy;
		private String modifiedBy;
		private Date startDate;
		private Date endDate;
		private float plannedHrs;
		private float actualHrs;
	}
	
}
