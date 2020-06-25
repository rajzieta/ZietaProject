package com.zietaproj.zieta.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientProjectTaskResponse {
	private AdditionalDetails additionalDetails;
	private ActivityUser activityUser;
	private TaskActivity taskActivity;			
	

	public ActivitiesByClientProjectTaskResponse() {
		additionalDetails = new ActivitiesByClientProjectTaskResponse.AdditionalDetails();
		activityUser = new ActivitiesByClientProjectTaskResponse.ActivityUser();
		taskActivity = new ActivitiesByClientProjectTaskResponse.TaskActivity();
	};
	
	
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
		private LocalDate startDate;
		private LocalDate endDate;
		private float plannedHrs;
		private float actualHrs;
	}
	
	
	@Getter
	@Setter
	public static class AdditionalDetails {
		private String userName;
		private String activityCode;
		private String activityDesc;
	}
}
