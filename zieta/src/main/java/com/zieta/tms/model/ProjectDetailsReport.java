package com.zieta.tms.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ProjectDetailsReport {

	@Id
	private Long tsentry_id;

	private String client_code;

	private String client_name;

	private String proj_id;

	private String project_name;

	private String project_manager;

	private String pm_name;

	private String user_id;

	private String employee_name;

	private String emp_id;
	
	private String team_id;
	
	private String team;

	private String ts_id;

	private String ts_date;

	private String submit_date;

	private String task_code;

	private String task_name;

	private String activity_code;

	private String activity_desc;

	private String timesheet_status;

	private String timesheet_status_desc;

	private String ts_start_time;

	private String ts_end_time;

	private String timeentry_duration;

	private String timeentry_desc;

	private String timetype_id;

	private String time_type;

	private String timeentry_status;

	private String timeentry_status_desc;

}
