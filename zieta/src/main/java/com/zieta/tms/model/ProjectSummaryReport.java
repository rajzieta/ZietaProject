package com.zieta.tms.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ProjectSummaryReport {

	@Id
	private Long row_num;

	private String emp_id;

	private String team_id;

	private String team;

	private String employee_name;

	private String proj_id;

	private String project_name;
	
	private String pm_name;
	
	private String submitted_hrs;

	private String approved_hrs;

	private String total_planned_hrs;

	private String total_actual_hrs;

}
