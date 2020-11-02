package com.zieta.tms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Immutable
@Table(name = "v_proj_report")
@Subselect("select uuid() as id, ts.* from v_proj_report ts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReport {
	
	@Id
	private String id;
	
	@Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "client_code")
	private String clientCode;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "project_code")
	private String projectCode;
	
	@Column(name = "project_name")
	private String projectName;

	@Column(name = "project_manager")
	private Long projectManager;
	
	@Column(name = "pm_fname")
	private String pmFname;
	
	@Column(name = "pm_mname")
	private String pmMname;
	
	@Column(name = "pm_lname")
	private String pmLname;
	
	
	@Column(name = "user_id")
	private Long userId;
	
	
	@Column(name = "user_fname")
	private String userFname;

	@Column(name = "user_mname")
	private String userMname;

	@Column(name = "user_lname")
	private String userLname;
	
	
	@Column(name = "emp_id")
	private String empId;
	
	
	@Column(name = "ts_id")
	private Long tsId;
	
	@Column(name = "ts_date")
	private Date tsDate;
	
	//@Column(name = "request_date")
	//private Date requestDate;
	
	@Column(name = "task_id")
	private Long taskId;
	
	@Column(name = "task_name")
	private String taskName;

	@Column(name = "task_code")
	private String taskCode;
	
	
	@Column(name="activity_id")
    private Long activityId;

    @Column(name = "activity_code")
    private String activityCode;
    
    @Column(name = "activity_desc")
    private String activityDesc;
	
    @Column(name = "timesheet_status")
    private Long timesheetStatus;
    
    @Column(name = "timesheet_status_desc")
    private String timesheetStatusDesc;
    
    @Column(name = "ts_start_time")
	private Float tsStartTime;
	
	@Column(name = "ts_end_time")
	private Float tsEndTime;
    
	@Column(name = "timeentry_duration")
	private Float timeEntryDuration;
	
	
	 @Column(name = "timeentry_desc")
	    private String timeentryDesc;
	 
	 @Column(name = "timetype_id")
	    private Long timetypeId;
	 
	 
	 @Column(name = "time_type")
	    private String timeType;
	 
	 @Column(name = "timeentry_status")
	   private Long timeentryStatus;
	 
	 
	 @Column(name = "timeentry_status_desc")
	    private String timeentryStatusDesc;
	 
	 
}
