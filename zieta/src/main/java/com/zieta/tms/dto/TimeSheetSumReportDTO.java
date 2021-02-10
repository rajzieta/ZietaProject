package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSheetSumReportDTO {

	public String emp_id ;
	public String team_id;
	public String team;
	public String employee_name;
	public String proj_id;
	public String project_name;
	public String tid;
	public String task_name;
	public String activity_desc;
	public String submitted_hrs;
	public String approved_hrs;
	public String total_planned_hrs;
	public String total_actual_hrs;
	
}
