package com.zieta.tms.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesByClientProjectTaskResponse {
	private AdditionalDetails additionalDetails;
	private TaskActivity taskActivity;			
	

	public ActivitiesByClientProjectTaskResponse() {
		additionalDetails = new ActivitiesByClientProjectTaskResponse.AdditionalDetails();
		taskActivity = new ActivitiesByClientProjectTaskResponse.TaskActivity();
	};
	
	

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
		private Long userId;
	}
	
	
	@Getter
	@Setter
	public static class AdditionalDetails {
		private String userName;
		private String activityCode;
		private String activityDesc;
		

	}


	
}
