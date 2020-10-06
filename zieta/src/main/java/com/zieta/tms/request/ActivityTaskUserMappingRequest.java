package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityTaskUserMappingRequest {

	private TaskActivity taskActivity;

	public ActivityTaskUserMappingRequest() {
		taskActivity = new ActivityTaskUserMappingRequest.TaskActivity();

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
		private Long userId;
	}

}
