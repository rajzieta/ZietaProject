package com.zietaproj.zieta.request;

import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityTaskUserMappingRequest {
	
	private ActivityUser activityUser;
	private TaskActivity taskActivity;
	
	

	/**
	 * This class is used to map the activity with user in the activity_user_mapping
	 * table
	 * 
	 * @author ranga
	 *
	 */
	@Getter
	@Setter
	
	public static class ActivityUser {

		private long activityId;
		private long taskId;
		private Long userId;
		private long projectId;
		private long clientId;
		private String created_by;
		private String modified_by;
	}

	/**
	 * This class is used to map the activity with task in the task_activity table
	 * 
	 * @author ranga
	 *
	 */
	@Getter
	@Setter
	public static class TaskActivity {
		private long activity_id;
		private long taskId;
		private long projectId;
		private long clientId;
		private String created_by;
		private String modified_by;
		private Date startDate;
		private Date endDate;
		private String plannedHrs;
		private String actualHrs;
	}
}
